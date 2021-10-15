package com.graduationProject.authentication.saga.sagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.EmployeeDto;
import com.graduationProject.authentication.dto.saga.SagaEmployeeDto;
import com.graduationProject.authentication.dto.saga.SagaResponseDto;
import com.graduationProject.authentication.entity.Employee;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.repository.EmployeeRepository;
import com.graduationProject.authentication.saga.SagaParticipator;
import com.graduationProject.authentication.topic.EmployeeTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationProject.authentication.type.SagaStatus.FAILED;
import static com.graduationProject.authentication.type.SagaStatus.SUCCESS;

@Service
public class UpdateEmployee implements SagaParticipator<SagaEmployeeDto> {

    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;

    @Autowired
    public UpdateEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
    }

    @Transactional
    @Override
    public void transact(SagaEmployeeDto sagaEmployeeDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaEmployeeDto.getEmployeeDto().getFirstname().equals("updateFail")) {
                throw new Exception();
            }
            Employee employee = employeeRepository.getById(sagaEmployeeDto.getEmployeeDto().getId());
            updateEmployee(employee, sagaEmployeeDto.getEmployeeDto());
            employeeRepository.save(employee);
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SUCCESS);
        }catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), FAILED);
            e.printStackTrace();
        }

        produceKafkaMessage(EmployeeTopic.UpdateEmployeeSagaDone, sagaResponseDto);
    }

    @Transactional
    @Override
    public void revert(SagaEmployeeDto sagaEmployeeDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaEmployeeDto.getEmployeeDto().getLastname().equals("updateFail")) {
                throw new Exception();
            }
            Employee employee = employeeRepository.getById(sagaEmployeeDto.getEmployeeDto().getId());
            updateEmployee(employee, sagaEmployeeDto.getEmployeeDto());
            employeeRepository.save(employee);
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), FAILED);
            e.printStackTrace();
        }
        produceKafkaMessage(EmployeeTopic.UpdateEmployeeSagaRevert, sagaResponseDto);
    }

    private void updateEmployee(Employee employee, EmployeeDto employeeDto) {
        employee.setUsername(employeeDto.getUsername());
        employee.setPassword(employeeDto.getPassword());
    }

    private void produceKafkaMessage(String employeeTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaApi.publish(employeeTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
