package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.ResidentSagaService;
import com.graduationproject.ochestrator.service.SagaResponseService;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class CreateResident implements SagaParticipator<ResidentDto> {
    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;
    private final ResidentSagaService residentSagaService;
    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public CreateResident(ResidentRepository residentRepository, KafkaApi kafkaApi, ResidentSagaService residentSagaService, SagaResponseService sagaResponseService, SagaResponseRepository sagaResponseRepository) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
        this.residentSagaService = residentSagaService;
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @Override
    public String transact(ResidentDto oldObject, ResidentDto newObject) {
        throw new UnsupportedOperationException("not implemented for this saga");
    }

    @Transactional
    @Override
    public String transact(ResidentDto residentDto) {
        //Create sagaId and the resident and sagaId to repo and publish Kafka
        // the dto that will be sent to the services so they know which saga they are part of
        SagaResidentDto sagaResidentDto = new SagaResidentDto("not set", residentDto);
        try {
            if (residentDto.getUsername().equals("failcreate")) {
                throw new IllegalStateException(String.format("Could not backup or broadcast Resident with \n ID: %s \n SagaID: %s",
                        sagaResidentDto.getResidentDto().getId(),
                        "not set"));
            }
            sagaResidentDto = residentSagaService.backupResident(residentDto);
            kafkaApi.publish(CreateResidentSagaBegin, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaResidentDto.getSagaId(),
                    SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            try {
                kafkaApi.publish(CreateResidentSagaInitRevert, new ObjectMapper().writeValueAsString(sagaResidentDto));
            } catch (JsonProcessingException a) {
                a.printStackTrace();
            }
        }
        return sagaResidentDto.getSagaId();
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

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto(residentRepository.findResidentBySagaId(sagaId));
        try {
            kafkaApi.publish(CreateResidentSagaFailed, new ObjectMapper().writeValueAsString(sagaResidentDto));
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
