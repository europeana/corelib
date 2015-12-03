-- ADD extra columns to apikey

ALTER TABLE apikey ADD registrationDate DATE NOT NULL DEFAULT '1980-01-01';
ALTER TABLE apikey ADD activationDate DATE NULL;
ALTER TABLE apikey ADD email VARCHAR(100) DEFAULT 'unknown' NOT NULL;
ALTER TABLE apikey ADD level VARCHAR(8) DEFAULT 'CLIENT' NOT NULL;
ALTER TABLE apikey ADD firstname VARCHAR(30) NULL;
ALTER TABLE apikey ADD lastname VARCHAR(50) NULL;
ALTER TABLE apikey ADD company VARCHAR(100) NULL;
ALTER TABLE apikey ADD website VARCHAR(100) NULL;
ALTER TABLE apikey ADD description VARCHAR(255) NULL;

-- ADD extra column to user

ALTER TABLE users ADD activationDate DATE NULL;

-- ADD index to email column

CREATE INDEX apikey_email_index ON apikey (email);

-- copy data from user to apikey

UPDATE apikey
SET
  registrationDate = COALESCE(u.registrationDate,now()),
  email = u.email,
  level = 'CLIENT',
  firstname = u.firstname,
  lastname = u.lastname,
  company = u.company,
  website = u.website
FROM users u
WHERE u.id = apikey.userid;

-- update activationDate for existing users
UPDATE users
SET
  activationDate = '1980-01-01';

-- drop userid column in apikey incl constraints

ALTER TABLE apikey DROP userid;

-- drop API v1 apikey column in user incl constraint and index

ALTER TABLE users DROP apikey;