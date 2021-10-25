package com.graduationproject.ochestrator.service;

import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSagaService {
    private final KafkaApi kafkaApi;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeSagaService(KafkaApi kafkaApi, EmployeeRepository employeeRepository) {
        this.kafkaApi = kafkaApi;
        this.employeeRepository = employeeRepository;
    }
public List<Employee> fetchAllSagaEmployees() {
        return employeeRepository.findAll();
    }
}
