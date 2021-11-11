package com.graduationproject.ochestrator.dto.saga;

import com.graduationproject.ochestrator.dto.ResidentDto;
import com.graduationproject.ochestrator.entities.Resident;

public class SagaResidentDto {

    private String sagaId;
    private ResidentDto residentDto;

    public SagaResidentDto(Resident resident) {
        this.sagaId = resident.getSagaId();
        this.residentDto = new ResidentDto(resident);
    }

    public SagaResidentDto(String sagaId, ResidentDto residentDto) {
        this.sagaId = sagaId;
        this.residentDto = residentDto;
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
