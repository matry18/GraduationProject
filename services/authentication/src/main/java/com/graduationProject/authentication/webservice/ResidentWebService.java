package com.graduationProject.authentication.webservice;

import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.producer.Producer;
import com.graduationProject.authentication.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class ResidentWebService {

    private final Producer producer;
    private final ResidentService residentService;

    @Autowired
    public ResidentWebService(ResidentService residentService, Producer producer) {
        this.residentService = residentService;
        this.producer = producer;
    }

    @PostMapping("authentication/test")
    public ResidentDto testBackendConnectionToDB(@RequestBody ResidentDto residentDto){
        residentService.addResident(residentDto);
        System.out.println("Endpoint for authentication-test blev ramt");
        return residentDto;
    }

    @GetMapping("Authentication/test/{message}")
    public void authenticationKafkaPublish(@PathVariable String message) {
        System.out.println("I just produced af message");
        //producer.sendMessage(message);
    }
}
