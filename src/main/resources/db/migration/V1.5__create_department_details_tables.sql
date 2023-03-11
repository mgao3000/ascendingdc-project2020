-- CREATE TABLE employee (
--     /*id              INTEGER NOT NULL default nextval('employee_id_seq'),*/
--     id              BIGSERIAL NOT NULL,
--     name            VARCHAR(30) not null unique,
--     first_name      VARCHAR(30),
--     last_name       VARCHAR(30),
--     email           VARCHAR(50),
--     address         VARCHAR(150),
--     hired_date      date default CURRENT_DATE,
--     department_id   bigint NOT NULL
-- );
--
-- ALTER TABLE employee ADD CONSTRAINT employee_pk PRIMARY KEY ( id );

CREATE TABLE DEPARTMENT_DETAILS (
    id BIGSERIAL NOT NULL,
    DESCRIPTION VARCHAR(80),
    SIZE int,
    REVENUE INT,
    DEPARTMENT_ID BIGINT
);

ALTER TABLE DEPARTMENT_DETAILS ADD CONSTRAINT department_details_pk PRIMARY KEY ( id );

ALTER TABLE DEPARTMENT_DETAILS
    ADD CONSTRAINT department_department_detail_fk FOREIGN KEY ( DEPARTMENT_ID )
        REFERENCES department ( id );


