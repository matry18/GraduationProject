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

    @GetMapping("bosted/employee/{id}")
    public EmployeeDto getPerson(@PathVariable String id) {
        return new EmployeeDto(employeeRepository.findById(id).orElse(null));
    }

    @PostMapping("bosted/employee")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.addEmployee(employeeDto);
        System.out.println("Got message from frontend");
        return employeeDto;
    }

    @GetMapping("bosted/employee")
    public List<EmployeeDto> getAllEmployees() {
        return this.employeeService.getAllEmployees().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping("bosted/employee")
    public EmployeeDto editEmployee(@RequestBody EmployeeDto employeeDto) {
        return new EmployeeDto(employeeService.editEmployee(employeeDto));
    }

    @DeleteMapping("bosted/employee/{id}")
    public EmployeeDto deleteEmployee(@PathVariable String id) {
        return new EmployeeDto(employeeService.deleteEmployee(id));
    }

}
