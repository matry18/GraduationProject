package com.graduationProject.authentication.saga.sagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResponseDto;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.saga.SagaParticipator;
import com.graduationProject.authentication.service.ResidentService;
import com.graduationProject.authentication.type.SagaStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.ResidentTopic.DeleteResidentSagaDone;
import static com.graduationProject.authentication.topic.ResidentTopic.DeleteResidentSagaRevert;

@Service
public class DeleteResident implements SagaParticipator<SagaResidentDto> {
    private final ResidentService residentService;
    private final KafkaApi kafkaApi;

    @Autowired
    public DeleteResident(ResidentService residentService, KafkaApi kafkaApi) {
        this.residentService = residentService;
        this.kafkaApi = kafkaApi;
    }

    @Override
    public void transact(SagaResidentDto sagaResidentDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaResidentDto.getResidentDto().getUsername().equals("neverDelete") || sagaResidentDto.getResidentDto().getUsername().equals("bFailRevert")) {
                throw new IllegalArgumentException("Admin can never be deleted");
            }
            residentService.deleteResident(sagaResidentDto.getResidentDto().getId());
            sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.SUCCESS);

        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        produceKafkaMessage(DeleteResidentSagaDone, sagaResponseDto);
    }

    @Override
    public void revert(SagaResidentDto sagaResidentDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaResidentDto.getResidentDto().getPassword().equals("neverDelete") ) {
                throw new Exception();
            }
            residentService.addResident(sagaResidentDto.getResidentDto());
            sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        produceKafkaMessage(DeleteResidentSagaRevert, sagaResponseDto);

    }


    private void produceKafkaMessage(String residentTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaApi.publish(residentTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
