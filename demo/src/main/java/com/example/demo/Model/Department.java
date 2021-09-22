package com.example.demo.Model;

import java.util.UUID;

// This is just a dummy demo class
public class Department {

    private String departmentName;
    private UUID departmentId;

    public Department(String departmentName, UUID departmentId){
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

}
