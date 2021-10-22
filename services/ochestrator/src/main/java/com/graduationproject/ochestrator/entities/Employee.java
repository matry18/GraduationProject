package com.graduationproject.ochestrator.entities;

import com.graduationproject.ochestrator.dto.EmployeeDto;

import javax.persistence.*;

@Entity
public class Employee {

    @Column(unique=true)
    private String sagaId;

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    @ManyToOne
    private Department department;

    private String username;

    private String password;

    public Employee(EmployeeDto employeeDto, String sagaId) {
        this.sagaId = sagaId;
        this.id = employeeDto.getId();
        this.firstname = employeeDto.getFirstname();
        this.lastname = employeeDto.getLastname();
        this.email = employeeDto.getEmail();
        this.phoneNumber = employeeDto.getPhoneNumber();
        this.department = new Department(employeeDto.getDepartment());
        //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
        this.username = employeeDto.getUsername();
        this.password = employeeDto.getPassword();
    }

    public Employee() {
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department=" + department +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
