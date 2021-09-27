package com.graduationproject.bosted.webservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.producer.Producer;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class ResidentWebService {


    private final ResidentService residentService;
    private final ResidentRepository residentRepository;
    private final Producer producer;

    @Autowired
    public ResidentWebService(ResidentService residentService, ResidentRepository residentRepository, Producer producer) {
        this.residentService = residentService;
        this.residentRepository = residentRepository;
        this.producer = producer;
    }


    @GetMapping("bosted/citizen/{id}")
    public ResidentDto getPerson(@PathVariable String id) {
        return new ResidentDto(residentRepository.findById(id).orElse(null));
    }

    @PostMapping("bosted/citizen")
    public ResidentDto createCitizen(@RequestBody ResidentDto residentDto) {
        residentService.addCitizen(residentDto);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            producer.sendMessage(objectMapper.writeValueAsString(residentDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return residentDto;
    }

    @GetMapping("bosted/citizen")
    public List<ResidentDto> getAllCitizen() {
        return this.residentService.getAllCitizen().stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping("bosted/citizen")
    public ResidentDto editCitizen(@RequestBody ResidentDto citizen) {
        return new ResidentDto(residentService.editCitizen(citizen));
    }

    @DeleteMapping("bosted/citizen/{id}")
    public ResidentDto deleteCitizen(@PathVariable String id) {
        return new ResidentDto(residentService.deleteCitizen(id));
    }

}