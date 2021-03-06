package com.graduationproject.ochestrator.entities;

import com.graduationproject.ochestrator.dto.ResidentDto;

import javax.persistence.*;

@Entity
@IdClass(ResidentKey.class)
public class Resident {

    @Id
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


    public Resident(ResidentDto residentDto, String sagaId) {
        this.sagaId = sagaId;
        this.id = residentDto.getId();
        this.firstname = residentDto.getFirstname();
        this.lastname = residentDto.getLastname();
        this.email = residentDto.getEmail();
        this.phoneNumber = residentDto.getPhoneNumber();
        this.department = new Department(residentDto.getDepartment());
        //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
        this.username = residentDto.getUsername();
        this.password = residentDto.getPassword();
    }

    public Resident() {

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

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    @Override
    public String toString() {
        return "Resident{" +
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
