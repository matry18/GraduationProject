package com.graduationproject.ochestrator.kafka.consumers;

import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.DeleteEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.*;

@Service
public class DeleteEmployeeConsumer {
    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<EmployeeDto> consumerHelper;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public DeleteEmployeeConsumer(DeleteEmployee deleteEmployee) {
        consumerHelper = new ConsumerHelper<>(deleteEmployee, services, EmployeeDto.class);
    }

    @KafkaListener(topics = DeleteEmployeeSagaInit, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaInit(String message) {
        consumerHelper.initSaga(message, DeleteEmployeeSagaInit);
    }

    @KafkaListener(topics = DeleteEmployeeSagaDone, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaDone(String message) {
        consumerHelper.sagaDone(message, DeleteEmployeeSagaDone);
    }

    @KafkaListener(topics = DeleteEmployeeSagaRevert, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaRevert(String message) {
        consumerHelper.sagaRevert(message, DeleteEmployeeSagaRevert);
    }
}
