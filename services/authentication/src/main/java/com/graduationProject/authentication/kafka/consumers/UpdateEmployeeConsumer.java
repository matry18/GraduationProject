package com.graduationProject.authentication.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaEmployeeDto;
import com.graduationProject.authentication.saga.sagaParticipators.UpdateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.EmployeeTopic.*;

@Service
public class UpdateEmployeeConsumer {

    private static final String GROUP_ID = "authentication";
    private final UpdateEmployee updateEmployee;

    @Autowired
    public UpdateEmployeeConsumer(UpdateEmployee updateEmployee) {
        this.updateEmployee = updateEmployee;
    }

    @KafkaListener(topics = UpdateEmployeeSagaBegin, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaBegin(String message) {
        try {
            System.out.println(GROUP_ID + " " + UpdateEmployeeSagaBegin);
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            updateEmployee.transact(sagaEmployeeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = UpdateEmployeeSagaFailed, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaFailed(String message) {
        try {
            System.out.println(GROUP_ID + " " + UpdateEmployeeSagaFailed);
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            updateEmployee.revert(sagaEmployeeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
