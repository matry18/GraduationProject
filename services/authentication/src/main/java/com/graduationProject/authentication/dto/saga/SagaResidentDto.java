package com.graduationProject.authentication.dto.saga;

import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.entity.Resident;


public class SagaResidentDto {

    private String sagaId;
    private ResidentDto residentDto;

    public SagaResidentDto() {

    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public ResidentDto getResidentDto() {
        return residentDto;
    }

    public void setResidentDto(ResidentDto residentDto) {
        this.residentDto = residentDto;
    }

    @Override
    public String toString() {
        return "SagaResidentDto{" +
                "sagaId='" + sagaId + '\'' +
                ", residentDto=" + residentDto.toString() +
                '}';
    }
}
