package com.graduationProject.authentication.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.saga.sagaParticipators.DeleteResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.ResidentTopic.*;

@Service
public class DeleteResidentConsumer {
    private static final String GROUP_ID = "authentication";
    private final DeleteResident deleteResident;

    @Autowired
    public DeleteResidentConsumer(DeleteResident deleteResident) {
        this.deleteResident = deleteResident;
    }


    @KafkaListener(topics = DeleteResidentSagaBegin, groupId = GROUP_ID)
    public void consumeCreateResidentSagaBegin(String message) {
        try {
            System.out.println(GROUP_ID+ " " +DeleteResidentSagaBegin);
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            deleteResident.transact(sagaResidentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    @KafkaListener(topics =  DeleteResidentSagaFailed, groupId = GROUP_ID)
    public void consumeCreateResidentSagaFailed(String message) {
        try {     System.out.println(GROUP_ID+ " " +DeleteResidentSagaFailed);
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            deleteResident.revert(sagaResidentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
