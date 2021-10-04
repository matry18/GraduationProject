package com.graduationProject.authentication.saga.sagaParticipators;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResponseDto;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.saga.SagaParticipator;
import com.graduationProject.authentication.service.ResidentService;
import com.graduationProject.authentication.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationProject.authentication.topic.ResidentTopic.CreateResidentSagaDone;
import static com.graduationProject.authentication.topic.ResidentTopic.CreateResidentSagaRevert;


@Service
public class CreateResident implements SagaParticipator<SagaResidentDto> {


    private final ResidentService residentService;
    private final KafkaApi kafkaApi;

    @Autowired
    public CreateResident(ResidentService residentService, KafkaApi kafkaApi) {
        this.residentService = residentService;
        this.kafkaApi = kafkaApi;
    }

    @Transactional
    @Override
    public void transact(SagaResidentDto sagaResidentDto) {
        //if anything goes wrong it should publish to the revert topic
        try {

            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(),
                    SagaStatus.SUCCESS);
            if (sagaResidentDto.getResidentDto().getUsername().equals("fail")) {
                throw new Exception(); //this should trigger a revert of the saga.
            }
            residentService.addResident(sagaResidentDto.getResidentDto());
            kafkaApi.publish(CreateResidentSagaDone, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(),
                    SagaStatus.FAILED);
            try {
                kafkaApi.publish(CreateResidentSagaDone, new ObjectMapper().writeValueAsString(sagaResponseDto));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }


    }

    @Transactional
    @Override
    public void revert(SagaResidentDto sagaResidentDto) {
        /*
        Revert whatever transaction was done. This could involve different thing in different scenarios.
        Deletion: The orchistrator should send the original object that was deleted to all the saga participators.
                 The saga participator should then save the deleted entity again. Maybe the entity instance can be saved in memory
                 until it is needed to revert?
        Creation: The Saga participator should delete the entity that was just created.
        Update: the saga participator should revert the entity to the state it had before.
         */
        try {
            if (sagaResidentDto.getResidentDto().getPassword().equals("fail")) {
                throw new Exception();
            }

            if (residentService.residentExists(sagaResidentDto.getResidentDto().getId())) {
                residentService.deleteResident(sagaResidentDto.getResidentDto().getId());
            }

            kafkaApi.publish(CreateResidentSagaRevert, new ObjectMapper()
                    .writeValueAsString(new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.SUCCESS)));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                kafkaApi.publish(CreateResidentSagaRevert, new ObjectMapper()
                        .writeValueAsString(new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.FAILED)));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }

    }
}
