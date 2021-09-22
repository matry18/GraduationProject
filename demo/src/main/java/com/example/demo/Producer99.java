package com.example.demo;

import com.example.demo.Model.Department;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer99 {

    // The topic is hard coded for each producer / service
    private static final String TOPIC = "producerninetynine";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Method used for POST request and this method is general for all producers / services
    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }

    // Method used for GET request and specific for this producer / service
    public void newDepartment(Department department) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(department));
    }
}