package com.graduationproject.medicineStock.dto;

import com.graduationproject.medicineStock.entity.MedicineStock;

public class MedicineStockDto {

    private String id;
    private String name;
    private int amount;

    public MedicineStockDto(){}

    public MedicineStockDto(String id, String name, int amount){
        this.id = id;
        this.id = name;
        this.amount = amount;
    }

    public MedicineStockDto(MedicineStock medicineStock) {
        this.id = medicineStock.getId();
        this.name = medicineStock.getName();
        this.amount = medicineStock.getAmount();
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
