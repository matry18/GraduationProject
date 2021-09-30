package com.graduationproject.ochestrator.dto.saga;

import com.graduationproject.ochestrator.topic.SagaStatus;

public class SagaResponseDto {

    private String sagaId;
    private String serviceName;
    private SagaStatus sagaStatus;

    public SagaResponseDto(String sagaId, String serviceName, SagaStatus sagaStatus) {
        this.sagaId = sagaId;
        this.serviceName = serviceName;
        this.sagaStatus = sagaStatus;
    }

    public SagaResponseDto() {

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

    @Override
    public String toString() {
        return "SagaResponseDto{" +
                "sagaId='" + sagaId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", sagaStatus=" + sagaStatus +
                '}';
    }
}
