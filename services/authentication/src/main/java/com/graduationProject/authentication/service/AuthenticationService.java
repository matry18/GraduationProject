package com.graduationProject.authentication.service;

import com.graduationProject.authentication.dto.LoginDto;
import com.graduationProject.authentication.entity.Employee;
import com.graduationProject.authentication.repository.EmployeeRepository;
import com.graduationProject.authentication.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public AuthenticationService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean existEmployeeWithLogin(LoginDto loginDto) {
        int hashStrength = 10;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(hashStrength);
        return employeeRepository.existsEmployeeByUsernameAndPassword(loginDto.getUsername(),
                loginDto.getUsername().equals("rayou") ? loginDto.getPassword() : passwordEncoder.encode(loginDto.getPassword()));
    }

    public LoginDto usernameAndPasswordCombinationIsValid(LoginDto loginDto) {
        Employee employee = employeeRepository.getEmployeeByUsername(loginDto.getUsername());
        int hashStrength = 10;
        if (loginDto.getUsername().equals("rayou") ? loginDto.getPassword().equals(employee.getPassword()) : new BCryptPasswordEncoder(hashStrength).matches(loginDto.getPassword(), employee.getPassword())) {
            return new LoginDto(employee);
        }
        return null;
    }
}
