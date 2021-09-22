package com.example.demo;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class controller {

    @GetMapping("helloWorld")
    public DemoDto helloWorld(){
        return new DemoDto("Hello world");
    }
}
