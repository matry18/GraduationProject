package com.graduationproject.ochestrator.webservices;

import com.graduationproject.ochestrator.dto.saga.SagaResidentDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.service.ResidentSagaService;
import com.graduationproject.ochestrator.service.SagaResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class SagaResponseWebService {

    private final SagaResponseService sagaResponseService;
    private final ResidentSagaService residentSagaService;

    @Autowired
    public SagaResponseWebService(SagaResponseService sagaResponseService, ResidentSagaService residentSagaService) {
        this.sagaResponseService = sagaResponseService;
        this.residentSagaService = residentSagaService;
    }

    @GetMapping("orchestrator/saga-responses")
    public List<SagaResponseDto> getAllSagaResponses() {
        List<SagaResponseDto> responseDtoList =  sagaResponseService.fetchAllSagaResponses().stream()
                .map(SagaResponseDto::new)
                .collect(Collectors.toList());
        return responseDtoList;
    }

    @GetMapping("orchestrator/saga-residents")
    public List<SagaResidentDto> getAllSagaResidents() {
        List<SagaResidentDto> sagaResidentDto = residentSagaService.fetchAllSagaResidents().stream()
                .map(SagaResidentDto::new)
                .collect(Collectors.toList());
        return sagaResidentDto;
    }
}
