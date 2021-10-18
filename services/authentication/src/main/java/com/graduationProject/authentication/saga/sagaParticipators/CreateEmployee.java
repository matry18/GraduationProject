package com.graduationProject.authentication.saga.sagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationProject.authentication.dto.saga.SagaEmployeeDto;
import com.graduationProject.authentication.dto.saga.SagaResponseDto;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.saga.SagaParticipator;
import com.graduationProject.authentication.service.EmployeeService;
import com.graduationProject.authentication.topic.EmployeeTopic;
import com.graduationProject.authentication.type.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.graduationProject.authentication.topic.EmployeeTopic.CreateEmployeeSagaDone;
import static com.graduationProject.authentication.topic.EmployeeTopic.CreateEmployeeSagaRevert;

@Service
public class CreateEmployee implements SagaParticipator<SagaEmployeeDto> {

    private final EmployeeService employeeService;
    private final KafkaApi kafkaApi;

    @Autowired
    public CreateEmployee(EmployeeService employeeService, KafkaApi kafkaApi) {
        this.employeeService = employeeService;
        this.kafkaApi = kafkaApi;
    }

    @Transactional
    @Override
    public void transact(SagaEmployeeDto sagaEmployeeDto) {
        try {

            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(),
                    SagaStatus.SUCCESS);
            if (sagaEmployeeDto.getEmployeeDto().getUsername().equals("fail")) {
                throw new Exception();
            }
            employeeService.addEmployee(sagaEmployeeDto.getEmployeeDto());
            kafkaApi.publish(CreateEmployeeSagaDone, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(),
                    SagaStatus.FAILED);
            try {
                kafkaApi.publish(CreateEmployeeSagaDone, new ObjectMapper().writeValueAsString(sagaResponseDto));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Transactional
    @Override
    public void revert(SagaEmployeeDto sagaEmployeeDto) {
        try {
            if (sagaEmployeeDto.getEmployeeDto().getPassword().equals("fail")) {
                throw new Exception();
            }

            if (employeeService.employeeExists(sagaEmployeeDto.getEmployeeDto().getId())) {
                employeeService.deleteEmployee(sagaEmployeeDto.getEmployeeDto().getId());
            }

            kafkaApi.publish(CreateEmployeeSagaRevert, new ObjectMapper()
                    .writeValueAsString(new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.SUCCESS)));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                kafkaApi.publish(CreateEmployeeSagaRevert, new ObjectMapper()
                        .writeValueAsString(new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.FAILED)));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
