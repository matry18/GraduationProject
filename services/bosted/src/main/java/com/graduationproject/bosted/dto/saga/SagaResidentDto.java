package com.graduationproject.bosted.dto.saga;

import com.graduationproject.bosted.dto.ResidentDto;

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
}
