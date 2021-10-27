package com.graduationproject.medicineStock.kafka.consumers;

import com.graduationproject.medicineStock.saga.sagaInitiators.UpdateMedicineStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateMedicineStockConsumer {

    private static final String GROUP_ID = "medicineStock";
    private final UpdateMedicineStock updateMedicineStock;

    @Autowired
    public UpdateMedicineStockConsumer(UpdateMedicineStock updateMedicineStock) {
        this.updateMedicineStock = updateMedicineStock;
    }

    //TODO
}
