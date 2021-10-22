package com.graduationProject.authentication.webservice;

import com.graduationProject.authentication.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class EmployeeWebService {

    private final Producer producer;

    @Autowired
    public EmployeeWebService(Producer producer) {
        this.producer = producer;
    }

}
