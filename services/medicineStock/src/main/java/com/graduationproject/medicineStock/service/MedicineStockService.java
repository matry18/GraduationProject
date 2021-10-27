package com.graduationproject.medicineStock.service;

import com.graduationproject.medicineStock.dto.MedicineStockDto;
import com.graduationproject.medicineStock.entity.MedicineStock;
import com.graduationproject.medicineStock.repository.MedicineStockRepository;
import com.graduationproject.medicineStock.saga.sagaInitiators.CreateMedicineStock;
import com.graduationproject.medicineStock.saga.sagaInitiators.UpdateMedicineStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class MedicineStockService {

    private final MedicineStockRepository medicineStockRepository;
    private final CreateMedicineStock createMedicineStock;
    private final UpdateMedicineStock updateMedicineStock;

    @Autowired
    public MedicineStockService(MedicineStockRepository medicineStockRepository, CreateMedicineStock createMedicineStock, UpdateMedicineStock updateMedicineStock) {
        this.medicineStockRepository = medicineStockRepository;
        this.createMedicineStock = createMedicineStock;
        this.updateMedicineStock = updateMedicineStock;
    }

    public void addMedicineStock(MedicineStockDto medicineStockDto) {
        medicineStockDto.setId(UUID.randomUUID().toString());
        medicineStockRepository.save(new MedicineStock(medicineStockDto));
    }

    public List<MedicineStock> getAllMedicineStock() {
        return medicineStockRepository.findAll();
    }

    @Transactional
    public MedicineStock editMedicineStock(MedicineStockDto medicineStockDto) {
        MedicineStock medicineStock = medicineStockRepository.findById(medicineStockDto.getId()).orElse(null);
        MedicineStockDto oldMedicineStockDto = new MedicineStockDto(medicineStock);
        medicineStock.setName(medicineStockDto.getName());
        medicineStock.setAmount(medicineStockDto.getAmount());
        medicineStockRepository.save(medicineStock);
        MedicineStockDto newMedicineStockDto = new MedicineStockDto(medicineStock);
        updateMedicineStock.

    }

}
