package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class controller {

    @GetMapping("helloWorld")
    public DemoDto helloWorld(){
        return new DemoDto("Hello world");
    }
}
