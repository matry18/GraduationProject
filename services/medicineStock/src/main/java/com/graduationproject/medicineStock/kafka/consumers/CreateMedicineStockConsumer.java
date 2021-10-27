package com.graduationproject.medicineStock.kafka.consumers;

import com.graduationproject.medicineStock.saga.sagaInitiators.CreateMedicineStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateMedicineStockConsumer {

    private static final String GROUP_ID = "medicineStock";
    private final CreateMedicineStock createMedicineStock;

    @Autowired
    public CreateMedicineStockConsumer(CreateMedicineStock createMedicineStock) {
        this.createMedicineStock = createMedicineStock;
    }

    //TODO


}
