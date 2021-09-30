package com.graduationproject.ochestrator.service;

import com.graduationproject.ochestrator.kafka.KafkaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentSagaService {

    private final KafkaApi kafkaApi;

    @Autowired
    public ResidentSagaService(KafkaApi kafkaApi) {
        this.kafkaApi = kafkaApi;
    }


}
