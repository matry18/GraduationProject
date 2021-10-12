package com.graduationproject.ochestrator.kafka.consumers;

import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.DeleteResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class DeleteResidentConsumer {
    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<ResidentDto> consumerHelper;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public DeleteResidentConsumer(DeleteResident deleteResident) {
        consumerHelper = new ConsumerHelper<>(deleteResident, services, ResidentDto.class);
    }

    @KafkaListener(topics = DeleteResidentSagaInit, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaInit(String message) {
        consumerHelper.initSaga(message, DeleteResidentSagaInit);
    }

    @KafkaListener(topics = DeleteResidentSagaDone, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaDone(String message) {
        consumerHelper.sagaDone(message, DeleteResidentSagaDone);
    }

    @KafkaListener(topics = DeleteResidentSagaRevert, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaRevert(String message) {
        consumerHelper.sagaRevert(message, DeleteResidentSagaRevert);
    }
}
