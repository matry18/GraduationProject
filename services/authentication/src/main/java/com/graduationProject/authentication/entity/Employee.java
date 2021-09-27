package com.graduationProject.authentication.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    private String id;

    private String username;

    private String password;
}
