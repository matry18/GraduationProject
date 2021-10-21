package com.graduationproject.bosted.saga.SagaInitiators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.dto.saga.SagaResponseDto;
import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.entity.Resident;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.saga.SagaInitiator;
import com.graduationproject.bosted.topic.ResidentTopics;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.bosted.topic.ResidentTopics.UpdateResidentSagaRevert;
import static com.graduationproject.bosted.type.SagaStatus.FAILED;
import static com.graduationproject.bosted.type.SagaStatus.SUCCESS;

@Service
public class UpdateResident implements SagaInitiator<ResidentDto> {
    private final KafkaAPI kafkaApi;
    private final ResidentRepository residentRepository;

    public UpdateResident(KafkaAPI kafkaApi, ResidentRepository residentRepository) {
        this.kafkaApi = kafkaApi;
        this.residentRepository = residentRepository;
    }

    @Override
    public void initSaga(ResidentDto residentDto) {
        throw new UnsupportedOperationException("this method cannot be used for update saga");
    }

    @Override
    public void initSaga(ResidentDto oldResident, ResidentDto newResident) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResidentDto> residents = new ArrayList<>(Arrays.asList(oldResident, newResident));

        try {
            kafkaApi.publish(ResidentTopics.UpdateResidentSagaInit, objectMapper.writeValueAsString(residents));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void revert(ResidentDto residentDto, String sagaId) {
        SagaResponseDto sagaResponseDto;
        try {
            Resident resident = residentRepository.getById(residentDto.getId());
            updateResident(resident, residentDto);
            residentRepository.save(resident);
            sagaResponseDto = new SagaResponseDto(sagaId, SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaId, FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        produceKafkaMessage(UpdateResidentSagaRevert, sagaResponseDto);
    }

    private void updateResident(Resident resident, ResidentDto residentDto) {
        resident.setFirstname(residentDto.getFirstname());
        resident.setLastname(residentDto.getLastname());
        resident.setEmail(residentDto.getEmail());
        resident.setPhoneNumber(residentDto.getPhoneNumber());
        resident.setUsername(residentDto.getUsername());
        resident.setDepartment(new Department(residentDto.getDepartment()));
    }

    private void produceKafkaMessage(String residentTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaApi.publish(residentTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
