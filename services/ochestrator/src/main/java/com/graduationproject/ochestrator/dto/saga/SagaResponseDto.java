package com.graduationproject.ochestrator.dto.saga;

import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.type.SagaStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SagaResponseDto {

    private String sagaId;
    private String serviceName = "orchestrator";
    private SagaStatus sagaStatus;
    private String errorMessage;
    private LocalDateTime receivingTime;

    public SagaResponseDto(String sagaId, String serviceName, SagaStatus sagaStatus, String errorMessage ) {
        this.sagaId = sagaId;
        this.serviceName = serviceName;
        this.sagaStatus = sagaStatus;
        this.errorMessage = errorMessage;
    }

    public SagaResponseDto(String sagaId, SagaStatus sagaStatus) {
        this.sagaId = sagaId;
        this.sagaStatus = sagaStatus;
        errorMessage = "";
    }
    public SagaResponseDto() {

    }

    public SagaResponseDto(SagaResponse sagaResponse) {
        this.sagaId = sagaResponse.getSagaId();
        this.serviceName = sagaResponse.getServiceName();
        this.sagaStatus = sagaResponse.getSagaStatus();
        this.errorMessage = sagaResponse.getErrorMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.receivingTime = sagaResponse.getReceivingTime();
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getReceivingTime() {
        return receivingTime;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "SagaResponseDto{" +
                "sagaId='" + sagaId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", sagaStatus=" + sagaStatus +
                '}';
    }
}
