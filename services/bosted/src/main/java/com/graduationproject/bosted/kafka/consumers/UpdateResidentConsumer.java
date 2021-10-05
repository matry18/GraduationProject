package com.graduationproject.bosted.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.saga.SagaResidentDto;
import com.graduationproject.bosted.saga.SagaInitiators.UpdateResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.ResidentTopics.DeleteResidentSagaFailed;
import static com.graduationproject.bosted.topic.ResidentTopics.UpdateResidentSagaFailed;

@Service
public class UpdateResidentConsumer {
    private static final String GROUP_ID = "bosted";
    private final UpdateResident updateResident;

    @Autowired
    public UpdateResidentConsumer(UpdateResident updateResident) {
        this.updateResident = updateResident;

    }


    @KafkaListener(topics = UpdateResidentSagaFailed, groupId = GROUP_ID)
    public void consumeCreateResidentSagaFailed(String message) {
        System.out.println(GROUP_ID + " " + UpdateResidentSagaFailed);
        //will be sent by the orchestrator and bosted should call its revert method for the saga
        try {
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            updateResident.revert(sagaResidentDto.getResidentDto(), sagaResidentDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
