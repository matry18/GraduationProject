package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipators.UpdateEmployee;
import com.graduationproject.ochestrator.type.SagaStatus;
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
    private final SagaResponseRepository sagaResponseRepository;
    private final static String BOSTED_SERVICE_NAME = "bosted";
    private final static String AUTHENTICATION_SERVICE_NAME = "authentication";
    private static final List<String> services = new ArrayList<>(
            Arrays.asList(
                    BOSTED_SERVICE_NAME,
                    AUTHENTICATION_SERVICE_NAME
            )
    );

    @Autowired
    public UpdateEmployeeConsumer(UpdateEmployee updateEmployee, SagaResponseRepository sagaResponseRepository) {
        consumerHelper = new ConsumerHelper<>(updateEmployee, services, EmployeeDto.class);
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @KafkaListener(topics = UpdateEmployeeSagaInit, groupId = GROUP_ID)
    public void consumeUpdateEmployeeSagaInit(String message) {
        consumerHelper.initUpdateSaga(message, UpdateEmployeeSagaInit, BOSTED_SERVICE_NAME);
    }

    @KafkaListener(topics = UpdateEmployeeSagaDone, groupId = GROUP_ID)
    public void consumeUpdateEmployeeSagaDone(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaDone(message, UpdateEmployeeSagaDone);
    }

    @KafkaListener(topics = UpdateEmployeeSagaRevert, groupId = GROUP_ID)
    public void consumeUpdateEmployeeSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaRevert(message, UpdateEmployeeSagaRevert);
    }
}
