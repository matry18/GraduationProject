package com.example.demo;

import com.example.demo.Model.Department;
import com.example.demo.Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("demo")
public class controller {

    // Lists all the different producers / services
    private final Producer producer;
    private final Producer99 producer99;

    // Add each producer / service to the Controller
        @Autowired
        public controller(Producer producer, Producer99 producer99) {
            this.producer = producer;
            this.producer99 = producer99;
        }
    // Method for POST request for a specific producer / service
    @PostMapping("/publish")
    public void messageToTopic(@RequestParam("message") String message){
        this.producer.sendMessage(message);
    }

    // Method for GET request for a specific producer / service
    @GetMapping("/user/{firstName}/{lastName}/{age}")
    public ResponseEntity<String> user(@PathVariable("firstName") final String firstName,
                                       @PathVariable("lastName") final String lastName,
                                       @PathVariable("age") final int age){
        try {
            this.producer.newUser(new User(firstName, lastName, age));
            return new ResponseEntity<>("Published successfully!", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Published failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method for GET request for a specific producer / service
    @GetMapping("/department/{departmentName}")
    public ResponseEntity<String> department(@PathVariable("departmentName") final String departmentName){
        try {
            this.producer99.newDepartment(new Department(departmentName, UUID.randomUUID()));
            return new ResponseEntity<>("Succesfull department!", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Department failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("helloWorld")
    public DemoDto helloWorld(){
        return new DemoDto("Hello world");
    }
}
