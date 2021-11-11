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
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.transaction.Transactional;

import static com.graduationProject.authentication.topic.EmployeeTopic.CreateEmployeeSagaDone;
import static com.graduationProject.authentication.topic.EmployeeTopic.CreateEmployeeSagaRevert;
import static java.util.Objects.nonNull;

@Service
public class CreateEmployee implements SagaParticipator<SagaEmployeeDto> {

    private final EmployeeService employeeService;
    private final KafkaApi kafkaApi;

    @Autowired
    public CreateEmployee(EmployeeService employeeService, KafkaApi kafkaApi) {
        this.employeeService = employeeService;
        this.kafkaApi = kafkaApi;
    }

    @Override
    public void transact(SagaEmployeeDto sagaEmployeeDto) {
        try {
            if ((sagaEmployeeDto.getEmployeeDto().getUsername().equals("fail")) || (sagaEmployeeDto.getEmployeeDto().getUsername().equals("failfail"))) {
                throw new IllegalStateException(String.format("Could not create Employee with \n ID: %s \n SagaID: %s",
                        sagaEmployeeDto.getEmployeeDto().getId(),
                        sagaEmployeeDto.getSagaId()));
            }
            employeeService.addEmployee(sagaEmployeeDto.getEmployeeDto());
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(),
                    SagaStatus.SUCCESS);
            kafkaApi.publish(CreateEmployeeSagaDone, new ObjectMapper().writeValueAsString(sagaResponseDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(),
                    SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
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
            if (nonNull(sagaEmployeeDto.getEmployeeDto().getUsername()) && sagaEmployeeDto.getEmployeeDto().getUsername().equals("failfail")) {
                throw new IllegalStateException(String.format("Could not revert creation of Employee with \n ID: %s \n SagaID: %s",
                        sagaEmployeeDto.getEmployeeDto().getId(),
                        sagaEmployeeDto.getSagaId()));
            }
            if (employeeService.employeeExists(sagaEmployeeDto.getEmployeeDto().getId())) {
                employeeService.deleteEmployee(sagaEmployeeDto.getEmployeeDto().getId());
            }
            kafkaApi.publish(CreateEmployeeSagaRevert, new ObjectMapper()
                    .writeValueAsString(new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.SUCCESS)));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(), SagaStatus.FAILED);
                sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
                kafkaApi.publish(CreateEmployeeSagaRevert, new ObjectMapper()
                        .writeValueAsString(sagaResponseDto));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
