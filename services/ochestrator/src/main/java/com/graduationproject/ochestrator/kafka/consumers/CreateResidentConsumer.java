package com.graduationproject.ochestrator.kafka.consumers;

import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.CreateResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class CreateResidentConsumer {
    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<ResidentDto> consumerHelper;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public CreateResidentConsumer(CreateResident createResident) {
        consumerHelper = new ConsumerHelper<>(createResident, services, ResidentDto.class);
    }

    @KafkaListener(topics = CreateResidentSagaInit, groupId = GROUP_ID)
    public void consumeCreateResidentSagaInit(String message) {
        consumerHelper.initSaga(message, CreateResidentSagaInit);
    }

    @KafkaListener(topics = CreateResidentSagaDone, groupId = GROUP_ID)
    public void consumeCreateResidentSagaDone(String message) {
        consumerHelper.sagaDone(message, CreateResidentSagaDone);
    }

    @KafkaListener(topics = CreateResidentSagaRevert, groupId = GROUP_ID)
    public void consumeCreateResidentSagaRevert(String message) {
        consumerHelper.sagaRevert(message, CreateResidentSagaRevert);
    }
}
