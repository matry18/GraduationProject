package com.graduationproject.ochestrator.entities;

import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.type.SagaStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class SagaResponse {
    @Id
    private String id;

    private String sagaId;

    private String serviceName;

    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;

    private LocalDateTime receivingTime;

    @Lob
    private String errorMessage;

    public SagaResponse(){
    }

    public SagaResponse(SagaResponseDto sagaResponseDto) {
        this.id = UUID.randomUUID().toString();
        this.serviceName = sagaResponseDto.getServiceName();
        this.sagaStatus = sagaResponseDto.getSagaStatus();
        this.sagaId = sagaResponseDto.getSagaId();
        this.receivingTime = LocalDateTime.now();
        this.errorMessage = sagaResponseDto.getErrorMessage();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public LocalDateTime getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(LocalDateTime receivingTime) {
        this.receivingTime = receivingTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
