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
			
		SELECT mu.opensAt INTO hour;
		SELECT mu.opensAt INTO opensAt;
		SELECT mu.visitorsAtTime INTO visitorsAtTime;
		
		LOOP
			SELECT bookedVisitors.visitors FROM bookedVisitors WHERE bookedVisitors.times = hour INTO booked;
			SELECT hour INTO available.t;
			SELECT visitorsAtTime - booked INTO available.vacations;
		
			RETURN QUERY SELECT * FROM available ;
			hour = interval + CONCAT((SELECT mu.minutesBetweenVisits), ' minutes');
			EXIT WHEN hour >= opensAt;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;

SELECT * FROM is_museum_open_at_day(1, '2021-02-20');

CREATE OR REPLACE FUNCTION available_to_visit2(museumId BIGINT, day DATE) RETURNS SETOF available_hours AS $$
	DECLARE 
		openingHour TIME;
		closingHour TIME;
		visitorsAtTime INTEGER;
		minutesBetweenVisits INTEGER;
		
		available available_hours;
		booked INTEGER:=0;
		strInterval TEXT;
		
	BEGIN
		SELECT 
			is_museum_open_at_day.opensAt,
			is_museum_open_at_day.closesAt,
			is_museum_open_at_day.visitorsAtTime, 
			is_museum_open_at_day.minutesBetweenVisits 
			FROM is_museum_open_at_day(museumId, day)
			INTO openingHour, closingHour, visitorsAtTime, minutesBetweenVisits;
		
		
		LOOP
			SELECT visitors FROM booked_visitors(museumId, day) INTO booked WHERE times = openingHour;
			
			IF NOT FOUND THEN
				booked:=0;
			END IF;
			
			available.t := openingHour;
			available.vacations := visitorsAtTime - booked;
		
			RETURN next available ;
		
			SELECT openingHour + (minutesBetweenVisits ||' minutes')::interval INTO openingHour;
			EXIT WHEN openingHour > closingHour;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;


SELECT * FROM booked_visitors(2, '2021-02-20');

SELECT * FROM available_to_visit2(1, '2021-02-20');


CREATE OR REPLACE FUNCTION available_to_visit(museumId BIGINT, day DATE) RETURNS SETOF available_hours AS $$
	DECLARE 
		currentHour TIME;
		closingHour TIME;
		visitorsAtTime INTEGER;
		minutesBetweenVisits INTEGER;
		
		available available_hours;
		booked INTEGER:=0;
		strInterval TEXT;
		
	BEGIN
		SELECT 
			is_museum_open_at_day.opensAt,
			is_museum_open_at_day.closesAt,
			is_museum_open_at_day.visitorsAtTime, 
			is_museum_open_at_day.minutesBetweenVisits 
			FROM is_museum_open_at_day(museumId, day)
			INTO currentHour, closingHour, visitorsAtTime, minutesBetweenVisits;
			
		LOOP
			SELECT visitors FROM booked_visitors(museumId, day) INTO booked WHERE times = currentHour;
			
			IF NOT FOUND THEN
				booked:=0;
			END IF;
			
			available.t := currentHour;
			available.vacations := visitorsAtTime - booked;
		
			RETURN NEXT available ;
			currentHour:= currentHour + (minutesBetweenVisits ||' minutes')::interval;
		
			EXIT WHEN currentHour > closingHour;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;



select ('{'||encode( substring(digest('foobar','sha256') from 1 for 16), 'hex')||'}')::uuid;
      SELECT MD5('teste MD5');



