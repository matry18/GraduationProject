package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    // All the services that the Consumer should listen to is listed in this class.
    // The topics and the groupId is hardcoded for each service

    @KafkaListener(topics = "producerone",groupId = "groupone")
    public void consumeMessage(String message){
        // Prints to the console / terminal
        System.out.println(message);
    }

    @KafkaListener(topics = "producerninetynine", groupId = "groupninenine")
    public void consumeMessage_ninetynine(String message){
        // Prints to the console / terminal
        System.out.println(message);
    }
}

