package com.graduationproject.ochestrator.TestFixtures;

import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.type.SagaStatus;

public class SagaResponseDtoFixture {

    private String sagaId = "id-1234";
    private SagaStatus sagaStatus = SagaStatus.SUCCESS;

    public static SagaResponseDto createSagaResponseDto() {
        return builder().build();
    }

    public static SagaResponseDtoFixture builder() {
        return new SagaResponseDtoFixture();
    }

    public SagaResponseDto build() {
        SagaResponseDto sagaResponseDto = new SagaResponseDto();
        sagaResponseDto.setSagaId(sagaId);
        sagaResponseDto.setSagaStatus(sagaStatus);

        return sagaResponseDto;
    }

    public SagaResponseDtoFixture setSagaId(String sagaId) {
        this.sagaId = sagaId;
        return this;
    }

    public SagaResponseDtoFixture setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
        return this;
    }
}
