package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipators.DeleteResident;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class DeleteResidentConsumer {

    private ConsumerHelper<ResidentDto> consumerHelper;

    private final SagaResponseRepository sagaResponseRepository;

    private final static String BOSTED_SERVICE_NAME = "bosted";
    private final static String AUTHENTICATION_SERVICE_NAME = "authentication";
    private static final String GROUP_ID = "orchestrator";
    private static final List<String> services = new ArrayList<>(
            Arrays.asList(
                    BOSTED_SERVICE_NAME,
                    AUTHENTICATION_SERVICE_NAME
            )
    );
    @Autowired
    public DeleteResidentConsumer(DeleteResident deleteResident, SagaResponseRepository sagaResponseRepository) {
        consumerHelper = new ConsumerHelper<>(deleteResident, services, ResidentDto.class);
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @KafkaListener(topics = DeleteResidentSagaInit, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaInit(String message) {
        consumerHelper.initSaga(message, DeleteResidentSagaInit, BOSTED_SERVICE_NAME);
    }

    @KafkaListener(topics = DeleteResidentSagaDone, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaDone(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if(sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaDone(message, DeleteResidentSagaDone);
    }

    @KafkaListener(topics = DeleteResidentSagaRevert, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if(sagaResponseDto.getSagaStatus().equals(SagaStatus.FAILED)) {
                sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        consumerHelper.sagaRevert(message, DeleteResidentSagaRevert);
    }
}
