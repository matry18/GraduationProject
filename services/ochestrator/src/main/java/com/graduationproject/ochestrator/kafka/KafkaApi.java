package com.graduationproject.ochestrator.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaApi {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, String message) {
        this.kafkaTemplate.send(topic, message);

    }
}
