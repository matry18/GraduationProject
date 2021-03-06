package com.graduationproject.ochestrator.service;

import com.graduationproject.ochestrator.dto.DepartmentDto;
import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Resident;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.DepartmentRepository;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ResidentSagaService {

    private final KafkaApi kafkaApi;
    private final ResidentRepository residentRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public ResidentSagaService(KafkaApi kafkaApi, ResidentRepository residentRepository, DepartmentRepository departmentRepository) {
        this.kafkaApi = kafkaApi;
        this.residentRepository = residentRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void saveDepartment(DepartmentDto departmentDto, String sagaId) {
        Department department = new Department(departmentDto);
        department.setSagaId(sagaId);
        departmentRepository.save(department);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SagaResidentDto backupResident(ResidentDto residentDto) {
        /*if (residentRepository.existsById(residentDto.getId())) {
            throw new IllegalStateException("You should not change an already backed up Entity " +
                    "- a synchronization issue has happened prior to this and needs to be handled first");
        } */
        String sagaId = UUID.randomUUID().toString();
        saveDepartment(residentDto.getDepartment(), sagaId);
        Resident resident = residentRepository.save(new Resident(residentDto, sagaId)); //Creates the saga that will be used by the services when responding
        return new SagaResidentDto(resident);
    }

    public List<Resident> fetchAllSagaResidents() {
        return residentRepository.findAll();
    }

    public long getResidentCount() {
        return this.residentRepository.count();
    }
}
