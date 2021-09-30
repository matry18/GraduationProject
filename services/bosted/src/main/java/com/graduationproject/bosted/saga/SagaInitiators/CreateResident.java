package com.graduationproject.bosted.saga.SagaInitiators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.dto.saga.SagaResponseDto;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.saga.SagaInitiator;
import com.graduationproject.bosted.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.ResidentTopics.CreateResidentSagaInit;
import static com.graduationproject.bosted.topic.ResidentTopics.CreateResidentSagaRevert;

@Service
public class CreateResident implements SagaInitiator<ResidentDto> {

    private final KafkaAPI kafkaAPI;
    private final ResidentRepository residentRepository;

    @Autowired
    public CreateResident(KafkaAPI kafkaAPI, ResidentRepository residentRepository) {
        this.kafkaAPI = kafkaAPI;
        this.residentRepository = residentRepository;
    }

    @Override
    public void beginSaga(ResidentDto residentDto) { // begins the Create Resident Saga which is published to the CreateResidentSagaInit topic
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaAPI.publish(CreateResidentSagaInit, objectMapper.writeValueAsString(residentDto));
            System.out.println("I JUST SENT STUFF TO " +  CreateResidentSagaInit);
            System.out.println(residentDto.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert(ResidentDto residentDto, String sagaId) {
        residentRepository.deleteById(residentDto.getId());

        try {
            kafkaAPI.publish(CreateResidentSagaRevert, new ObjectMapper()
                    .writeValueAsString(new SagaResponseDto(sagaId, SagaStatus.SUCCESS)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
