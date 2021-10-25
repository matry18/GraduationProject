package com.graduationproject.bosted.webservice;

import com.graduationproject.bosted.dto.EmployeeDto;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class EmployeeWebService {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeWebService(EmployeeService employeeService, EmployeeRepository employeeRepository, KafkaAPI kafkaAPI) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    @GetMapping("bosted/employees/{id}")
    public EmployeeDto getEmployee(@PathVariable String id) {
        EmployeeDto employeeDto = new EmployeeDto(employeeRepository.getById(id));
        employeeDto.setPassword(null); //no reason to send password.
        return employeeDto;
    }

    @PostMapping("bosted/employees")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.addEmployee(employeeDto);
        return employeeDto;
    }

    @GetMapping("bosted/employees")
    public List<EmployeeDto> getAllEmployees() {
        return this.employeeService.getAllEmployees().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping("bosted/employees")
    public EmployeeDto editEmployee(@RequestBody EmployeeDto employeeDto) {
        return new EmployeeDto(employeeService.editEmployee(employeeDto));
    }

    @DeleteMapping("bosted/employees/{id}")
    public EmployeeDto deleteEmployee(@PathVariable String id) {
        return new EmployeeDto(employeeService.deleteEmployee(id));
    }

}
