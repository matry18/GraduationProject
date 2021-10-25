package com.graduationProject.authentication.dto;

import com.graduationProject.authentication.entity.Employee;

public class LoginDto {
    private String employeeId;
    private String username;
    private String password;

    public LoginDto(Employee employee) {
        this.employeeId = employee.getId();
        this.username = employee.getUsername();
        this.password = null; //we would not like to the send the hashed password to the client..
    }

    public LoginDto() {

    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
