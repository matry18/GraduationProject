package com.graduationproject.bosted.webservice;

import com.graduationproject.bosted.dto.DepartmentDto;
import com.graduationproject.bosted.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class DepartmentWebService {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentWebService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("bosted/department")
    public DepartmentDto addDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentService.addDepartment(departmentDto);
        return departmentDto;
    }

    @GetMapping("bosted/department")
    public List<DepartmentDto> getAllDepartments() {
        return this.departmentService.getAllDepartments().stream()
                .map(DepartmentDto::new)
                .collect(Collectors.toList());
    }
}
