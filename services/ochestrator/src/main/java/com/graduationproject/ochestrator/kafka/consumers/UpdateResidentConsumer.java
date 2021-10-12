package com.graduationproject.ochestrator.kafka.consumers;

import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.UpdateResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class UpdateResidentConsumer {

    private static final String GROUP_ID = "orchestrator";
    private ConsumerHelper<ResidentDto> consumerHelper;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public UpdateResidentConsumer(UpdateResident updateResident) {
        consumerHelper = new ConsumerHelper<>(updateResident, services, ResidentDto.class);
    }

    @KafkaListener(topics = UpdateResidentSagaInit, groupId = GROUP_ID)
    public void consumeUpdateResidentSagaInit(String message) {
        consumerHelper.initUpdateSaga(message, UpdateResidentSagaInit);
    }

    @KafkaListener(topics = UpdateResidentSagaDone, groupId = GROUP_ID)
    public void consumeUpdateResidentSagaDone(String message) {
       consumerHelper.sagaDone(message, UpdateResidentSagaDone);
    }

    @KafkaListener(topics = UpdateResidentSagaRevert, groupId = GROUP_ID)
    public void consumeUpdateResidentSagaRevert(String message) {
        consumerHelper.sagaRevert(message, UpdateResidentSagaRevert);
    }

}
