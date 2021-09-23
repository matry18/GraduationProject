package com.graduationproject.bosted.entity;

import com.graduationproject.bosted.dto.DepartmentDto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Department {

    @Id
    private String id;

    private String departmentName;

    public Department() {

    }

    public Department(DepartmentDto departmentDto) {
        this.id = departmentDto.getId();
        this.departmentName = departmentDto.getDepartmentName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
