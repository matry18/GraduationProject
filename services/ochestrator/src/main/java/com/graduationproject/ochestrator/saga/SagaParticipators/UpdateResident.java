package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.DepartmentDto;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Resident;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.DepartmentRepository;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.graduationproject.ochestrator.topic.resident.ResidentTopics.*;

@Service
public class UpdateResident implements SagaParticipator<ResidentDto> {

    private final DepartmentRepository departmentRepository;
    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;

    @Autowired
    public UpdateResident(DepartmentRepository departmentRepository, ResidentRepository residentRepository, KafkaApi kafkaApi) {
        this.departmentRepository = departmentRepository;
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
    }

    @Transactional
    public void saveDepartment(DepartmentDto departmentDto, String sagaId) {
        Department department = new Department(departmentDto);
        department.setSagaId(sagaId);
        departmentRepository.save(department);
    }


    @Override
    public void transact(ResidentDto oldResident, ResidentDto newResident) {
        //Create sagaId and the resident and sagaId to repo and publish Kafka
        String sagaId = UUID.randomUUID().toString();
        saveDepartment(oldResident.getDepartment(), sagaId);
        residentRepository.save(new Resident(oldResident, sagaId)); //Creates the saga that will be used by the services when responding
        SagaResidentDto sagaResidentDto = new SagaResidentDto(new Resident(newResident, sagaId)); // the dto that will be sent to the services so they know which saga they are part of
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaApi.publish(UpdateResidentSagaBegin, objectMapper.writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transact(ResidentDto residentDto) {
        throw new UnsupportedOperationException("this method cannot be used for update saga");
    }

    @Override
    public void revert(String sagaId) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto( residentRepository.findResidentBySagaId(sagaId));
        try {
            kafkaApi.publish(UpdateResidentSagaFailed, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        Resident resident = residentRepository.findResidentBySagaId(sagaId);
        residentRepository.deleteBySagaId(sagaId);
        if (residentRepository.countByDepartment(resident.getDepartment()) == 0) {
            departmentRepository.deleteBySagaId(sagaId);
        }
    }
}
