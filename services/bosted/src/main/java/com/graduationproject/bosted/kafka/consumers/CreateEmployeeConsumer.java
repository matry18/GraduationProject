package com.graduationproject.bosted.kafka.consumers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.graduationproject.bosted.saga.SagaInitiators.CreateEmployee;
import com.graduationproject.bosted.dto.saga.SagaEmployeeDto;

import static com.graduationproject.bosted.topic.EmployeeTopics.CreateEmployeeSagaFailed;

@Service
public class CreateEmployeeConsumer {
    private static final String GROUP_ID = "bosted";
    private final CreateEmployee createEmployee;

    @Autowired
    public CreateEmployeeConsumer(CreateEmployee createEmployee) {
        this.createEmployee = createEmployee;
    }

    @KafkaListener(topics = CreateEmployeeSagaFailed, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaFailed(String message) {
        try {
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            createEmployee.revert(sagaEmployeeDto.getEmployeeDto(), sagaEmployeeDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}