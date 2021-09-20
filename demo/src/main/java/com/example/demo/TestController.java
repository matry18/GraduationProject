package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class TestController {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;
    private static final String TOPIC = "Kafka_Example";
    private final com.example.demo.Producer producer;

    @Autowired
    public TestController(com.example.demo.Producer producer) {
        this.producer = producer;
    }
    @PostMapping("/publish")
    public void messageToTopic(@RequestParam("message") String message){

        this.producer.sendMessage(message);
        System.out.println("POST SUCCESFULL!");
    }

    @GetMapping("/publish/{firstName}")
    public String post(@PathVariable("firstName") final String firstName){
        kafkaTemplate.send(TOPIC, new User(firstName, "Hansen", 42));
        return "Published succesfully";
    }
}