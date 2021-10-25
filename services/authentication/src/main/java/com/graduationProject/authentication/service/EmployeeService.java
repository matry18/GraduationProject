package com.graduationProject.authentication.service;

import com.graduationProject.authentication.dto.EmployeeDto;
import com.graduationProject.authentication.entity.Employee;
import com.graduationProject.authentication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService( EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void addEmployee(EmployeeDto employeeDto) {
        int hashStrength = 10;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(hashStrength);
        employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employeeRepository.save(new Employee(employeeDto));
    }

    private boolean doesEmployeeExists(String employeeId) {
        return employeeRepository.existsEmployeeById(employeeId);
    }

    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public boolean employeeExists(String employeeId) {
        return employeeRepository.existsEmployeeById(employeeId);
    }

    public void deleteIfExists(String id) {
        if (doesEmployeeExists(id)) {
            deleteEmployee(id);
        }
    }
}
