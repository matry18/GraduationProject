package com.graduationproject.bosted.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.saga.SagaEmployeeDto;
import com.graduationproject.bosted.saga.SagaInitiators.DeleteEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.EmployeeTopics.DeleteEmployeeSagaFailed;
import static com.graduationproject.bosted.topic.EmployeeTopics.DeleteEmployeeSagaInitRevert;

@Service
public class DeleteEmployeeConsumer {

    private static final String GROUP_ID = "bosted";
    private final DeleteEmployee deleteEmployee;

    @Autowired
    public DeleteEmployeeConsumer(DeleteEmployee deleteEmployee) {
        this.deleteEmployee = deleteEmployee;
    }

    @KafkaListener(topics = DeleteEmployeeSagaFailed, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaFailed(String message) {
        try {
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            deleteEmployee.revert(sagaEmployeeDto.getEmployeeDto(), sagaEmployeeDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = DeleteEmployeeSagaInitRevert, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaInitRevert(String message) {
        try {
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            deleteEmployee.revert(sagaEmployeeDto.getEmployeeDto(), sagaEmployeeDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
