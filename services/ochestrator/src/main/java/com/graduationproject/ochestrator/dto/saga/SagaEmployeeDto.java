package com.graduationproject.ochestrator.dto.saga;

import com.graduationproject.ochestrator.dto.EmployeeDto;
import com.graduationproject.ochestrator.entities.Employee;

public class SagaEmployeeDto {

    private String sagaId;
    private EmployeeDto employeeDto;

    public SagaEmployeeDto(Employee employee) {
        this.sagaId = employee.getSagaId();
        this.employeeDto = new EmployeeDto(employee);
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public EmployeeDto getEmployeeDto() {
        return employeeDto;
    }

    public void setEmployeeDto(EmployeeDto employeeDto) {
        this.employeeDto = employeeDto;
    }
}
