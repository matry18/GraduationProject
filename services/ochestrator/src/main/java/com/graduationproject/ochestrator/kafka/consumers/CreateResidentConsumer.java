package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipators.CreateResident;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class CreateResidentConsumer {
    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<ResidentDto> consumerHelper;
    private final static String BOSTED_SERVICE_NAME = "bosted";
    private final static String AUTHENTICATION_SERVICE_NAME = "authentication";
    private static final List<String> services = new ArrayList<>(
            Arrays.asList(
                    BOSTED_SERVICE_NAME,
                    AUTHENTICATION_SERVICE_NAME
            )
    );
    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public CreateResidentConsumer(CreateResident createResident, SagaResponseRepository sagaResponseRepository) {
        consumerHelper = new ConsumerHelper<>(createResident, services, ResidentDto.class);
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @KafkaListener(topics = CreateResidentSagaInit, groupId = GROUP_ID)
    public void consumeCreateResidentSagaInit(String message) {
        consumerHelper.initSaga(message, CreateResidentSagaInit, BOSTED_SERVICE_NAME);
    }

    @KafkaListener(topics = CreateResidentSagaDone, groupId = GROUP_ID)
    public void consumeCreateResidentSagaDone(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaDone(message, CreateResidentSagaDone);
    }

    @KafkaListener(topics = CreateResidentSagaRevert, groupId = GROUP_ID)
    public void consumeCreateResidentSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaRevert(message, CreateResidentSagaRevert);
    }
}
