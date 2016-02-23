-- ADD extra column to tokem

ALTER TABLE token ADD redirect character varying(256) NOT NULL DEFAULT 'http://europeana.eu'::character varying;