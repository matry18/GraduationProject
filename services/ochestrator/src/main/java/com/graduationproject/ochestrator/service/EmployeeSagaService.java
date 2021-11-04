package com.graduationproject.ochestrator.service;

import com.graduationproject.ochestrator.dto.AccessRightDto;
import com.graduationproject.ochestrator.dto.DepartmentDto;
import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.dto.RoleDto;
import com.graduationproject.ochestrator.dto.saga.SagaEmployeeDto;
import com.graduationproject.ochestrator.entities.AccessRight;
import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.entities.Role;
import com.graduationproject.ochestrator.kafka.KafkaApi;
import com.graduationproject.ochestrator.repository.AccessRightRepository;
import com.graduationproject.ochestrator.repository.DepartmentRepository;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeSagaService {
    private final KafkaApi kafkaApi;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final AccessRightRepository accessRightRepository;

    @Autowired
    public EmployeeSagaService(KafkaApi kafkaApi, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository, AccessRightRepository accessRightRepository) {
        this.kafkaApi = kafkaApi;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.accessRightRepository = accessRightRepository;
    }

    public List<Employee> fetchAllSagaEmployees() {
        return employeeRepository.findAll();
    }

    public long getEmployeesCount() {
        return employeeRepository.count();
    }

    private void saveDepartment(DepartmentDto departmentDto, String sagaId) {
        Department department = new Department(departmentDto);
        department.setSagaId(sagaId);
        departmentRepository.save(department);
    }

    private void saveAccessRights(AccessRightDto accessRightDto, String sagaId) {
        AccessRight accessRight = new AccessRight(accessRightDto);
        accessRight.setSagaId(sagaId);
        accessRightRepository.save(accessRight);
    }

    private void saveRole(RoleDto roleDto, String sagaId) {
        roleDto.getAccessRights().forEach(accessRightDto -> {
            saveAccessRights(accessRightDto, sagaId);
        });
        Role role = new Role(roleDto);
        role.setSagaId(sagaId);
        roleRepository.save(role);
    }

    public SagaEmployeeDto backupEmployee(EmployeeDto employeeDto) {
        String sagaId = UUID.randomUUID().toString();
        setupEmployeeDataForTransaction(employeeDto, sagaId);
        Employee employee = employeeRepository.save(new Employee(employeeDto, sagaId)); //Creates the saga that will be used by the services when responding
        return new SagaEmployeeDto(employee);
    }

    @Transactional
    public void setupEmployeeDataForTransaction(EmployeeDto employeeDto, String sagaId) {
        saveDepartment(employeeDto.getDepartment(), sagaId);
        saveRole(employeeDto.getRoleDto(), sagaId);
    }

    public void deleteEmployeeDataForTransaction(String sagaId) {
        if (employeeRepository.existsEmployeeByRole(roleRepository.findBySagaId(sagaId))) {
            return;
        }

        if (roleRepository.existsRoleByAccessRightsIn(accessRightRepository.getAccessRightBySagaId(sagaId))) {
            return;
        }
        roleRepository.deleteBySagaId(sagaId);
        accessRightRepository.deleteBySagaId(sagaId);
    }
}
