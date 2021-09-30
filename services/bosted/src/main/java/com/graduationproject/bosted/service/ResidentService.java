package com.graduationproject.bosted.service;


import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.entity.Resident;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.saga.SagaInitiators.CreateResident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final KafkaAPI kafkaAPI;
    private final CreateResident createResident;

    @Autowired
    public ResidentService(ResidentRepository residentRepository, KafkaAPI kafkaAPI, CreateResident createResident) {
        this.residentRepository = residentRepository;
        this.kafkaAPI = kafkaAPI;
        this.createResident = createResident;
    }

    public void addCitizen(ResidentDto residentDto) {
        residentDto.setId(UUID.randomUUID().toString());
        Resident resident = residentRepository.save(new Resident(residentDto));
        createResident.beginSaga(new ResidentDto(resident));
    }


    public List<Resident> getAllCitizen() {
        return residentRepository.findAll();
    }

    public Resident editCitizen(ResidentDto residentDto) {
        Resident resident = residentRepository.findById(residentDto.getId()).orElse(null);
        resident.setFirstname(residentDto.getFirstname());
        resident.setLastname(residentDto.getLastname());
        resident.setDepartment(new Department(residentDto.getDepartment()));
        resident.setEmail(residentDto.getEmail());
        resident.setPhoneNumber(resident.getPhoneNumber());
        residentRepository.save(resident);


        return resident;
    }

    public Resident deleteCitizen(String citizenId) {
        Resident resident = residentRepository.getById(citizenId);
        residentRepository.deleteById(citizenId);
        return resident;
    }
}
