DROP TABLE IF EXISTS car_dealer CASCADE;
DROP TABLE IF EXISTS cars CASCADE;

--DROP SEQUENCE IF EXISTS car_dealer_id_seq;
--DROP SEQUENCE IF EXISTS cars_id_seq;
--
--CREATE SEQUENCE car_dealer_id_seq START WITH 1;
--CREATE SEQUENCE cars_id_seq START WITH 1;


CREATE TABLE car_dealer (
    id                BIGSERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    description       VARCHAR(150)
);

ALTER TABLE car_dealer ADD CONSTRAINT car_dealer_pk PRIMARY KEY ( id );

CREATE TABLE cars (
    id              BIGSERIAL NOT NULL,
    model           VARCHAR(30),
    maker           VARCHAR(64),
    price           INTEGER,
    address         VARCHAR(150),
    car_dealer_id   INTEGER NOT NULL
);

ALTER TABLE cars ADD CONSTRAINT cars_pk PRIMARY KEY ( id );

ALTER TABLE cars
    ADD CONSTRAINT car_dealer_cars_fk FOREIGN KEY ( car_dealer_id )
        REFERENCES car_dealer ( id );

