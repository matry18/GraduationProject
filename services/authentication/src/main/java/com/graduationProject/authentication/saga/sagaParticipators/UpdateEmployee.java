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
import com.graduationProject.authentication.service.EmployeeService;
import com.graduationProject.authentication.topic.EmployeeTopic;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationProject.authentication.type.SagaStatus.FAILED;
import static com.graduationProject.authentication.type.SagaStatus.SUCCESS;

@Service
public class UpdateEmployee implements SagaParticipator<SagaEmployeeDto> {

    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final EmployeeService employeeService;

    @Autowired
    public UpdateEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.employeeService = employeeService;
    }

    @Transactional
    @Override
    public void transact(SagaEmployeeDto sagaEmployeeDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaEmployeeDto.getEmployeeDto().getFirstname().equals("updateFail")) {
                throw new IllegalStateException(String.format("Could not update Employee with \n ID: %s \n SagaID: %s",
                        sagaEmployeeDto.getEmployeeDto().getId(),
                        sagaEmployeeDto.getSagaId()));
            }
            employeeService.addEmployee(sagaEmployeeDto.getEmployeeDto());
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SUCCESS);
        }catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
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
                throw new IllegalStateException(String.format("Could not revert update of Employee with \n ID: %s \n SagaID: %s",
                        sagaEmployeeDto.getEmployeeDto().getId(),
                        sagaEmployeeDto.getSagaId()));
            }
            Employee employee = employeeRepository.getById(sagaEmployeeDto.getEmployeeDto().getId());
            updateEmployee(employee, sagaEmployeeDto.getEmployeeDto());
            employeeRepository.save(employee);
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
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
