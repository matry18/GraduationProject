package com.graduationproject.bosted.kafka.consumers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.saga.SagaEmployeeDto;
import com.graduationproject.bosted.saga.SagaInitiators.UpdateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.EmployeeTopics.UpdateEmployeeSagaFailed;

@Service
public class UpdateEmployeeConsumer {
    private static final String GROUP_ID = "bosted";
    private final UpdateEmployee updateEmployee;

    @Autowired
    public UpdateEmployeeConsumer(UpdateEmployee updateEmployee) {
        this.updateEmployee = updateEmployee;
    }

    @KafkaListener(topics = UpdateEmployeeSagaFailed, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaFailed(String message) {
        System.out.println(GROUP_ID + " " + UpdateEmployeeSagaFailed);
        try {
            SagaEmployeeDto sagaEmployeeDto = new ObjectMapper().readValue(message, SagaEmployeeDto.class);
            updateEmployee.revert(sagaEmployeeDto.getEmployeeDto(), sagaEmployeeDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
