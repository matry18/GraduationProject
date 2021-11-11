package com.graduationproject.bosted.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.saga.SagaResidentDto;
import com.graduationproject.bosted.saga.SagaInitiators.DeleteResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.ResidentTopics.DeleteResidentSagaFailed;
import static com.graduationproject.bosted.topic.ResidentTopics.DeleteResidentSagaInitRevert;

@Service
public class DeleteResidentConsumer {

    private static final String GROUP_ID = "bosted";
    private final DeleteResident deleteResident;

    @Autowired
    public DeleteResidentConsumer(DeleteResident deleteResident) {
        this.deleteResident = deleteResident;
    }


    @KafkaListener(topics = DeleteResidentSagaFailed, groupId = GROUP_ID)
    public void consumeCreateResidentSagaFailed(String message) {
        revertSaga(message);
    }

    @KafkaListener(topics = DeleteResidentSagaInitRevert, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaInitRevert(String message) {
        revertSaga(message);
    }

    private void revertSaga(String message) {
        //will be sent by the orchestrator and bosted should call its revert method for the saga
        try {
            SagaResidentDto sagaResidentDto = new ObjectMapper().readValue(message, SagaResidentDto.class);
            deleteResident.revert(sagaResidentDto.getResidentDto(), sagaResidentDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
