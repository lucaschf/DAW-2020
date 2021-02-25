CREATE TABLE email_sender_credentials
(
    email character varying(255) PRIMARY KEY,
    pass character varying(255) NOT NULL,
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
	
	PRIMARY KEY (schedule_id, cpf)
);

ALTER TABLE visitor ADD COLUMN attended BOOLEAN NOT NULL DEFAULT FALSE;	

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

select * from visiting_hours(1, '2021-02-21');

INSERT INTO public.museum(
	name, opens_at, closes_at, visitors_at_time, minutes_between_visits)
	VALUES 
		('Museu Municipal de Barbacena', '08:00', '18:00', 5, 60),
		('Museu da Loucura','09:00', '18:00', 10, 60)
	;
	

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


DROP TABLE users;
DROP TABLE role;

CREATE TABLE role(
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	is_admin BOOLEAN NOT NULL DEFAULT false
);

DROP TABLE users;
DROP TABLE employee;
CREATE TABLE users(
	username VARCHAR(30) PRIMARY KEY,
	password VARCHAR(50) NOT NULL,
	role_id INTEGER REFERENCES role(id) ON DELETE CASCADE,
	employee_id INTEGER NULL REFERENCES employee(id) ON DELETE CASCADE,
	created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE employee(
	id SERIAL PRIMARY KEY,
	cpf CHARACTER VARYING(11) NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	museum_id INTEGER NULL REFERENCES museum(id) ON DELETE CASCADE,
	UNIQUE (museum_id, cpf)
);

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

INSERT INTO role(name, is_admin) VALUES
		('ADMINISTRATOR', true),
		('EMPLOYEE', false);
		
INSERT INTO public.users(
	username, password, role_id, employee_id)
	VALUES ('admin', 'admin', 1, NULL);
	
DROP FUNCTION visitors_per_day;
CREATE OR REPLACE FUNCTION visitors_per_day(visit_date DATE, museumId INTEGER) RETURNS TABLE(
	schedule_id INTEGER,
	cpf CHARACTER VARYING(11),
	name CHARACTER VARYING(100),
	attended BOOLEAN,
	ticket_type INTEGER,
	schedule_number TEXT,
	schedule_date DATE,
	schedule_time TIME,
	scheduler_email CHARACTER VARYING(50),
	museum_name CHARACTER VARYING(255)
)  AS $$
	BEGIN
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
		WHERE schedule.schedule_date = visit_date AND schedule.museum_id = museumId
		ORDER BY schedule.schedule_time
		;
	END;
$$ LANGUAGE PLPGSQL;

SELECT * FROM visitors_per_day('2021-02-25', 1)

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
		