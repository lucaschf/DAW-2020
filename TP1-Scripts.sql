CREATE TABLE email_sender_credentials(
    email character varying(255) PRIMARY KEY,
    pass character varying(255) NOT NULL
);

CREATE TABLE museum(
	id SERIAL PRIMARY KEY,
	name CHARACTER VARYING(255),
 	closes_at TIME WITHOUT TIME ZONE NOT NULL,
    opens_at TIME WITHOUT TIME ZONE NOT NULL,
    visitors_at_time INTEGER NOT NULL,
    minutes_between_visits INTEGER NOT NULL
);

CREATE TABLE museum_working_days (
	museum_id bigint REFERENCES museum(id),
    day_of_week integer NOT NULL,
	
	PRIMARY KEY (museum_id, day_of_week)
);

CREATE TABLE schedule(
	id SERIAL PRIMARY KEY,
	scheduler_email CHARACTER VARYING(50) NOT NULL,
	schedule_date DATE NOT NULL,
	schedule_time TIME NOT NULL,
	visitors INTEGER NOT NULL,
	museum_id BIGINT NOT NULL,
	termsAcceptanceDate TIMESTAMP NOT NULL,
   	code TEXT NOT NULL UNIQUE
);

CREATE TABLE visitor(
	schedule_id BIGINT REFERENCES schedule(id) ,
	cpf CHARACTER VARYING(11) NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	ticket_type INTEGER NOT NULL,
	attended BOOLEAN NOT NULL DEFAULT FALSE,
	
	PRIMARY KEY (schedule_id, cpf)
);

CREATE TABLE employee(
	id SERIAL PRIMARY KEY,
	cpf CHARACTER VARYING(11) NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	museum_id INTEGER NULL REFERENCES museum(id) ON DELETE CASCADE,
	UNIQUE (museum_id, cpf)
);

