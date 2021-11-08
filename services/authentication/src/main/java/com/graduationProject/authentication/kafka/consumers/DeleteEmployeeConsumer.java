package com.graduationProject.authentication.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaEmployeeDto;
import com.graduationProject.authentication.saga.sagaParticipators.DeleteEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.EmployeeTopic.*;

@Service
public class DeleteEmployeeConsumer {

    private static final String GROUP_ID = "authentication";
    private final DeleteEmployee deleteEmployee;

    @Autowired
    public DeleteEmployeeConsumer(DeleteEmployee deleteEmployee) {
        this.deleteEmployee = deleteEmployee;
    }


    @KafkaListener(topics = DeleteEmployeeSagaBegin, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaBegin(String message) {
        try {
            System.out.println(GROUP_ID + " " + DeleteEmployeeSagaBegin);
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            deleteEmployee.transact(sagaEmployeeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = DeleteEmployeeSagaFailed, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaFailed(String message) {
        try {
            System.out.println(GROUP_ID + " " + DeleteEmployeeSagaFailed);
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            deleteEmployee.revert(sagaEmployeeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
