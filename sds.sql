SELECT * FROM schedule WHERE museum_id = 1 AND schedule_date = '2021-02-21'





CREATE OR REPLACE FUNCTION tabuada(numero INTEGER) RETURNS SETOF INTEGER AS $$
	DECLARE
		multiplicador INTEGER DEFAULT 1;
	
	BEGIN
		LOOP
			RETURN NEXT numero * multiplicador;
			multiplicador = multiplicador + 1;
			EXIT WHEN multiplicador = 10;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;

select tabuada(9);

SELECT time '01:00' + interval '3 minutes'

SELECT extract(isodow from schedule_date::timestamp)
from schedule;


SELECT 
	visitors
	FROM schedule
	WHERE schedule_date = '2021-02-20' AND EXTRACT(isodow FROM schedule_date::TIMESTAMP) = 6


SELECT DISTINCT museum.id, museum.closes_at, museum.opens_at, museum.visitors_at_time, minutes_between_visits, museum_working_days.day_of_week
		FROM museum 
		INNER JOIN museum_working_days ON museum_working_days.museum_id = museum.id 
		WHERE museum_id = 1 AND museum_working_days.day_of_week = EXTRACT(isodow FROM schedule_date::TIMESTAMP)
		LIMIT 1
		;

CREATE OR REPLACE FUNCTION is_museum_open_at_day(museumId BIGINT, day DATE) RETURNS TABLE(
	id BIGINT,
	opensAt TIME,
	closesAt TIME,
	visitorsAtTime INTEGER,
	minutesBetweenVisits INTEGER, 
	dayOfWeek INTEGER
) AS $$
	SELECT DISTINCT museum.id, museum.opens_at, museum.closes_at, museum.visitors_at_time, minutes_between_visits, museum_working_days.day_of_week
		FROM museum 
		INNER JOIN museum_working_days ON museum_working_days.museum_id = museum.id 
		WHERE museum_id = museumId AND museum_working_days.day_of_week = EXTRACT(isodow FROM day::TIMESTAMP)
		LIMIT 1
	;
$$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION booked_visitors(museumId BIGINT, day DATE) RETURNS TABLE(
	visitors INT,
	times TIME
) AS $$ 
	SELECT 
		visitors,
		schedule_time
		FROM schedule
		WHERE museum_id = museumId
			AND schedule_date = day
			AND EXTRACT(isodow FROM schedule_date::TIMESTAMP) = EXTRACT(isodow FROM day::TIMESTAMP)
$$ LANGUAGE SQL;

SELECT * FROM is_museum_open_at_day(1, '2021-02-23');
SELECT * FROM booked_visitors(2, '2021-02-20');


SELECT * FROM available_to_visit(2, '2021-02-20');

CREATE TYPE available_hours AS (t TIME, vacations INTEGER);
CREATE OR REPLACE FUNCTION available_to_visit(museumId BIGINT, day DATE) RETURNS TABLE (t TIME, vacations INTEGER) AS $$
	DECLARE 
		hour TIME;
		opensAt TIME;
		visitorsAtTime INTEGER;
		booked INTEGER;
		mu RECORD;
		bookedVisitors RECORD;
		available available_hours;
		
	BEGIN
		SELECT * FROM is_museum_open_at_day(museumId, day) INTO mu;
		SELECT * FROM booked_visitors(museumId, day) INTO bookedVisitors;
			
		SELECT time '01:00' + interval '3 minutes';
		SELECT mu.opensAt INTO hour;
		SELECT mu.opensAt INTO opensAt;
		SELECT mu.visitorsAtTime INTO visitorsAtTime;
		
		LOOP
			SELECT booked_visitors.visitors WHERE booked_visitors.times = hour into booked;
			SELECT hour INTO available.t;
			SELECT visitorsAtTime - booked INTO available.vacations;
		
			RETURN QUERY SELECT * FROM available ;
			hour = interval + CONCAT((SELECT mu.minutesBetweenVisits), ' minutes');
			EXIT WHEN hour >= opensAt;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;
