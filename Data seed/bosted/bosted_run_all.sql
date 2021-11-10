INSERT INTO bosted.access_right (id, name) VALUES ('1', 'admin');
INSERT INTO bosted.access_right (id, name) VALUES ('2', 'create');
INSERT INTO bosted.access_right (id, name) VALUES ('3', 'read');
INSERT INTO bosted.access_right (id, name) VALUES ('4', 'update');
INSERT INTO bosted.access_right (id, name) VALUES ('5', 'delete');

INSERT INTO bosted.role (id, name) VALUES ('d2b280e1-6b37-4f88-b389-fc87d50d871b', 'employee');
INSERT INTO bosted.role (id, name) VALUES ('df79d25a-183a-4b7a-8af9-66e20a79aa5a', 'visitor');
INSERT INTO bosted.role (id, name) VALUES ('ffdff574-c124-4629-9032-dc922a575ef3', 'admin');

INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('ffdff574-c124-4629-9032-dc922a575ef3', '1');
INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('d2b280e1-6b37-4f88-b389-fc87d50d871b', '2');
INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('d2b280e1-6b37-4f88-b389-fc87d50d871b', '3');
INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('d2b280e1-6b37-4f88-b389-fc87d50d871b', '4');
INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('d2b280e1-6b37-4f88-b389-fc87d50d871b', '5');
INSERT INTO bosted.role_access_rights (role_id, access_rights_id) VALUES ('df79d25a-183a-4b7a-8af9-66e20a79aa5a', '3');

INSERT INTO bosted.department (id, department_name) VALUES ('249d2a0b-426e-44ea-84fe-f0625b0c0887', 'Admin department');
INSERT INTO bosted.employee (id, email, firstname, lastname, password, phone_number, username, department_id, role_id) VALUES ('463bb843-c1b7-4573-b78d-c7adcbac486f', 'admin@admin.com', 'Admin', 'Admin', '$2a$10$WkurfslYKS6PEMUhMskCxuqleGYXb1hy4GZCxx/i8mvSesteJX91K', '10101010', 'admin', '249d2a0b-426e-44ea-84fe-f0625b0c0887', 'ffdff574-c124-4629-9032-dc922a575ef3');
