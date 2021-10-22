package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipators.DeleteEmployee;
import com.graduationproject.ochestrator.type.SagaStatus;
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

    private final SagaResponseRepository sagaResponseRepository;
    @Autowired
    public DeleteEmployeeConsumer(DeleteEmployee deleteEmployee, SagaResponseRepository sagaResponseRepository) {
        consumerHelper = new ConsumerHelper<>(deleteEmployee, services, EmployeeDto.class);
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @KafkaListener(topics = DeleteEmployeeSagaInit, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaInit(String message) {
        consumerHelper.initSaga(message, DeleteEmployeeSagaInit);
    }

    @KafkaListener(topics = DeleteEmployeeSagaDone, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaDone(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaDone(message, DeleteEmployeeSagaDone);
    }

    @KafkaListener(topics = DeleteEmployeeSagaRevert, groupId = GROUP_ID)
    public void consumeDeleteEmployeeSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaRevert(message, DeleteEmployeeSagaRevert);
    }
}
