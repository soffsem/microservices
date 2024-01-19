--CREATE DATABASE users;

CREATE SCHEMA IF NOT EXISTS users_scheme;

CREATE TABLE IF NOT EXISTS users_scheme.users
(
    id bigserial NOT NULL primary key,
    first_name character varying(150) NOT NULL,
    middle_name character varying(150),
	surname character varying(150) NOT NULL,
	birthday date NOT NULL,
	sex character(1) NOT NULL,
	city_id integer,
	phone_number character varying(50) NOT NULL,
    email character varying NOT NULL
	nickname character varying(200) NOT NULL,
	password_hash character varying NOT NULL,
	avatarLink character varying,
	about_user text
);

CREATE TABLE IF NOT EXISTS users_scheme.cities
(
    id bigserial NOT NULL primary key,
    city_name character varying(200) NOT NULL,
    country_name character varying(75) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_scheme.hardskills
(
    id bigserial NOT NULL primary key,
	skill character varying(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_scheme.users_hardskills
(
	user_id bigint NOT NULL,
	skill_id bigint NOT NULL,
	PRIMARY KEY (user_id, skill_id)
);

CREATE TABLE IF NOT EXISTS users_scheme.subscriptions
(
   	followee_id bigint NOT NULL,
	follower_id bigint NOT NULL,
	PRIMARY KEY (followee_id, follower_id)
);

ALTER TABLE users_scheme.users
ADD CONSTRAINT city_id_fk foreign key(city_id)
REFERENCES users_scheme.cities(id)
ON DELETE SET NULL;

ALTER TABLE users_scheme.subscriptions
ADD CONSTRAINT follower_id_fk foreign key(follower_id)
REFERENCES users_scheme.users(id)
ON DELETE CASCADE;

ALTER TABLE users_scheme.subscriptions
ADD CONSTRAINT followee_id_fk foreign key(followee_id)
REFERENCES users_scheme.users(id)
ON DELETE CASCADE;

ALTER TABLE users_scheme.users_hardskills
ADD CONSTRAINT user_id_fk foreign key(user_id)
REFERENCES users_scheme.users(id)
ON DELETE CASCADE;

ALTER TABLE users_scheme.users_hardskills
ADD CONSTRAINT skill_id_fk foreign key(skill_id)
REFERENCES users_scheme.hardskills(id)
ON DELETE SET NULL;

CREATE INDEX i_users_sex ON users_scheme.users(sex);
CREATE INDEX i_cities_city_name ON users_scheme.cities(city_name);
CREATE INDEX i_users_name ON users_scheme.users(surname, first_name);
CREATE INDEX i_hardskills_skill ON users_scheme.hardskills(skill);
CREATE INDEX i_subscriptions_follower_followee ON users_scheme.subscriptions(follower_id, followee_id);
CREATE INDEX i_users_hardskills_skill_user ON users_scheme.users_hardskills(skill_id, user_id);