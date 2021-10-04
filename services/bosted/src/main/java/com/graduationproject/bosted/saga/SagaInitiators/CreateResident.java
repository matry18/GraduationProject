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
    public void initSaga(ResidentDto residentDto) { // begins the Create Resident Saga which is published to the CreateResidentSagaInit topic
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaAPI.publish(CreateResidentSagaInit, objectMapper.writeValueAsString(residentDto));
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
        try {
            residentRepository.deleteById(residentDto.getId()); //you have to use the repository so you dont get cyclic bean dependencies
            kafkaAPI.publish(CreateResidentSagaRevert, new ObjectMapper()
                    .writeValueAsString(new SagaResponseDto(sagaId, SagaStatus.SUCCESS)));
        } catch (Exception e) {
            try {
                kafkaAPI.publish(CreateResidentSagaRevert, new ObjectMapper()
                        .writeValueAsString(new SagaResponseDto(sagaId, SagaStatus.FAILED)));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

}
