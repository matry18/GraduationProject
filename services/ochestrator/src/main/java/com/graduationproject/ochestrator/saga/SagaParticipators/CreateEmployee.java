package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaEmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.EmployeeSagaService;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.*;

@Service
public class CreateEmployee implements SagaParticipator<EmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final EmployeeSagaService employeeSagaService;
    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public CreateEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi, EmployeeSagaService employeeSagaService, SagaResponseRepository sagaResponseRepository) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.employeeSagaService = employeeSagaService;
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @Override
    public String transact(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this Saga!");
    }

    @Transactional
    @Override
    public String transact(EmployeeDto employeeDto) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto("not set", employeeDto);
        try {
            if (employeeDto.getUsername().equals("failcreate")) {
                throw new IllegalStateException(String.format("Could not backup or broadcast Employee with \n ID: %s \n SagaID: %s",
                        employeeDto.getId(),
                        "not set"));
            }
            sagaEmployeeDto = employeeSagaService.backupEmployee(employeeDto);
            kafkaApi.publish(CreateEmployeeSagaBegin, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(),
                    SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
            try {
                kafkaApi.publish(CreateEmployeeSagaInitRevert, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
        return sagaEmployeeDto.getSagaId();
    }

    @Transactional
    public void transact(String sagaId) {
        try {
            //this will be run after a successful saga
            employeeRepository.deleteBySagaId(sagaId);
            employeeSagaService.deleteEmployeeDataForTransaction(sagaId);
        } catch (Exception e) {
            handleException(e, sagaId);
        }
    }

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employeeRepository.findEmployeeBySagaId(sagaId));
        try {
            kafkaApi.publish(CreateEmployeeSagaFailed, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (Exception e) {
            handleException(e, sagaId);
        }
    }

    private void handleException(Exception e, String sagaId) {
        SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaId, SagaStatus.FAILED);
        sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
        sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
    }
}
