package com.graduationproject.bosted.service;

import com.graduationproject.bosted.dto.EmployeeDto;
import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.entity.Employee;
import com.graduationproject.bosted.entity.Role;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.saga.SagaInitiators.CreateEmployee;
import com.graduationproject.bosted.saga.SagaInitiators.DeleteEmployee;
import com.graduationproject.bosted.saga.SagaInitiators.UpdateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CreateEmployee createEmployee;
    private final UpdateEmployee updateEmployee;
    private final DeleteEmployee deleteEmployee;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CreateEmployee createEmployee, UpdateEmployee updateEmployee, DeleteEmployee deleteEmployee) {
        this.employeeRepository = employeeRepository;
        this.createEmployee = createEmployee;
        this.updateEmployee = updateEmployee;
        this.deleteEmployee = deleteEmployee;
    }

    public void addEmployee(EmployeeDto employeeDto) {
        employeeDto.setId(UUID.randomUUID().toString());
        employeeDto.hashPassword();
        employeeRepository.save(new Employee(employeeDto));
        createEmployee.initSaga(employeeDto);
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee editEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId()).orElse(null);
        EmployeeDto oldEmployeeDto = new EmployeeDto(employee);
        employee.setFirstname(employeeDto.getFirstname());
        employee.setLastname(employeeDto.getLastname());
        employee.setDepartment(new Department(employeeDto.getDepartment()));
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setRole(new Role(employeeDto.getRoleDto()));
        employeeRepository.save(employee);
        EmployeeDto newEmployeeDto = new EmployeeDto(employee);
        updateEmployee.initSaga(oldEmployeeDto, newEmployeeDto);
        return employee;
    }

    public Employee deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.getById(employeeId);
        employeeRepository.deleteById(employeeId);
        deleteEmployee.initSaga(new EmployeeDto(employee));
        return employee;
    }

    public long getEmployeeCount() {
        return this.employeeRepository.count();
    }

}
