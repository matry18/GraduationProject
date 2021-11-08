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

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.DeleteEmployeeSagaBegin;
import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.DeleteEmployeeSagaFailed;

@Service
public class DeleteEmployee implements SagaParticipator<EmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final EmployeeSagaService employeeSagaService;
    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public DeleteEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi, EmployeeSagaService employeeSagaService, SagaResponseRepository sagaResponseRepository) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.employeeSagaService = employeeSagaService;
        this.sagaResponseRepository = sagaResponseRepository;
    }

    @Override
    public String transact(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this saga!");
    }

    @Transactional
    @Override
    public String transact(EmployeeDto employeeDto) {
        SagaEmployeeDto sagaEmployeeDto = employeeSagaService.backupEmployee(employeeDto);
        try {
            if (sagaEmployeeDto.getEmployeeDto().getUsername().equals("faildelete")) {
                throw new IllegalStateException(String.format("Could not delete Employee with \n ID: %s \n SagaID: %s",
                        sagaEmployeeDto.getEmployeeDto().getId(),
                        sagaEmployeeDto.getSagaId()));
            }
            kafkaApi.publish(DeleteEmployeeSagaBegin, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (Exception e) {
            SagaResponseDto sagaResponseDto = new SagaResponseDto(sagaEmployeeDto.getSagaId(),
                    SagaStatus.FAILED);
            sagaResponseDto.setErrorMessage(ExceptionUtils.getStackTrace(e));
            sagaResponseRepository.save(new SagaResponse(sagaResponseDto));
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
            kafkaApi.publish(DeleteEmployeeSagaFailed, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
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
