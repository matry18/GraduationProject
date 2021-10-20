package com.graduationproject.bosted.dto.saga;

import com.graduationproject.bosted.dto.EmployeeDto;

public class SagaEmployeeDto {
    private String sagaId;
    private EmployeeDto employeeDto;

    public SagaEmployeeDto(){}

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
