package com.graduationproject.bosted.webservice;

import com.graduationproject.bosted.dto.ResidentDto;
import com.graduationproject.bosted.entity.Resident;
import com.graduationproject.bosted.kafka.KafkaAPI;
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

    @Autowired
    public ResidentWebService(ResidentService residentService, ResidentRepository residentRepository, KafkaAPI kafkaAPI) {
        this.residentService = residentService;
        this.residentRepository = residentRepository;
    }

    @GetMapping("bosted/citizen/{id}")
    public ResidentDto getCitizen(@PathVariable String id) {
        Resident resident = residentRepository.getById(id);
        return new ResidentDto(resident);
    }

    @PostMapping("bosted/citizen")
    public ResidentDto createCitizen(@RequestBody ResidentDto residentDto) {
        residentService.addCitizen(residentDto);
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

    @GetMapping("bosted/residentCount")
    public long getResidentCount() {
        return this.residentService.getResidentCount();
    }

}
