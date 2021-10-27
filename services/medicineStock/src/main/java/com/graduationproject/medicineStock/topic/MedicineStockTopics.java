package com.graduationproject.medicineStock.topic;

public interface MedicineStockTopics {
    // UPDATE MEDICINE STOCK
    static final String UpdateMedicineStockSagaInit = "UpdateMedicineStockSagaInit";
    static final String UpdateMedicineStockSagaBegin = "UpdateMedicineStockSagaBegin";
    static final String UpdateMedicineStockSagaFailed = "UpdateMedicineStockSagaFailed";
    static final String UpdateMedicineStockSagaRevert = "UpdateMedicineStockSagaRevert";
    static final String UpdateMedicineStockSagaDone = "UpdateMedicineStockSagaDone";
}
