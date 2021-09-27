package com.graduationProject.authentication.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "userCreate", groupId = "authentication")
    public void consumeMessage(String message) {
        System.out.println("Authentication Consumer: " + message);
    }
}
