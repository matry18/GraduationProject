package com.graduationproject.medicineStock.saga.sagaInitiators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.medicineStock.dto.saga.SagaMedicineStockDto;
import com.graduationproject.medicineStock.kafka.KafkaApi;
import com.graduationproject.medicineStock.repository.MedicineStockRepository;
import com.graduationproject.medicineStock.saga.SagaInitiator;

import org.springframework.stereotype.Service;

@Service
public class CreateMedicineStock implements SagaInitiator<SagaMedicineStockDto> {

    private final KafkaApi kafkaApi;
    private final MedicineStockRepository medicineStockRepository;

    public CreateMedicineStock(KafkaApi kafkaApi, MedicineStockRepository medicineStockRepository) {
        this.kafkaApi = kafkaApi;
        this.medicineStockRepository = medicineStockRepository;
    }

    @Override
    public void initSaga(SagaMedicineStockDto sagaMedicineStockDto) {
        throw new UnsupportedOperationException("Not implemented for this saga!");

    }

    @Override
    public void initSaga(SagaMedicineStockDto oldObject, SagaMedicineStockDto newObject) {
        throw new UnsupportedOperationException("Not implemented for this saga!");
    }

    @Override
    public void revert(SagaMedicineStockDto object, String sagaId) {
        throw new UnsupportedOperationException("Not implemented for this saga!");
    }
}
