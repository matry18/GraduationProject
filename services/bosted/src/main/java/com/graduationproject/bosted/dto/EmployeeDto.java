package com.graduationproject.bosted.dto;

import com.graduationproject.bosted.entity.Employee;
import com.graduationproject.bosted.entity.Resident;

import static java.util.Objects.isNull;

public class EmployeeDto {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private DepartmentDto department;
    //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
    private String username;
    private String password;


    public EmployeeDto(String firstname, String id, String lastname, DepartmentDto department){
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
        this.department = department;
    }

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.firstname = employee.getFirstname();
        this.lastname = employee.getLastname();
        this.department = isNull(employee.getDepartment()) ? null : new DepartmentDto(employee.getDepartment());
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}