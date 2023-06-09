ALTER TABLE MAJOR
ADD CONSTRAINT UQ_major_name UNIQUE(name);

ALTER TABLE STUDENT
ADD CONSTRAINT UQ_login_name UNIQUE(login_name);

ALTER TABLE STUDENT
ADD CONSTRAINT UQ_email UNIQUE(email);

ALTER TABLE PROJECT
ADD CONSTRAINT UQ_project_name UNIQUE(name);

ALTER TABLE STUDENT_PROJECT
ADD CONSTRAINT UQ_student_id_project_id UNIQUE(student_id, project_id);
