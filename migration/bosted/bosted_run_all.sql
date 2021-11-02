INSERT INTO bosted.access_right (id, name) VALUES ('1', 'admin');
INSERT INTO bosted.role (id, name) VALUES ('ffdff574-c124-4629-9032-dc922a575ef3', 'admin');
INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('ffdff574-c124-4629-9032-dc922a575ef3', '1');
INSERT INTO bosted.department (id, department_name) VALUES ('249d2a0b-426e-44ea-84fe-f0625b0c0887', 'Admin department');
INSERT INTO bosted.employee (id, email, firstname, lastname, password, phone_number, username, department_id, role_id) VALUES ('463bb843-c1b7-4573-b78d-c7adcbac486f', 'admin@admin.com', 'Admin', 'Admin', 'admin', '10101010', 'admin', '249d2a0b-426e-44ea-84fe-f0625b0c0887', 'ffdff574-c124-4629-9032-dc922a575ef3');
