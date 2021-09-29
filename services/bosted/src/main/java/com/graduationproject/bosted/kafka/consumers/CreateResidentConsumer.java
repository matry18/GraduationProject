package com.graduationproject.bosted.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.dto.saga.SagaResidentDto;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.saga.SagaInitiators.CreateResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.ResidentTopics.CreateResidentSagaFailed;

@Service
public class CreateResidentConsumer {

    private static final String GROUP_ID = "bosted";

    private final CreateResident createResident;

    @Autowired
    public CreateResidentConsumer(CreateResident createResident) {
        this.createResident = createResident;
    }

    @KafkaListener(topics = CreateResidentSagaFailed, groupId = GROUP_ID)
    public void consumeCreateResidentSagaFailed(String message) {
       //will be sent by the orchestrator and bosted should call its revert method for the saga
        try {
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            createResident.revert(sagaResidentDto.getResidentDto(), sagaResidentDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }



}
