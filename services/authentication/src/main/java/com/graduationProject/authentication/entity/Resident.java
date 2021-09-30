package com.graduationProject.authentication.entity;

import com.graduationProject.authentication.dto.ResidentDto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Resident extends User{
    @Id
    private String id;

    private String username;

    private String password;

    public Resident(ResidentDto residentDto) {
        this.id = residentDto.getId();
        this.username = residentDto.getUsername();
        this.password = residentDto.getPassword();
    }

    public Resident(){

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
