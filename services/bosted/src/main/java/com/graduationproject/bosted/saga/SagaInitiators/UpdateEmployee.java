package com.graduationproject.bosted.saga.SagaInitiators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.EmployeeDto;
import com.graduationproject.bosted.dto.saga.SagaResponseDto;
import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.entity.Employee;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.saga.SagaInitiator;
import com.graduationproject.bosted.topic.EmployeeTopics;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.graduationproject.bosted.type.SagaStatus.SUCCESS;
import static com.graduationproject.bosted.type.SagaStatus.FAILED;
import static com.graduationproject.bosted.topic.EmployeeTopics.UpdateEmployeeSagaRevert;

@Service
public class UpdateEmployee implements SagaInitiator<EmployeeDto> {

    private final KafkaAPI kafkaAPI;
    private final EmployeeRepository employeeRepository;

    public UpdateEmployee(KafkaAPI kafkaAPI, EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.kafkaAPI = kafkaAPI;
    }


    @Override
    public void initSaga(EmployeeDto employeeDto) {
        throw new UnsupportedOperationException("This method cannot be user for update Saga!");

    }

    @Override
    public void initSaga(EmployeeDto oldEmployee, EmployeeDto newEmployee) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<EmployeeDto> employees = new ArrayList<>(Arrays.asList(oldEmployee, newEmployee));
        try {
            kafkaAPI.publish(EmployeeTopics.UpdateEmployeeSagaInit, objectMapper.writeValueAsString(employees));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void revert(EmployeeDto employeeDto, String sagaId) {
        SagaResponseDto sagaResponseDto;
        try {
            employeeRepository.save(new Employee(employeeDto));
            sagaResponseDto = new SagaResponseDto(sagaId, SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaId, FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        produceKafkaMessage(UpdateEmployeeSagaRevert, sagaResponseDto);
    }

    private void produceKafkaMessage(String employeeTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaAPI.publish(employeeTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
