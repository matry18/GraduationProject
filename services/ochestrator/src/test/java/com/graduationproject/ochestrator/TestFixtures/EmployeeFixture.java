package com.graduationproject.ochestrator.TestFixtures;

import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.entities.Role;

import static com.graduationproject.ochestrator.TestFixtures.DepartmentFixture.createDepartment;
import static com.graduationproject.ochestrator.TestFixtures.RoleFixture.createRole;

public class EmployeeFixture {

    private String id = "1234";
    private String firstName = "Rasmus";
    private String lastName = "Hansen";
    private String email = "rasmus@mail.dk";
    private String phoneNumber = "66554433";
    private Department department = createDepartment();
    private Role role = createRole();
    private String username = "Raller";
    private String password = "pswd";
    private String sagaId = "1234";


    public static Employee createEmployee() {
        return builder().build();
    }

    public static EmployeeFixture builder() {
        return new EmployeeFixture();
    }

    public Employee build() {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstname(firstName);
        employee.setLastname(lastName);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setDepartment(department);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setSagaId(sagaId);
        employee.setRole(role);
        return employee;
    }

    public EmployeeFixture setId(String id) {
        this.id = id;
        return this;
    }

    public EmployeeFixture setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeFixture setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeFixture setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmployeeFixture setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public EmployeeFixture setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public EmployeeFixture setUsername(String username) {
        this.username = username;
        return this;
    }

    public EmployeeFixture setSagaId(String sagaId) {
        this.sagaId = sagaId;
        return this;
    }

}
