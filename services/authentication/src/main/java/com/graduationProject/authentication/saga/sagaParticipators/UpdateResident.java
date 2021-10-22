package com.graduationProject.authentication.saga.sagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResponseDto;
import com.graduationProject.authentication.entity.Resident;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.repository.ResidentRepository;
import com.graduationProject.authentication.saga.SagaParticipator;
import com.graduationProject.authentication.topic.ResidentTopic;
import com.graduationProject.authentication.type.SagaStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationProject.authentication.type.SagaStatus.FAILED;
import static com.graduationProject.authentication.type.SagaStatus.SUCCESS;

@Service
public class UpdateResident implements SagaParticipator<SagaResidentDto> {


    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;

    @Autowired
    public UpdateResident(ResidentRepository residentRepository, KafkaApi kafkaApi) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
    }

    @Transactional
    @Override
    public void transact(SagaResidentDto sagaResidentDto) {
        SagaResponseDto sagaResponseDto;
       try {
           if (sagaResidentDto.getResidentDto().getFirstname().equals("updateFail")) {
               throw new IllegalStateException(String.format("Could not update Resident with \n ID: %s \n SagaID: %s",
                       sagaResidentDto.getResidentDto().getId(),
                       sagaResidentDto.getSagaId()));
           }
           Resident resident = residentRepository.getById(sagaResidentDto.getResidentDto().getId());
           updateResident(resident, sagaResidentDto.getResidentDto());
           residentRepository.save(resident);
           sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SUCCESS);
       }catch (Exception e) {
           sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), FAILED);
           sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
           e.printStackTrace();
       }

        produceKafkaMessage(ResidentTopic.UpdateResidentSagaDone, sagaResponseDto);
    }

    @Transactional
    @Override
    public void revert(SagaResidentDto sagaResidentDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaResidentDto.getResidentDto().getLastname().equals("updateFail")) {
                throw new IllegalStateException(String.format("Could not revert update of Resident with \n ID: %s \n SagaID: %s",
                        sagaResidentDto.getResidentDto().getId(),
                        sagaResidentDto.getSagaId()));
            }
            Resident resident = residentRepository.getById(sagaResidentDto.getResidentDto().getId());
            updateResident(resident, sagaResidentDto.getResidentDto());
            residentRepository.save(resident);
            sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        produceKafkaMessage(ResidentTopic.UpdateResidentSagaRevert, sagaResponseDto);
    }

    private void updateResident(Resident resident, ResidentDto residentDto) {
        resident.setUsername(residentDto.getUsername());
        resident.setPassword(residentDto.getPassword());
    }

    private void produceKafkaMessage(String residentTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaApi.publish(residentTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
