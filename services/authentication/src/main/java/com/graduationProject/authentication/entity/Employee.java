package com.graduationProject.authentication.entity;

import com.graduationProject.authentication.dto.EmployeeDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee extends User {
    @Id
    private String id;

    @Column(unique=true)
    private String username;

    private String password;

    public Employee(EmployeeDto employeeDto) {
        this.id = employeeDto.getId();
        this.username = employeeDto.getUsername();
        this.password = employeeDto.getPassword();
    }

    public Employee(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
