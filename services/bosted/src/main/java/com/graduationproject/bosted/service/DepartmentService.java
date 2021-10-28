package com.graduationproject.bosted.service;

import com.graduationproject.bosted.dto.DepartmentDto;
import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void addDepartment(DepartmentDto departmentDto) {
        departmentDto.setId(UUID.randomUUID().toString());
        departmentRepository.save(new Department(departmentDto));
    }

    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    public long getDepartmentCount() {
        return this.departmentRepository.count();
    }
}
