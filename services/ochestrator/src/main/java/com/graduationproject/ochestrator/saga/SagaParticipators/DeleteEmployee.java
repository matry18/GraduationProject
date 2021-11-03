package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaEmployeeDto;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.EmployeeSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.DeleteEmployeeSagaBegin;
import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.DeleteEmployeeSagaFailed;

@Service
public class DeleteEmployee implements SagaParticipator<EmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final EmployeeSagaService employeeSagaService;

    @Autowired
    public DeleteEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi, EmployeeSagaService employeeSagaService) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.employeeSagaService = employeeSagaService;
    }

    @Override
    public String transact(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this saga!");
    }

    @Transactional
    @Override
    public String transact(EmployeeDto employeeDto) {
        SagaEmployeeDto sagaEmployeeDto = employeeSagaService.backupEmployee(employeeDto);
        try {
            kafkaApi.publish(DeleteEmployeeSagaBegin, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sagaEmployeeDto.getSagaId();
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        employeeRepository.deleteBySagaId(sagaId);
        employeeSagaService.deleteEmployeeDataForTransaction(sagaId);
    }

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employeeRepository.findEmployeeBySagaId(sagaId));
        try {
            kafkaApi.publish(DeleteEmployeeSagaFailed, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
