package com.graduationproject.medicineStock.entity;

import com.graduationproject.medicineStock.dto.MedicineStockDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MedicineStock {

    @Id
    private String id;

    @Column(unique = true)
    private String name;

    private int amount;

    public MedicineStock(){
    }

    public MedicineStock(MedicineStockDto medicineStockDto) {
        this.id = medicineStockDto.getId();
        this.name = medicineStockDto.getName();
        this.amount = medicineStockDto.getAmount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MedicineStockDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