CREATE TABLE role(
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	is_admin BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE users(
	username VARCHAR(30) PRIMARY KEY,
	password VARCHAR(50) NOT NULL,
	role_id INTEGER REFERENCES role(id) ON DELETE CASCADE,
	employee_id INTEGER NULL REFERENCES employee(id) ON DELETE CASCADE,
	created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE attractions (
    id SERIAL PRIMARY KEY,
    title CHARACTER VARYING(200) NOT NULL,
    details TEXT  NOT NULL,
    cover_url TEXT  NOT NULL,
    museum_id INTEGER REFERENCES museum(id) ON DELETE CASCADE,
    beginning_exhibition DATE NOT NULL,
    end_exhibition DATE NOT NULL
);

CREATE OR REPLACE FUNCTION booked_visitors(museumId BIGINT, _date DATE) RETURNS TABLE(
	visitors INT,
	_times TIME
) AS $$ 
	SELECT 
		visitors,
		schedule_time
		FROM schedule
		WHERE museum_id = museumId
			AND schedule_date = _date
			AND EXTRACT(isodow FROM schedule_date::TIMESTAMP) = EXTRACT(isodow FROM _date::TIMESTAMP)
$$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION is_museum_open_at_day(museumId BIGINT, _date DATE) RETURNS TABLE(
	id BIGINT,
	opensAt TIME,
	closesAt TIME,
	visitorsAtTime INTEGER,
	minutesBetweenVisits INTEGER, 
	dayOfWeek INTEGER
) AS $$
	SELECT DISTINCT museum.id,
		museum.opens_at,
		museum.closes_at,
		museum.visitors_at_time,
		minutes_between_visits, 
		museum_working_days.day_of_week
		FROM museum 
		INNER JOIN museum_working_days ON museum_working_days.museum_id = museum.id 
		WHERE museum_id = museumId AND museum_working_days.day_of_week = EXTRACT(isodow FROM _date::TIMESTAMP)
		LIMIT 1
	;
$$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION visiting_hours(museumId BIGINT, _date DATE) RETURNS TABLE (_time TIME, vacations INTEGER) AS $$
	DECLARE 
		currentHour TIME;
		closingHour TIME;
		visitorsAtTime INTEGER;
		minutesBetweenVisits INTEGER;
		
		booked INTEGER:=0;
		strInterval TEXT;
		
	BEGIN
		SELECT 
			is_museum_open_at_day.opensAt,
			is_museum_open_at_day.closesAt,
			is_museum_open_at_day.visitorsAtTime, 
			is_museum_open_at_day.minutesBetweenVisits 
			FROM is_museum_open_at_day(museumId, _date)
			INTO currentHour, closingHour, visitorsAtTime, minutesBetweenVisits;
			
			IF NOT FOUND THEN
				RETURN;
			END IF;
			
		LOOP
			SELECT visitors FROM booked_visitors(museumId, _date) INTO booked WHERE _times = currentHour;
			
			IF NOT FOUND THEN
				booked:=0;
			END IF;
		
			RETURN QUERY SELECT currentHour, visitorsAtTime - booked ;
			currentHour:= currentHour + (minutesBetweenVisits || ' minutes')::interval;
		
			EXIT WHEN currentHour > closingHour;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;
	

CREATE OR REPLACE FUNCTION update_schedule_visitors_count() RETURNS TRIGGER AS $$
	DECLARE 
		visitors_count INTEGER;
	BEGIN 
		SELECT COUNT(cpf) FROM visitor WHERE schedule_id = OLD.schedule_id INTO visitors_count;
		UPDATE schedule SET visitors = visitors_count WHERE schedule.id = OLD.schedule_id; 
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_vistors_count AFTER DELETE ON visitor
	FOR EACH ROW EXECUTE FUNCTION update_schedule_visitors_count();


CREATE OR REPLACE FUNCTION check_user_data_before_insert() RETURNS TRIGGER AS $$
	DECLARE
		is_admin BOOLEAN;
	BEGIN 
		SELECT role.is_admin FROM role WHERE role.id = NEW.role_id INTO is_admin;
		if NOT is_admin AND NEW.employee_id IS NULL THEN 
			RAISE EXCEPTION 'Invalid employee_id';
			RETURN NULL;
		ELSE 
			RETURN NEW;
		END IF;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_user_role_before_insert BEFORE INSERT ON users
	FOR EACH ROW EXECUTE FUNCTION check_user_data_before_insert();

CREATE OR REPLACE FUNCTION generate_schedule_confirmation_code() RETURNS TRIGGER AS $$
	BEGIN 
		NEW.code := 'MSC' || md5(row(NEW.scheduler_email, NEW.schedule_date, NEW.schedule_time )::TEXT);
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER tg_generate_confirmation_code BEFORE INSERT ON schedule
	FOR EACH ROW EXECUTE FUNCTION generate_schedule_confirmation_code();
	

CREATE OR REPLACE FUNCTION is_email_booked(
	targetemail character varying,
	targetdate date,
	targettime time without time zone)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE 
		booked INTEGER;
		
	BEGIN 
		SELECT COUNT(schedule.scheduler_email) FROM schedule 
		WHERE schedule.schedule_date = targetDate AND schedule.schedule_time = targetTime AND schedule.scheduler_email LIKE targetEmail INTO booked;
		
		RETURN booked > 0;
	END;
$BODY$;


CREATE OR REPLACE FUNCTION is_cpf_booked(
	targetcpf character varying,
	targetdate date,
	targettime time without time zone)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE 
		booked INTEGER;
		
	BEGIN 
		SELECT COUNT(visitor.cpf) FROM visitor 
			INNER JOIN schedule ON schedule.id = visitor.schedule_id
		WHERE schedule.schedule_date = targetDate AND schedule.schedule_time = targetTime AND visitor.cpf LIKE targetCpf INTO booked;
		
		RETURN booked > 0;
	END;
$BODY$;

CREATE TYPE report_entry as (
	schedule_id integer,
	cpf character varying,
	name character varying, 
	attended boolean,
	ticket_type integer, 
	schedule_number text,
	schedule_date date, 
	schedule_time time without time zone,
	scheduler_email character varying, 
	museum_name character varying
); 


CREATE OR REPLACE FUNCTION visitors_per_day_time(
	visit_date date,
	visit_time time without time zone,
	museumid bigint
) RETURNS SETOF report_entry
AS $$
BEGIN
		IF museumId IS NOT NULL THEN 
			RETURN QUERY SELECT
				schedule.id,
				visitor.cpf,
				visitor.name,
				visitor.attended, 
				visitor.ticket_type,
				schedule.code,
				schedule.schedule_date,
				schedule.schedule_time,
				schedule.scheduler_email,
				museum.name as museum_name
			FROM schedule 
			INNER JOIN visitor ON visitor.schedule_id = schedule.id
			INNER JOIN museum ON museum.id = schedule.museum_id
			WHERE schedule.schedule_date = visit_date AND schedule.schedule_time = visit_time AND schedule.museum_id = museumId
			ORDER BY schedule.schedule_time;
		ELSE 
			RETURN QUERY SELECT
					schedule.id,
					visitor.cpf,
					visitor.name,
					visitor.attended, 
					visitor.ticket_type,
					schedule.code,
					schedule.schedule_date,
					schedule.schedule_time,
					schedule.scheduler_email,
					museum.name as museum_name
				FROM schedule 
				INNER JOIN visitor ON visitor.schedule_id = schedule.id
				INNER JOIN museum ON museum.id = schedule.museum_id
				WHERE schedule.schedule_date = visit_date AND schedule.schedule_time = visit_time
				ORDER BY schedule.schedule_time;
		END IF
		;
	END;
$$ LANGUAGE PLPGSQL;


CREATE OR REPLACE FUNCTION visitors_who_attended_per_day(
	visit_date DATE,
	museumid BIGINT
)
    RETURNS SETOF report_entry
AS $$
BEGIN
		IF museumId IS NOT NULL THEN 
			RETURN QUERY SELECT
				schedule.id,
				visitor.cpf,
				visitor.name,
				visitor.attended, 
				visitor.ticket_type,
				schedule.code,
				schedule.schedule_date,
				schedule.schedule_time,
				schedule.scheduler_email,
				museum.name as museum_name
			FROM schedule 
			INNER JOIN visitor ON visitor.schedule_id = schedule.id
			INNER JOIN museum ON museum.id = schedule.museum_id
			WHERE schedule.schedule_date = visit_date AND schedule.museum_id = museumId AND visitor.attended
			ORDER BY schedule.schedule_time;
		ELSE 
			RETURN QUERY SELECT
					schedule.id,
					visitor.cpf,
					visitor.name,
					visitor.attended, 
					visitor.ticket_type,
					schedule.code,
					schedule.schedule_date,
					schedule.schedule_time,
					schedule.scheduler_email,
					museum.name as museum_name
				FROM schedule 
				INNER JOIN visitor ON visitor.schedule_id = schedule.id
				INNER JOIN museum ON museum.id = schedule.museum_id
				WHERE schedule.schedule_date = visit_date AND visitor.attended
				ORDER BY schedule.schedule_time;
		END IF
		;
	END;
$$ LANGUAGE PLPGSQL;


INSERT INTO public.museum(
	name, opens_at, closes_at, visitors_at_time, minutes_between_visits)
	VALUES 
		('Museu Municipal de Barbacena', '08:00', '18:00', 5, 60),
		('Museu da Loucura','09:00', '18:00', 10, 60)
	;

INSERT INTO public.museum_working_days(
	museum_id, day_of_week)
	VALUES 
		(1, 1),
		(1, 3),
		(1, 4),
		(1, 5),
		(1, 6),
		(1, 7),
		
		(2, 1),
		(2, 3),
		(2, 4),
		(2, 5),
		(2, 6),
		(2, 7);
		

INSERT INTO role(name, is_admin) VALUES
		('ADMINISTRATOR', true),
		('EMPLOYEE', false);
		
INSERT INTO public.users(
	username, password, role_id, employee_id)
	VALUES ('admin', 'admin', 1, NULL);


INSERT INTO attractions(title, details, cover_url, museum_id, beginning_exhibition end_exhibition) VALUES 
		(
			'Mumificação', 
			'Sem dúvida, as múmias mais famosas foram as egípcias, mas isso não quer dizer que o método usado no Egito antigo foi a única forma de conservação de cadáveres. Conheça os tipos de mumificação existentes em diferentes culturas:

			Cultura Americana

			Em países como Colômbia, Equador, Bolívia e Peru, as múmias históricas foram conservadas graças ao clima seco e frio característico dessas regiões. Em algumas, o processo foi facilitado pela retirada das vísceras e óleos injetados, mas em outros casos, não se fazia esse tratamento prévio, e mesmo assim as múmias foram eternizadas.

			Cultura Oriental

			Na China, Tibete e Japão, o método era feito assim: o corpo era mergulhado em solução aquosa conservante, em seguida, todo o sangue do corpo era retirado (e substituído por álcool) e, por último, um banho com arsênico. Acredita-se que uns dos componentes da solução aquosa eram mercúrio e chumbo.

			Cultura Católica

			Em Palermo (Itália) e México existem muito segredos acerca da conservação de cadáveres, se não fosse por um vestígio deixado nos túmulos: a presença de cal. A cal pode formar uma espécie de filme sobre o corpo, isolando-o da terra. O processo é mantido em segredo pelos freis, mas uma coisa é certa, as catacumbas são gélidas e secas, o que contribuiu em muito para a mumificação.

			Cultura Egípcia

			A mais conhecida das técnicas começava com a retirada do cérebro e vísceras, em seguida o corpo era levado para a secagem. Feito isso, partia-se para a desidratação do cadáver, onde sais de natrão eram aplicados para agilizar o processo (este durava cerca de 70 dias). Por último, se enrolavam as faixas características das múmias.',
		 	'https://cdn.pixabay.com/photo/2015/09/11/04/23/mummy-935258_960_720.jpg',
		 	1,
		 	'2021-02-15',
		 	'2021-05-20'
		 ),

		(
			'Nefertiti', 
			'Nascida no ano de 1380 a.C., Nefertiti, cujo nome significa ‘a mais bela chegou’, foi uma rainha egípcia da XVIII dinastia que se tornou notável por ser a esposa do faraó Amenhotep IV, conhecido como Akhenaton, responsável por substituir o culto politeísta pela reverência a um deus único, o rei-sol Aton.

			Com Akhenaton, Nefertiti teve seis filhas entre os nove anos de reinado do marido. São elas: Meritaton, Meketaton, Ankhesenpaaton, Neferneferuaton, Neferneferuré e Setepenré. Porém, ao longo do reinado egípcio de Akhenaton, três de suas filhas sucumbiram com o alastramento de uma peste da malária, que era conhecida como “doença mágica” por seu poder de devastação. Mais uma das filhas do casal, Meketaton, morreria cedo em decorrência de um afogamento acidental.

			Apesar de ser um símbolo de beleza fascinante mesmo na atualidade, pouco se sabe sobre a vida de Nefertiti. Ela teve uma irmã que se chamava Mutnedjemet e foi criada pela ama Tiy, que era casada com um funcionário da nobre corte, até conhecer e casar-se muito jovem com o faraó Akhenaton.

			A rainha teve grande importância na disseminação do culto monoteísta junto ao seu marido, pois era uma das únicas que podia reverenciar e interceder diretamente com o rei-sol Athon. No reinado de Akhenaton, o faraó e a rainha eram responsáveis pela realização dos cultos e eram figuras representativas dessa divindade, fortalecendo os laços com a população.

			Por sua grande popularidade, alguns historiadores defendem a tese de que Nefertiti tenha sido alvo de assassinato de alguns sacerdotes que defendiam o politeísmo. Outros especialistas, ainda, acreditam que ela tenha se tornado co-regente de Akhenaton, acumulando mais poder. Essa última tese é levantada graças a uma imagem em bloco de pedra onde a rainha aparece golpeando um inimigo com uma maça, remetendo à ideia de força.

			Entretanto, sabe-se que após o término do reinado de seu marido, Nefertiti sumiu misteriosamente, pois poucas escrituras e imagens retratam esse período de sua vida. Alguns arqueólogos estimam que ela tenha morrido no ano de 1345 a.C.

			Em dezembro de 1912, os alemães acharam em sua terra natal uma escultura que identificaram como o ‘busto de Nefertiti’, obra que tornou-se a principal referência estética de sua beleza e austeridade que marcou o período do Egito Antigo. Atualmente, a obra pertence ao Museu de Berlim, na Alemanha.',
		 	'https://cdn.pixabay.com/photo/2017/07/22/22/14/nefertiti-2530055_960_720.jpg',
		 	1,
		 	'2021-02-15',
		 	'2021-05-20'
		 ),
		
		(
			'Nefertiti', 
			'Nascida no ano de 1380 a.C., Nefertiti, cujo nome significa ‘a mais bela chegou’, foi uma rainha egípcia da XVIII dinastia que se tornou notável por ser a esposa do faraó Amenhotep IV, conhecido como Akhenaton, responsável por substituir o culto politeísta pela reverência a um deus único, o rei-sol Aton.

			Com Akhenaton, Nefertiti teve seis filhas entre os nove anos de reinado do marido. São elas: Meritaton, Meketaton, Ankhesenpaaton, Neferneferuaton, Neferneferuré e Setepenré. Porém, ao longo do reinado egípcio de Akhenaton, três de suas filhas sucumbiram com o alastramento de uma peste da malária, que era conhecida como “doença mágica” por seu poder de devastação. Mais uma das filhas do casal, Meketaton, morreria cedo em decorrência de um afogamento acidental.

			Apesar de ser um símbolo de beleza fascinante mesmo na atualidade, pouco se sabe sobre a vida de Nefertiti. Ela teve uma irmã que se chamava Mutnedjemet e foi criada pela ama Tiy, que era casada com um funcionário da nobre corte, até conhecer e casar-se muito jovem com o faraó Akhenaton.

			A rainha teve grande importância na disseminação do culto monoteísta junto ao seu marido, pois era uma das únicas que podia reverenciar e interceder diretamente com o rei-sol Athon. No reinado de Akhenaton, o faraó e a rainha eram responsáveis pela realização dos cultos e eram figuras representativas dessa divindade, fortalecendo os laços com a população.

			Por sua grande popularidade, alguns historiadores defendem a tese de que Nefertiti tenha sido alvo de assassinato de alguns sacerdotes que defendiam o politeísmo. Outros especialistas, ainda, acreditam que ela tenha se tornado co-regente de Akhenaton, acumulando mais poder. Essa última tese é levantada graças a uma imagem em bloco de pedra onde a rainha aparece golpeando um inimigo com uma maça, remetendo à ideia de força.

			Entretanto, sabe-se que após o término do reinado de seu marido, Nefertiti sumiu misteriosamente, pois poucas escrituras e imagens retratam esse período de sua vida. Alguns arqueólogos estimam que ela tenha morrido no ano de 1345 a.C.

			Em dezembro de 1912, os alemães acharam em sua terra natal uma escultura que identificaram como o ‘busto de Nefertiti’, obra que tornou-se a principal referência estética de sua beleza e austeridade que marcou o período do Egito Antigo. Atualmente, a obra pertence ao Museu de Berlim, na Alemanha.',
		 	'https://cdn.pixabay.com/photo/2017/07/22/22/14/nefertiti-2530055_960_720.jpg',
		 	2,
		 	'2021-02-15',
		 	'2021-05-20'
		 );
		
	;