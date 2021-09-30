package com.graduationproject.ochestrator.entities;

import com.graduationproject.ochestrator.dto.DepartmentDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Department {

    @Column(unique=true)
    private String sagaId;

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

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
