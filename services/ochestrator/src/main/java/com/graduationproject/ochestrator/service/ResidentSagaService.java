package com.graduationproject.ochestrator.service;

import com.graduationproject.ochestrator.entities.Resident;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentSagaService {

    private final KafkaApi kafkaApi;
    private final ResidentRepository residentRepository;

    @Autowired
    public ResidentSagaService(KafkaApi kafkaApi, ResidentRepository residentRepository) {
        this.kafkaApi = kafkaApi;
        this.residentRepository = residentRepository;
    }


    public List<Resident> fetchAllSagaResidents() {
        return residentRepository.findAll();
    }

    public long getResidentCount() {
        return this.residentRepository.count();
    }
}
