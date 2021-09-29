package com.graduationproject.ochestrator.dto;


import com.graduationproject.ochestrator.entities.Department;

public class DepartmentDto {

    private String id;
    private String departmentName;

    public DepartmentDto(Department department) {
        this.id = department.getId();
        this.departmentName = department.getDepartmentName();
    }

    public DepartmentDto() {

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
        return "DepartmentDto{" +
                "id='" + id + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
