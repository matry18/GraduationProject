package com.example.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class USersResource {

    @Autowired
    // publiserer en string message
    KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "Kafka_Example"; // dette er navnet på ens topic

    @GetMapping("/publish/{message}")
    public String post(@PathVariable("message") final String message) {

        // bliver publiseret til en topic med en message
        kafkaTemplate.send(TOPIC, message);

        return "Published message succesfully!";
    }
}
