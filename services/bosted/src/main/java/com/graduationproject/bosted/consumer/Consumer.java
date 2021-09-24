package com.graduationproject.bosted.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "userCreate", groupId = "bosted")
    public void consumeMessage(String message) {
        System.out.println("CONSUMER MESSAGE: "+message);
    }
}
