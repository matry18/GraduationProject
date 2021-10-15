package com.graduationProject.authentication.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaEmployeeDto;
import com.graduationProject.authentication.saga.sagaParticipators.CreateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.EmployeeTopic.CreateEmployeeSagaBegin;
import static com.graduationProject.authentication.topic.EmployeeTopic.CreateEmployeeSagaFailed;


@Service
public class CreateEmployeeConsumer {

    private static final String GROUP_ID = "authentication";

    private final CreateEmployee createEmployee;

    @Autowired
    public CreateEmployeeConsumer(CreateEmployee createEmployee) {
        this.createEmployee = createEmployee;
    }

    @KafkaListener(topics = CreateEmployeeSagaBegin, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaBegin(String message) {
        try {
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            createEmployee.transact(sagaEmployeeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @KafkaListener(topics =  CreateEmployeeSagaFailed, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaFailed(String message) {
        try {
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            createEmployee.revert(sagaEmployeeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
