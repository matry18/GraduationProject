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

import java.util.*;

import static com.graduationproject.ochestrator.topic.ResidentTopics.*;
import static com.graduationproject.ochestrator.topic.SagaStatus.FAILED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class CreateResidentConsumer {
    private static final String GROUP_ID = "orchestrator";
    private Map<String, List<SagaResponseDto>> serviceReplies = new HashMap<>();
    private final CreateResident createResident;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    @Autowired
    public CreateResidentConsumer(CreateResident createResident) {
        this.createResident = createResident;
    }

    @KafkaListener(topics = CreateResidentSagaInit, groupId = GROUP_ID)
    public void consumeCreateResidentSagaInit(String message) {
        try {
            ResidentDto residentDto = new ObjectMapper().readValue(message, ResidentDto.class);
            createResident.transact(residentDto);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = CreateResidentSagaDone, groupId = GROUP_ID)
    public void consumeCreateResidentSagaDone(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            addToServiceReplyMap(sagaResponseDto.getSagaId(), new SagaResponseDto(sagaResponseDto.getSagaId(),
                    "bosted", SagaStatus.SUCCESS));
            addToServiceReplyMap(sagaResponseDto.getSagaId(), sagaResponseDto);
            if (sagaResponseDto.getSagaStatus() == FAILED) {
                createResident.revert(sagaResponseDto.getSagaId());
                serviceReplies.remove(sagaResponseDto.getSagaId());
                return;
            }
            deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = CreateResidentSagaRevert, groupId = GROUP_ID)
    public void consumeCreateResidentSagaRevert(String message) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            addToServiceReplyMap(sagaResponseDto.getSagaId(), sagaResponseDto);
            if (sagaResponseDto.getSagaStatus() == FAILED) {
                //todo: error handling for failed revert = human interaction/log
                System.out.println("Revert failed");
                return;
            }
            System.out.println("SAGA REVERT SUCCESS sendt by: " + sagaResponseDto.toString());
            deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(String sagaId) {
        if (hasEveryServiceReplied(sagaId) && hasNoFailStatuses(serviceReplies.get(sagaId))) {
            System.out.println("EVERY SERVICE GAVE SUCCESS TO DELETE ON REVERT");
            createResident.transact(sagaId);
            serviceReplies.remove(sagaId);
        }
    }

    private void addToServiceReplyMap(String sagaId, SagaResponseDto sagaResponseDto) {
        if (serviceReplies.containsKey(sagaId)) {
            if (!serviceReplies.get(sagaId).contains(sagaResponseDto)) {
                serviceReplies.get(sagaId).add(sagaResponseDto);
            }
        } else {
            List<SagaResponseDto> sagaResponses = new ArrayList<>();
            sagaResponses.add(sagaResponseDto);
            serviceReplies.put(sagaId, sagaResponses);
        }

        // serviceReplies.computeIfAbsent(sagaId, k -> new ArrayList<>()).add(sagaResponseDto);
    }

    private boolean hasNoFailStatuses(List<SagaResponseDto> sagaResponseDtos){
    return sagaResponseDtos.stream().noneMatch(sagaResponse -> sagaResponse.getSagaStatus().equals(FAILED));
    }

    private boolean hasEveryServiceReplied(String sagaId) {
        return nonNull(serviceReplies.get(sagaId)) && serviceReplies.get(sagaId).size() == services.size();
    }

}
