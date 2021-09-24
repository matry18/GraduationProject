package com.graduationProject.authentication.producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        final String TOPIC = "userCreate";
        this.kafkaTemplate.send(TOPIC, "FROM Authentication: "+message);
    }

}
