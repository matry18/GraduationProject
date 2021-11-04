package com.graduationproject.bosted.dto;

import com.graduationproject.bosted.entity.Resident;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Objects.isNull;
public class ResidentDto {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private DepartmentDto department;
    //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
    private String username;
    private String password;


    public ResidentDto(String firstname, String id, String lastname, DepartmentDto department){
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
        this.department = department;
    }

    public ResidentDto() {

    }

    public ResidentDto(Resident resident) {
        this.id = resident.getId();
        this.firstname = resident.getFirstname();
        this.lastname = resident.getLastname();
        this.department = isNull(resident.getDepartment()) ? null : new DepartmentDto(resident.getDepartment());
        this.email = resident.getEmail();
        this.phoneNumber = resident.getPhoneNumber();
        this.username = resident.getUsername();
    }

    public void hashPassword() {
        int hashStrength = 10;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(hashStrength);
        password = passwordEncoder.encode(password);
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

    @Override
    public String toString() {
        return "ResidentDto{" +
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
