package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
//    @KafkaListener(topics = "test_topic",groupId = "group_id")
//    public void consumeMessage(String message){
//
//        System.out.println(message);
//    }

    @KafkaListener(topics = "Kafka_Example",groupId = "group_id")
    public void consumeMessage(String message){
        System.out.println(message);
    }
}