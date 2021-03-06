package com.graduationProject.authentication.dto.saga;

import com.graduationProject.authentication.type.SagaStatus;

public class SagaResponseDto {
    private String sagaId;
    private String serviceName = "authentication";
    private SagaStatus sagaStatus;
    private String errorMessage;

    public SagaResponseDto(String sagaId, SagaStatus sagaStatus) {
        this.sagaId = sagaId;
        this.sagaStatus = sagaStatus;
        errorMessage = "";
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
