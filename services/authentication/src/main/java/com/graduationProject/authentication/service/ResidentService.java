package com.graduationProject.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.entity.Resident;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.repository.ResidentRepository;
import com.graduationProject.authentication.topic.ResidentTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentService {
    private final KafkaApi kafkaApi;
    private final ResidentRepository residentRepository;

    @Autowired
    public ResidentService(KafkaApi kafkaApi, ResidentRepository residentRepository) {
        this.kafkaApi = kafkaApi;
        this.residentRepository = residentRepository;
    }


    public void addResident(ResidentDto residentDto) {
        residentRepository.save(new Resident(residentDto));
    }


    public void deleteResident(String residentId) {
        residentRepository.deleteById(residentId);
    }
}
