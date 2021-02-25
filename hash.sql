CREATE TABLE hsTest(
	name TEXT NOT NULL,
	id SERIAL PRIMARY KEY,
	moment TIME NOT NULL,
	d DATE NOT NULL,
	hash TEXT NOT NULL
);

DROP TABLE hash2;
CREATE TABLE hash2(
	name TEXT NOT NULL,
	id SERIAL PRIMARY KEY,
	moment TIME NOT NULL,
	d DATE NOT NULL,

	hash TEXT GENERATED ALWAYS AS ( md5(moment::TEXT || d::TEXT)) STORED
);

INSERT INTO public.hash2(
	name, moment, d)
	VALUES ('Lucas', NOW(),  NOW());



CREATE OR REPLACE FUNCTION before_insert() RETURNS TRIGGER AS $$
	DECLARE
		hash TEXT;

	BEGIN 
		hash := md5(CONCAT(NEW.moment, NEW.d)); 
		NEW.hash := hash;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER bf BEFORE INSERT ON hsTest
	FOR EACH ROW EXECUTE FUNCTION before_insert();
	
	
