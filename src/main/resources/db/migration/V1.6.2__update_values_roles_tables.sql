update roles set allowed_resource = '/dept,/department,/employee,/' where id = 2;
update roles set allowed_resource = '/employees,/ems,/accounts' where id = 3;

commit