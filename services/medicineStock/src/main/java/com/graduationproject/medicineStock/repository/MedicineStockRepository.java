package com.graduationproject.medicineStock.repository;

import com.graduationproject.medicineStock.entity.MedicineStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineStockRepository extends JpaRepository<MedicineStock, String> {
    public boolean existsMedicineStocksById(String id); // do I need this?
}
