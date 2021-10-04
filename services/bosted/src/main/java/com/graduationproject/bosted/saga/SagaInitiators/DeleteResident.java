package com.graduationproject.bosted.saga.SagaInitiators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.dto.saga.SagaResponseDto;
import com.graduationproject.bosted.entity.Resident;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.saga.SagaInitiator;
import com.graduationproject.bosted.topic.ResidentTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.type.SagaStatus.FAILED;
import static com.graduationproject.bosted.type.SagaStatus.SUCCESS;

@Service
public class DeleteResident implements SagaInitiator<ResidentDto> {

    private final KafkaAPI kafkaApi;
    private final ResidentRepository residentRepository;


    @Autowired
    public DeleteResident(KafkaAPI kafkaApi, ResidentRepository residentRepository) {
        this.kafkaApi = kafkaApi;
        this.residentRepository = residentRepository;
    }

    @Override
    public void initSaga(ResidentDto residentDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaApi.publish(ResidentTopics.DeleteResidentSagaInit, objectMapper.writeValueAsString(residentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initSaga(ResidentDto oldObject, ResidentDto newObject) {
        throw new UnsupportedOperationException("not implemented for this saga");

    }

    @Override
    public void revert(ResidentDto residentDto, String sagaId) {
        SagaResponseDto sagaResponseDto;
        try {
            residentRepository.save(new Resident(residentDto));
            sagaResponseDto = new SagaResponseDto(sagaId, SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaId, FAILED);
            e.printStackTrace();
        }

        produceKafkaMessage(ResidentTopics.DeleteResidentSagaRevert, sagaResponseDto);
    }

    private void produceKafkaMessage(String residentTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaApi.publish(residentTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
