package com.example.demo;

import com.example.demo.Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    // The topic is hard coded for each producer / service
    private static final String TOPIC = "producerone";

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    // Method used for POST request and this method is general for all producers / services
    public void sendMessage(String message){
        this.kafkaTemplate.send(TOPIC,message);
    }

    // Method used for GET request and specific for this producer / service
    public void newUser(User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(user));
    }

    // Will probably not be used
    @Bean
    public NewTopic createTopic(){
        return new NewTopic(TOPIC,3,(short) 1);
    }
}