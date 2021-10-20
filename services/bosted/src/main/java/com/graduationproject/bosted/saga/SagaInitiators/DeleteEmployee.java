package com.graduationproject.bosted.saga.SagaInitiators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.saga.SagaResponseDto;
import com.graduationproject.bosted.entity.Employee;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.saga.SagaInitiator;
import com.graduationproject.bosted.topic.EmployeeTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.graduationproject.bosted.dto.EmployeeDto;

import static com.graduationproject.bosted.type.SagaStatus.SUCCESS;
import static com.graduationproject.bosted.type.SagaStatus.FAILED;

@Service
public class DeleteEmployee implements SagaInitiator<EmployeeDto> {

    private final KafkaAPI kafkaAPI;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DeleteEmployee(KafkaAPI kafkaAPI, EmployeeRepository employeeRepository){
        this.kafkaAPI = kafkaAPI;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void initSaga(EmployeeDto employeeDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaAPI.publish(EmployeeTopics.DeleteEmployeeSagaInit, objectMapper.writeValueAsString(employeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initSaga(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this Saga!");
    }

    @Override
    public void revert(EmployeeDto employeeDto, String sagaId) {
        SagaResponseDto sagaResponseDto;
        try {
            employeeRepository.save(new Employee(employeeDto));
            sagaResponseDto = new SagaResponseDto(sagaId, SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaId, FAILED);
            e.printStackTrace();
        }
        produceKafkaMessage(EmployeeTopics.DeleteEmployeeSagaRevert, sagaResponseDto);
    }

    private void produceKafkaMessage(String employeeTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaAPI.publish(employeeTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
