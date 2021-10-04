package com.graduationProject.authentication.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.saga.sagaParticipators.UpdateResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.ResidentTopic.*;

@Service
public class UpdateResidentConsumer {
    private static final String GROUP_ID = "authentication";
    private final UpdateResident updateResident;

    @Autowired
    public UpdateResidentConsumer(UpdateResident updateResident) {
        this.updateResident = updateResident;
    }


    @KafkaListener(topics = UpdateResidentSagaBegin, groupId = GROUP_ID)
    public void consumeCreateResidentSagaBegin(String message) {
        try {
            System.out.println(GROUP_ID + " " + UpdateResidentSagaBegin);
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            updateResident.transact(sagaResidentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = UpdateResidentSagaFailed, groupId = GROUP_ID)
    public void consumeCreateResidentSagaFailed(String message) {
        try {
            System.out.println(GROUP_ID + " " + UpdateResidentSagaFailed);
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            updateResident.revert(sagaResidentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
