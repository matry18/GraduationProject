package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.Resident;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.ResidentSagaService;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class UpdateResident implements SagaParticipator<ResidentDto> {
    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;
    private final ResidentSagaService residentSagaService;
    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public UpdateResident(ResidentRepository residentRepository, KafkaApi kafkaApi, ResidentSagaService residentSagaService, SagaResponseRepository sagaResponseRepository) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
        this.residentSagaService = residentSagaService;
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @Override
    public String transact(ResidentDto oldResident, ResidentDto newResident) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto("not set", oldResident);
        try {
            if (sagaResidentDto.getResidentDto().getUsername().equals("updatefailo")) {
                throw new IllegalStateException(String.format("Could not backup or broadcast Resident with \n ID: %s \n SagaID: %s",
                        sagaResidentDto.getResidentDto().getId(),
                        sagaResidentDto.getSagaId()));
            }
            sagaResidentDto = new SagaResidentDto(
                    new Resident(newResident, residentSagaService.backupResident(oldResident).getSagaId())
            );
            kafkaApi.publish(UpdateResidentSagaBegin, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(), SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            try {
                kafkaApi.publish(UpdateResidentSagaInitRevert, new ObjectMapper().writeValueAsString(sagaResidentDto));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
        return sagaResidentDto.getSagaId();
    }

    @Override
    public String transact(ResidentDto residentDto) {
        throw new UnsupportedOperationException("this method cannot be used for update saga");
    }

    @Override
    public void revert(String sagaId) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto(residentRepository.findResidentBySagaId(sagaId));
        try {
            kafkaApi.publish(UpdateResidentSagaFailed, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (Exception e) {
            handleException(e, sagaId);
        }
    }

    @Transactional
    public void transact(String sagaId) {
        try {
            //this will be run after a successful saga
            residentRepository.deleteBySagaId(sagaId);
        } catch (Exception e) {
            handleException(e, sagaId);
        }
    }

    private void handleException(Exception e, String sagaId) {
        SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaId, SagaStatus.FAILED);
        sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
        sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
    }
}
