package com.graduationproject.ochestrator.kafka.consumers;

import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.UpdateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.*;

@Service
public class UpdateEmployeeConsumer {

    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<EmployeeDto> consumerHelper;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public UpdateEmployeeConsumer(UpdateEmployee updateEmployee) {
        consumerHelper = new ConsumerHelper<>(updateEmployee, services, EmployeeDto.class);
    }

    @KafkaListener(topics = UpdateEmployeeSagaInit, groupId = GROUP_ID)
    public void consumeUpdateEmployeeSagaInit(String message) {
        consumerHelper.initUpdateSaga(message, UpdateEmployeeSagaInit);
    }

    @KafkaListener(topics = UpdateEmployeeSagaDone, groupId = GROUP_ID)
    public void consumeUpdateEmployeeSagaDone(String message) {
        consumerHelper.sagaDone(message, UpdateEmployeeSagaDone);
    }

    @KafkaListener(topics = UpdateEmployeeSagaRevert, groupId = GROUP_ID)
    public void consumeUpdateEmployeeSagaRevert(String message) {
        consumerHelper.sagaRevert(message, UpdateEmployeeSagaRevert);
    }
}
