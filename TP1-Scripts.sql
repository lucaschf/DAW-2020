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

CREATE TABLE museum_employee (	
	cpf  CHARACTER VARYING(11) PRIMARY KEY,
	name CHARACTER VARYING(100) NOT NULL,
	museum_id bigint REFERENCES museum(id)
);

CREATE TABLE schedule(
	id SERIAL PRIMARY KEY,
	scheduler_email CHARACTER VARYING(50) NOT NULL,
	schedule_date DATE NOT NULL,
	schedule_time TIME NOT NULL,
	visitors INTEGER NOT NULL,
	museum_id BIGINT NOT NULL,
   	code TEXT NOT NULL UNIQUE
);

CREATE TABLE visitor(
	schedule_id BIGINT REFERENCES schedule(id) ON DELETE CASCADE,
	cpf CHARACTER VARYING(11) NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	ticket_type INTEGER NOT NULL,
	
	PRIMARY KEY (schedule_id, cpf)
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

select * from visiting_hours(1, '2021-02-21');

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
		
