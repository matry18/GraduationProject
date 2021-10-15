package com.graduationProject.authentication.saga.sagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaEmployeeDto;
import com.graduationProject.authentication.dto.saga.SagaResponseDto;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.saga.SagaParticipator;
import com.graduationProject.authentication.service.EmployeeService;
import com.graduationProject.authentication.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.graduationProject.authentication.topic.EmployeeTopic.DeleteEmployeeSagaDone;
import static com.graduationProject.authentication.topic.EmployeeTopic.DeleteEmployeeSagaRevert;


@Service
public class DeleteEmployee implements SagaParticipator<SagaEmployeeDto> {

    private final EmployeeService employeeService;
    private final KafkaApi kafkaApi;

    @Autowired
    public DeleteEmployee(EmployeeService employeeService, KafkaApi kafkaApi) {
        this.employeeService = employeeService;
        this.kafkaApi = kafkaApi;
    }

    @Override
    public void transact(SagaEmployeeDto sagaEmployeeDto) {
        SagaResponseDto sagaResponseDto;
        try {
            employeeService.deleteEmployee(sagaEmployeeDto.getEmployeeDto().getId());
            if (sagaEmployeeDto.getEmployeeDto().getUsername().equals("neverDelete")) {
                throw new Exception();
            }
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.SUCCESS);

        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.FAILED);
            e.printStackTrace();
        }
        produceKafkaMessage(DeleteEmployeeSagaDone, sagaResponseDto);
    }

    @Override
    public void revert(SagaEmployeeDto sagaEmployeeDto) {
        SagaResponseDto sagaResponseDto;
        try {
            if (sagaEmployeeDto.getEmployeeDto().getPassword().equals("neverDelete")) {
                throw new Exception();
            }
            employeeService.addEmployee(sagaEmployeeDto.getEmployeeDto());
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.SUCCESS);
        } catch (Exception e) {
            sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.FAILED);
            e.printStackTrace();
        }
        produceKafkaMessage(DeleteEmployeeSagaRevert, sagaResponseDto);
    }

    private void produceKafkaMessage(String employeeTopic, SagaResponseDto sagaResponseDto) {
        try {
            kafkaApi.publish(employeeTopic, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
