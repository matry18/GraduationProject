package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.CreateResident;
import com.graduationproject.ochestrator.topic.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationproject.ochestrator.topic.ResidentTopics.*;

@Service
public class CreateResidentConsumer {
    private static final String GROUP_ID = "orchestrator";

    private final CreateResident createResident;

    @Autowired
    public CreateResidentConsumer(CreateResident createResident) {
        this.createResident = createResident;
    }

    @KafkaListener(topics = CreateResidentSagaInit, groupId = GROUP_ID)
    public void consumeCreateResidentSagaInit(String message) {
        try {
            System.out.println("CONSUME " + CreateResidentSagaInit);
            ResidentDto residentDto = new ObjectMapper().readValue(message, ResidentDto.class);
            System.out.println(residentDto.toString());
            createResident.transact(residentDto);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = CreateResidentSagaDone, groupId = GROUP_ID)
    public void consumeCreateResidentSagaDone(String message) {
        try {

            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if(sagaResponseDto.getSagaStatus() == SagaStatus.FAILED) {
                createResident.revert(sagaResponseDto.getSagaId());
                return;
            }
            /*todo: vi kan jo ikke vide om den næste service sender en failed status.
                    Hvis det sker er det jo allerede for sent da vi har gennemført transactionen.
            * */
            createResident.transact(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = CreateResidentSagaRevert, groupId = GROUP_ID)
    public void consumeCreateResidentSagaRevert(String message) {
        SagaResponseDto sagaResponseDto = null;
        try {
            sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            if(sagaResponseDto.getSagaStatus() == SagaStatus.FAILED) {
                //todo: error handling for failed revert = human interaction/log
                return;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
