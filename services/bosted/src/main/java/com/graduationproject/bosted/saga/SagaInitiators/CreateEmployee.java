package com.graduationproject.bosted.saga.SagaInitiators;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.EmployeeDto;
import com.graduationproject.bosted.dto.saga.SagaResponseDto;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.saga.SagaInitiator;
import com.graduationproject.bosted.type.SagaStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.graduationproject.bosted.topic.EmployeeTopics.CreateEmployeeSagaInit;
import static com.graduationproject.bosted.topic.EmployeeTopics.CreateEmployeeSagaRevert;

@Service
public class CreateEmployee implements SagaInitiator<EmployeeDto> {

    private final KafkaAPI kafkaAPI;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CreateEmployee(KafkaAPI kafkaAPI, EmployeeRepository employeeRepository){
        this.kafkaAPI = kafkaAPI;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void initSaga(EmployeeDto employeeDto) {
        System.out.println("BOSTED CREATE SAGA INIT");
        try {
            kafkaAPI.publish(CreateEmployeeSagaInit, new ObjectMapper().writeValueAsString(employeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initSaga(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this saga!");
    }

    @Override
    public void revert(EmployeeDto employeeDto, String sagaId) {
        SagaResponseDto sagaResponseDto;
        try {
            if (employeeRepository.existsEmployeeById(employeeDto.getId())) {
                employeeRepository.deleteById(employeeDto.getId());
            }
            sagaResponseDto = new SagaResponseDto(sagaId, SagaStatus.SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaId, SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        try {
            kafkaAPI.publish(CreateEmployeeSagaRevert, new ObjectMapper()
                    .writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
