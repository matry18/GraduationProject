package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipators.UpdateResident;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class UpdateResidentConsumer {

    private ConsumerHelper<ResidentDto> consumerHelper;

    private final SagaResponseRepository sagaResponseRepository;

    private static final String GROUP_ID = "orchestrator";
    private final static String BOSTED_SERVICE_NAME = "bosted";
    private final static String AUTHENTICATION_SERVICE_NAME = "authentication";
    private static final List<String> services = new ArrayList<>(
            Arrays.asList(
                    BOSTED_SERVICE_NAME,
                    AUTHENTICATION_SERVICE_NAME
            )
    );

    @Autowired
    public UpdateResidentConsumer(UpdateResident updateResident, SagaResponseRepository sagaResponseRepository) {
        consumerHelper = new ConsumerHelper<>(updateResident, services, ResidentDto.class);
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @KafkaListener(topics = UpdateResidentSagaInit, groupId = GROUP_ID)
    @Transactional
    public void consumeUpdateResidentSagaInit(String message) {
        consumerHelper.initUpdateSaga(message, UpdateResidentSagaInit, BOSTED_SERVICE_NAME);
    }

    @KafkaListener(topics = UpdateResidentSagaDone, groupId = GROUP_ID)
    @Transactional
    public void consumeUpdateResidentSagaDone(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaDone(message, UpdateResidentSagaDone);
    }

    @KafkaListener(topics = UpdateResidentSagaRevert, groupId = GROUP_ID)
    @Transactional
    public void consumeUpdateResidentSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if (sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaRevert(message, UpdateResidentSagaRevert);
    }

}
