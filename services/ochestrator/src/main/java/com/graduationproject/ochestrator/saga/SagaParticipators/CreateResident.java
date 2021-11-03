package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.ResidentSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.CreateResidentSagaBegin;
import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.CreateResidentSagaFailed;

@Service
public class CreateResident implements SagaParticipator<ResidentDto> {
    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;
    private final ResidentSagaService residentSagaService;

    @Autowired
    public CreateResident(ResidentRepository residentRepository, KafkaApi kafkaApi, ResidentSagaService residentSagaService) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
        this.residentSagaService = residentSagaService;
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
        SagaResidentDto sagaResidentDto = residentSagaService.backupResident(residentDto);
        try {
            kafkaApi.publish(CreateResidentSagaBegin, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sagaResidentDto.getSagaId();
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        residentRepository.deleteBySagaId(sagaId);
    }

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto(residentRepository.findResidentBySagaId(sagaId));
        try {
            kafkaApi.publish(CreateResidentSagaFailed, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
