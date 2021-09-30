package com.graduationproject.bosted.entity;

import com.graduationproject.bosted.dto.EmployeeDto;
import com.graduationproject.bosted.dto.ResidentDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employee extends User{
    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String Email;

    private String phoneNumber;

    @ManyToOne
    private Department department;

    private String username;

    private String password;


    public Employee(EmployeeDto employeeDto) {
        this.setFirstname(employeeDto.getFirstname());
        this.setLastname(employeeDto.getLastname());
        this.setEmail(employeeDto.getEmail());
        this.setPhoneNumber(employeeDto.getPhoneNumber());

        //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
        this.setUsername(employeeDto.getUsername());
        this.setPassword(employeeDto.getPassword());
    }

    public Employee(){

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
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
