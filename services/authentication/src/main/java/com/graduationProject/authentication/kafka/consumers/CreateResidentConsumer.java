package com.graduationProject.authentication.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.entity.Resident;
import com.graduationProject.authentication.saga.sagaParticipators.CreateResident;
import com.graduationProject.authentication.topic.ResidentTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.ResidentTopics.CreateResidentSagaBegin;
import static com.graduationProject.authentication.topic.ResidentTopics.CreateResidentSagaFailed;

@Service
public class CreateResidentConsumer {
    private static final String GROUP_ID = "authentication";

    private final CreateResident createResident;

    @Autowired
    public CreateResidentConsumer(CreateResident createResident) {
        this.createResident = createResident;
    }

    @KafkaListener(topics = CreateResidentSagaBegin, groupId = GROUP_ID)
    public void consumeCreateResidentSagaBegin(String message) {
        try {
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            System.out.println("On " + CreateResidentSagaBegin + " topic i got: "+ sagaResidentDto.toString());
            createResident.transact(sagaResidentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @KafkaListener(topics =  CreateResidentSagaFailed, groupId = GROUP_ID)
    public void consumeCreateResidentSagaFailed(String message) {
        try {
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            createResident.revert(sagaResidentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
