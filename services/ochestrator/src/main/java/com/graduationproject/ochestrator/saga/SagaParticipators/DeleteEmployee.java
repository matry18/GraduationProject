package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaEmployeeDto;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.DepartmentRepository;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.service.EmployeeSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.DeleteEmployeeSagaBegin;
import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.DeleteEmployeeSagaFailed;

@Service
public class DeleteEmployee implements SagaParticipator<EmployeeDto> {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final EmployeeSagaService employeeSagaService;

    @Autowired
    public DeleteEmployee(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, KafkaApi kafkaApi, EmployeeSagaService employeeSagaService) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.employeeSagaService = employeeSagaService;
    }




    @Override
    public void transact(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this saga!");
    }

    @Transactional
    @Override
    public void transact(EmployeeDto employeeDto) {
        //Create sagaId and the employee and sagaId to repo and publish Kafka
        String sagaId = UUID.randomUUID().toString();
        employeeSagaService.setupEmployeeDataForTransaction(employeeDto, sagaId);
        Employee employee = employeeRepository.save(new Employee(employeeDto, sagaId)); //Creates the saga that will be used by the services when responding
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employee); // the dto that will be sent to the services so they know which saga they are part of
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaApi.publish(DeleteEmployeeSagaBegin, objectMapper.writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        Employee employee = employeeRepository.findEmployeeBySagaId(sagaId);
        employeeRepository.deleteBySagaId(sagaId);
        employeeSagaService.deleteEmployeeDataForTransaction(sagaId);
    }

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employeeRepository.findEmployeeBySagaId(sagaId));
        try {
            kafkaApi.publish(DeleteEmployeeSagaFailed, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
