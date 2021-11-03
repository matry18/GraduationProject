package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.entities.Resident;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.ResidentSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.UpdateResidentSagaBegin;
import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.UpdateResidentSagaFailed;

@Service
public class UpdateResident implements SagaParticipator<ResidentDto> {
    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;
    private final ResidentSagaService residentSagaService;

    @Autowired
    public UpdateResident(ResidentRepository residentRepository, KafkaApi kafkaApi, ResidentSagaService residentSagaService) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
        this.residentSagaService = residentSagaService;
    }

    @Override
    public String transact(ResidentDto oldResident, ResidentDto newResident) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto(
                new Resident(newResident, residentSagaService.backupResident(oldResident).getSagaId())
        );
        try {
            kafkaApi.publish(UpdateResidentSagaBegin, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        residentRepository.deleteBySagaId(sagaId);
    }
}
