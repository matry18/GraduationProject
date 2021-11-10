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

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class DeleteResident implements SagaParticipator<ResidentDto> {
    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;
    private final ResidentSagaService residentSagaService;
    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public DeleteResident(ResidentRepository residentRepository, KafkaApi kafkaApi, ResidentSagaService residentSagaService, SagaResponseRepository sagaResponseRepository) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
        this.residentSagaService = residentSagaService;
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @Override
    public String transact(ResidentDto oldObject, ResidentDto newObject) {
        throw new UnsupportedOperationException("not implemented for this saga");
    }

    @Override
    public String transact(ResidentDto residentDto) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto("not set", residentDto);
        try {
            if (sagaResidentDto.getResidentDto().getUsername().equals("deletefail")) {
                throw new IllegalStateException(String.format("Could not backup or broadcast Resident with \n ID: %s \n SagaID: %s",
                        sagaResidentDto.getResidentDto().getId(),
                        sagaResidentDto.getSagaId()));
            }
            sagaResidentDto = residentSagaService.backupResident(residentDto);
            kafkaApi.publish(DeleteResidentSagaBegin, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(),
                    SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            try {
                kafkaApi.publish(DeleteResidentSagaInitRevert, new ObjectMapper().writeValueAsString(sagaResidentDto));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
        return sagaResidentDto.getSagaId();
    }

    public void transact(String sagaId) {
        try {
            //this will be run after a successful saga
            Resident resident = residentRepository.findResidentBySagaId(sagaId);
            residentRepository.deleteBySagaId(sagaId);
        } catch (Exception e) {
            handleException(e, sagaId);
        }
    }

    @Override
    public void revert(String sagaId) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto(residentRepository.findResidentBySagaId(sagaId));
        try {
            kafkaApi.publish(DeleteResidentSagaFailed, new ObjectMapper().writeValueAsString(sagaResidentDto));
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
