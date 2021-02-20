CREATE TABLE museum(
	id SERIAL PRIMARY KEY,
	name CHARACTER VARYING(255),
 	closes_at TIME WITHOUT TIME ZONE NOT NULL,
    opens_at TIME WITHOUT TIME ZONE NOT NULL,
    visitors_at_time INTEGER NOT NULL,
    minutes_between_visits INTEGER NOT NULL
);

SELECT
	CONCAT ('CONCAT',' ', 'function');

CREATE TABLE museum_working_days (
	museum_id bigint REFERENCES museum(id),
    day_of_week integer NOT NULL,
	
	PRIMARY KEY (museum_id, day_of_week)
);

CREATE TABLE museum_employee (	
	cpf  CHARACTER VARYING(11) PRIMARY KEY,
	name CHARACTER VARYING(100) NOT NULL,
	museum_id bigint REFERENCES museum(id)
);

CREATE TABLE schedule(
	scheduler_email CHARACTER VARYING(50) NOT NULL,
	schedule_date DATE NOT NULL,
	schedule_time TIME NOT NULL,
	visitors INTEGER NOT NULL,
	museum_id BIGINT NOT NULL,
   	code TEXT PRIMARY KEY
);

select ('{'||encode( substring(digest('foobar','sha256') from 1 for 16), 'hex')||'}')::uuid;
      SELECT MD5('GeeksForGeeks MD5');

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
		(2, 7)
		;