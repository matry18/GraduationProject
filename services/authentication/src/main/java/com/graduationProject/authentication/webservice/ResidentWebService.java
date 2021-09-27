package com.graduationProject.authentication.webservice;

import com.graduationProject.authentication.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class ResidentWebService {

    private final Producer producer;

    @Autowired
    public ResidentWebService(Producer producer) {
        this.producer = producer;
    }


    @GetMapping("Authentication/test/{message}")
    public void authenticationKafkaPublish(@PathVariable String message) {
        System.out.println("I just produced af message");
        producer.sendMessage(message);
    }
}
