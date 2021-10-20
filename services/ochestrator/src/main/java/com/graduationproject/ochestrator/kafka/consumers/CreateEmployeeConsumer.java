package com.graduationproject.ochestrator.kafka.consumers;

import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.CreateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.*;

@Service
public class CreateEmployeeConsumer {

    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<EmployeeDto> consumerHelper;
    private static final List<String> services = new ArrayList<>(
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public CreateEmployeeConsumer(CreateEmployee createEmployee) {
        consumerHelper = new ConsumerHelper<>(createEmployee, services, EmployeeDto.class);
    }

    @KafkaListener(topics = CreateEmployeeSagaInit, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaInit(String message) {
        consumerHelper.initSaga(message, CreateEmployeeSagaInit);
    }

    @KafkaListener(topics = CreateEmployeeSagaDone, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaDone(String message) {
        consumerHelper.sagaDone(message, CreateEmployeeSagaDone);
    }

    @KafkaListener(topics = CreateEmployeeSagaRevert, groupId = GROUP_ID)
    public void consumeCreateEmployeeSagaRevert(String message) {
        consumerHelper.sagaRevert(message, CreateEmployeeSagaRevert);
    }

}
