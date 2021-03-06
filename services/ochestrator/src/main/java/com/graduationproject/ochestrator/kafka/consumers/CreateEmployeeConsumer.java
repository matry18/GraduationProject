package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipators.CreateEmployee;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.*;

@Service
public class CreateEmployeeConsumer {

    private final SagaResponseRepository sagaResponseRepository;

    private ConsumerHelper<EmployeeDto> consumerHelper;

    private static final String GROUP_ID = "orchestrator";
    private final static String ORCHESTRATOR_SERVICE_NAME = "orchestrator";
    private final static String BOSTED_SERVICE_NAME = "bosted";
    private final static String AUTHENTICATION_SERVICE_NAME = "authentication";
    private static final List<String> services = new ArrayList<>(
            Arrays.asList(
                    BOSTED_SERVICE_NAME,
                    AUTHENTICATION_SERVICE_NAME
            )
    );

    @Autowired
    public CreateEmployeeConsumer(CreateEmployee createEmployee, SagaResponseRepository sagaResponseRepository) {
        consumerHelper = new ConsumerHelper<>(createEmployee, services, EmployeeDto.class);
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @KafkaListener(topics = CreateEmployeeSagaInit, groupId = GROUP_ID)
    @Transactional
    public void consumeCreateEmployeeSagaInit(String message) {
        System.out.println("CREATE EMPLOYEE INIT");
        String sagaId = "not set";
        try {
            sagaId = consumerHelper.initSaga(message, CreateEmployeeSagaInit, BOSTED_SERVICE_NAME);
        } catch (Exception e) {
            sagaResponseRepository.save(new SagaResponse(new SagaResponseDto(sagaId, ORCHESTRATOR_SERVICE_NAME, SagaStatus.FAILED, ExceptionUtils.getStackTrace(e))));
        }
    }

    @KafkaListener(topics = CreateEmployeeSagaDone, groupId = GROUP_ID)
    @Transactional
    public void consumeCreateEmployeeSagaDone(String message) {
        String sagaId = "not set";
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            sagaId = sagaResponseDto.getSagaId();
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
            consumerHelper.sagaDone(message, CreateEmployeeSagaDone);
        } catch (Exception e) {
            e.printStackTrace();
            sagaResponseRepository.save(new SagaResponse(new SagaResponseDto(sagaId, ORCHESTRATOR_SERVICE_NAME, SagaStatus.FAILED, ExceptionUtils.getStackTrace(e))));
        }

    }

    @KafkaListener(topics = CreateEmployeeSagaRevert, groupId = GROUP_ID)
    @Transactional
    public void consumeCreateEmployeeSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaRevert(message, CreateEmployeeSagaRevert);
    }

}
