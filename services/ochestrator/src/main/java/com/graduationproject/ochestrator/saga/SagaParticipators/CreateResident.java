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
import com.graduationproject.ochestrator.topic.ResidentTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.graduationproject.ochestrator.topic.ResidentTopics.CreateResidentSagaBegin;
import static com.graduationproject.ochestrator.topic.ResidentTopics.CreateResidentSagaFailed;

@Service
public class CreateResident implements SagaParticipator<ResidentDto> {

    private final ResidentRepository residentRepository;
    private final KafkaApi kafkaApi;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public CreateResident(ResidentRepository residentRepository, KafkaApi kafkaApi, DepartmentRepository departmentRepository) {
        this.residentRepository = residentRepository;
        this.kafkaApi = kafkaApi;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void saveDepartment(DepartmentDto departmentDto, String sagaId) {
        Department department = new Department(departmentDto);
        department.setSagaId(sagaId);
        departmentRepository.save(department);
    }

    @Override
    public void transact(ResidentDto residentDto) {
        //Create sagaId and the resident and sagaId to repo and publish Kafka
        String sagaId = UUID.randomUUID().toString();
        saveDepartment(residentDto.getDepartment(), sagaId);
        Resident resident = residentRepository.save(new Resident(residentDto, sagaId)); //Creates the saga that will be used by the services when responding
        SagaResidentDto sagaResidentDto = new SagaResidentDto(resident); // the dto that will be sent to the services so they know which saga they are part of
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaApi.publish(CreateResidentSagaBegin, objectMapper.writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        System.out.println("DELETE BEGUN");
        residentRepository.deleteBySagaId(sagaId);
        departmentRepository.deleteBySagaId(sagaId);
        System.out.println("Was DELETED with sageID: "+ sagaId);
    }

    @Override
    public void revert(String sagaId) {
        SagaResidentDto sagaResidentDto = new SagaResidentDto( residentRepository.findResidentBySagaId(sagaId));
        try {
            kafkaApi.publish(CreateResidentSagaFailed, new ObjectMapper().writeValueAsString(sagaResidentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
