package com.graduationProject.authentication.dto.saga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.graduationProject.authentication.dto.EmployeeDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SagaEmployeeDto {

    private String sagaId;
    private EmployeeDto employeeDto;

    public SagaEmployeeDto() {

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

    @Override
    public String toString() {
        return "SagaEmployeeDto{" +
                "sagaId='" + sagaId + '\'' +
                ", employeeDto=" + employeeDto.toString() +
                '}';
    }
}
