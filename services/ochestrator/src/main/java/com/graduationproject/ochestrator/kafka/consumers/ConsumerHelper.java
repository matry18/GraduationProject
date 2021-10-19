package com.graduationproject.ochestrator.kafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.type.SagaStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.graduationproject.ochestrator.type.SagaStatus.FAILED;
import static java.util.Objects.nonNull;


public class ConsumerHelper<T> {

    private Map<String, List<SagaResponseDto>> serviceReplies = new HashMap<>();
    private SagaParticipator<T> sagaParticipator;
    private final List<String> services;
    private Class<T> typeParameterClass;


    public ConsumerHelper(SagaParticipator<T> sagaParticipator, List<String> services, Class<T> typeParameterClass) {
        this.sagaParticipator = sagaParticipator;
        this.services = services;
        this.typeParameterClass = typeParameterClass;
    }

    public void initSaga(String message, String topic) {

        T object = null;
        try {
            object = new ObjectMapper().readValue(message, typeParameterClass);
            System.out.println(topic);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sagaParticipator.transact(object);
    }

    public void initUpdateSaga(String message, String topic) {
        System.out.println(topic);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<T> updateDto = objectMapper.readValue(message, objectMapper.getTypeFactory().constructCollectionType(List.class, typeParameterClass));
            sagaParticipator.transact(updateDto.get(0), updateDto.get(1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sagaDone(String message, String topic) {

        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            System.out.println(topic + " " + sagaResponseDto.getServiceName());
            addToServiceReplyMap(sagaResponseDto.getSagaId(), new SagaResponseDto(sagaResponseDto.getSagaId(),
                    "bosted", SagaStatus.SUCCESS,"")); //remember that this might have to be removed if the init saga does not come from Bosted. Could be that each consumer has the first index as the initializing service.
            addToServiceReplyMap(sagaResponseDto.getSagaId(), sagaResponseDto);
            if (sagaResponseDto.getSagaStatus() == FAILED) {
                System.out.println(sagaResponseDto.getServiceName() + " FAILED on topic: " + topic);
                sagaParticipator.revert(sagaResponseDto.getSagaId());
                serviceReplies.remove(sagaResponseDto.getSagaId());
                return;
            }
            deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sagaRevert(String message, String topic) {
        try {
            SagaResponseDto sagaResponseDto = new ObjectMapper().readValue(message, SagaResponseDto.class);
            System.out.println(topic + " " + sagaResponseDto.getServiceName());
            addToServiceReplyMap(sagaResponseDto.getSagaId(), sagaResponseDto);
            if (sagaResponseDto.getSagaStatus() == FAILED) {
                //todo: error handling for failed revert = human interaction/log
                System.out.println("Revert failed on topic: " + topic);
                return;
            }
            deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(sagaResponseDto.getSagaId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void deleteResidentDtoWhenAllServiceTypesHasRepliedSuccess(String sagaId) {
        if (hasEveryServiceReplied(sagaId) && hasNoFailStatuses(serviceReplies.get(sagaId))) {
            sagaParticipator.transact(sagaId);
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

    private boolean hasNoFailStatuses(List<SagaResponseDto> sagaResponseDtos) {
        return sagaResponseDtos.stream().noneMatch(sagaResponse -> sagaResponse.getSagaStatus().equals(FAILED));
    }

    private boolean hasEveryServiceReplied(String sagaId) {
        return nonNull(serviceReplies.get(sagaId)) && serviceReplies.get(sagaId).size() == services.size();
    }
}
