package com.graduationproject.medicineStock.dto.saga;

import com.graduationproject.medicineStock.dto.MedicineStockDto;

public class SagaMedicineStockDto {

    private String sagaId;
    private MedicineStockDto medicineStockDto;

    public SagaMedicineStockDto() {}

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public MedicineStockDto getMedicineStockDto() {
        return medicineStockDto;
    }

    public void setMedicineStockDto(MedicineStockDto medicineStockDto) {
        this.medicineStockDto = medicineStockDto;
    }

    @Override
    public String toString() {
        return "SagaMedicineStockDto{" +
                "sagaId='" + sagaId + '\'' +
                ", medicineStockDto=" + medicineStockDto.toString() +
                '}';
    }
}
