package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.saga.SagaParticipators.DeleteResident;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.graduationproject.ochestrator.topic.ResidentTopics.*;
import static com.graduationproject.ochestrator.type.SagaStatus.FAILED;
import static java.util.Objects.nonNull;

@Service
public class DeleteResidentConsumer {
    private static final String GROUP_ID = "orchestrator";
    private Map<String, List<SagaResponseDto>> serviceReplies = new HashMap<>();
    private final DeleteResident deleteResident;
    private static final List<String> services = new ArrayList<>( //Remember that it is not always every service participating in each saga
            Arrays.asList(
                    "bosted",
                    "authentication"
            )
    );

    public DeleteResidentConsumer(DeleteResident deleteResident) {
        this.deleteResident = deleteResident;
    }

    @KafkaListener(topics = DeleteResidentSagaInit, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaInit(String message) {
        System.out.println(GROUP_ID+ " " +DeleteResidentSagaInit);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ResidentDto residentDto = objectMapper.readValue(message, ResidentDto.class);
            deleteResident.transact(residentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = DeleteResidentSagaDone, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaDone(String message) {
        try {
            System.out.println(GROUP_ID+ " " +DeleteResidentSagaDone);
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            addToServiceReplyMap(sagaResponseDto.getSagaId(), new SagaResponseDto(sagaResponseDto.getSagaId(),
                    "bosted", SagaStatus.SUCCESS));
            addToServiceReplyMap(sagaResponseDto.getSagaId(), sagaResponseDto);
            if (sagaResponseDto.getSagaStatus() == FAILED) {
                System.out.println(sagaResponseDto.getServiceName() + " FAILED to delete resident");
                deleteResident.revert(sagaResponseDto.getSagaId());
                serviceReplies.remove(sagaResponseDto.getSagaId());
                return;
            }
            deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = DeleteResidentSagaRevert, groupId = GROUP_ID)
    public void consumeDeleteResidentSagaRevert(String message) {
        try {
            System.out.println(GROUP_ID+ " " +DeleteResidentSagaRevert);
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            addToServiceReplyMap(sagaResponseDto.getSagaId(), sagaResponseDto);
            if (sagaResponseDto.getSagaStatus() == FAILED) {
                //todo: error handling for failed revert = human interaction/log
                System.out.println("Revert failed");
                return;
            }
            deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(String sagaId) {
        if (hasEveryServiceReplied(sagaId) && hasNoFailStatuses(serviceReplies.get(sagaId))) {
            deleteResident.transact(sagaId);
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
