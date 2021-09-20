package com.example.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
//    private static final String TOPIC = "test_topic";
    private static final String TOPIC = "Kafka_Example";

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String message){

        this.kafkaTemplate.send(TOPIC,message);
    }

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate2;
//
//    public void sendMessages(String message) {
//
//        this.kafkaTemplate2.send(TOPIC2, message);
//    }

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(TOPIC,3,(short) 1);
    }
}