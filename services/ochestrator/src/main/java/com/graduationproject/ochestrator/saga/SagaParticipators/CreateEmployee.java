package com.graduationproject.ochestrator.saga.SagaParticipators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.DepartmentDto;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.saga.SagaEmployeeDto;
import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.DepartmentRepository;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.CreateEmployeeSagaBegin;
import static com.graduationproject.ochestrator.topic.employee.EmployeeTopics.CreateEmployeeSagaFailed;

@Service
public class CreateEmployee implements SagaParticipator<EmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final KafkaApi kafkaApi;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public CreateEmployee(EmployeeRepository employeeRepository, KafkaApi kafkaApi, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.kafkaApi = kafkaApi;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void saveDepartment(DepartmentDto departmentDto, String sagaId) {
        Department department = new Department(departmentDto);
        department.setSagaId(sagaId);
        departmentRepository.save(department);
    }

    @Override
    public void transact(EmployeeDto oldEmployeeDto, EmployeeDto newEmployeeDto) {
        throw new UnsupportedOperationException("Not implemented for this Saga!");
    }

    @Transactional
    @Override
    public void transact(EmployeeDto employeeDto) {
        //Create sagaId and the employee and sagaId to repo and publish Kafka
        String sagaId = UUID.randomUUID().toString();
        saveDepartment(employeeDto.getDepartment(), sagaId);
        Employee employee = employeeRepository.save(new Employee(employeeDto, sagaId)); //Creates the saga that will be used by the services when responding
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employee); // the dto that will be sent to the services so they know which saga they are part of
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaApi.publish(CreateEmployeeSagaBegin, objectMapper.writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void transact(String sagaId) {
        //this will be run after a successful saga
        Employee employee = employeeRepository.findEmployeeBySagaId(sagaId);
        employeeRepository.deleteBySagaId(sagaId);
        if (employeeRepository.countByDepartment(employee.getDepartment()) == 0) {
            departmentRepository.deleteBySagaId(sagaId);
        }

    }

    @Transactional
    @Override
    public void revert(String sagaId) {
        SagaEmployeeDto sagaEmployeeDto = new SagaEmployeeDto(employeeRepository.findEmployeeBySagaId(sagaId));
        try {
            kafkaApi.publish(CreateEmployeeSagaFailed, new ObjectMapper().writeValueAsString(sagaEmployeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
