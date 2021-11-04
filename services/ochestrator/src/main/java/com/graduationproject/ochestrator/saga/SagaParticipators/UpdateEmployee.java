package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaEmployeeDto;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.EmployeeSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.UpdateEmployeeSagaBegin;
import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.UpdateEmployeeSagaFailed;

@Service
public class UpdateEmployee implements SagaParticipator<EmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final EmployeeSagaService employeeSagaService;

    @Autowired
    public UpdateEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi, EmployeeSagaService employeeSagaService) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.employeeSagaService = employeeSagaService;
    }

    @Transactional
    @Override
    public String transact(EmployeeDto oldEmployee, EmployeeDto newEmployee) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(
                new Employee(newEmployee, employeeSagaService.backupEmployee(oldEmployee).getSagaId())
        );
        try {
            kafkaApi.publish(UpdateEmployeeSagaBegin, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sagaEmployeeDto.getSagaId();
    }

    @Override
    public String transact(EmployeeDto employeeDto) {
        throw new UnsupportedOperationException("This method cannot be used for update Saga!");
    }

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employeeRepository.findEmployeeBySagaId(sagaId));
        try {
            kafkaApi.publish(UpdateEmployeeSagaFailed, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        employeeRepository.deleteBySagaId(sagaId);
        employeeSagaService.deleteEmployeeDataForTransaction(sagaId);
    }

}
