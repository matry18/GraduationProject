package com.graduationproject.medicineStock.saga.sagaInitiators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.medicineStock.dto.MedicineStockDto;
import com.graduationproject.medicineStock.dto.saga.SagaMedicineStockDto;
import com.graduationproject.medicineStock.kafka.KafkaApi;
import com.graduationproject.medicineStock.repository.MedicineStockRepository;
import com.graduationproject.medicineStock.saga.SagaInitiator;
import com.graduationproject.medicineStock.topic.MedicineStockTopics;
import org.springframework.stereotype.Service;

//import static com.graduationproject.bosted.topic.EmployeeTopics.UpdateEmployeeSagaRevert;
import static com.graduationproject.medicineStock.topic.MedicineStockTopics.UpdateMedicineStockSagaRevert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UpdateMedicineStock implements SagaInitiator<MedicineStockDto> {

    private final KafkaApi kafkaApi;
    private final MedicineStockRepository medicineStockRepository;

    public UpdateMedicineStock(KafkaApi kafkaApi, MedicineStockRepository medicineStockRepository) {
        this.kafkaApi = kafkaApi;
        this.medicineStockRepository = medicineStockRepository;
    }

    @Override
    public void initSaga(MedicineStockDto medicineStockDto) {
        throw new UnsupportedOperationException("This method cannot be user for update Saga!");
    }

    @Override
    public void initSaga(MedicineStockDto oldMedicineStock, MedicineStockDto newMedicineStock) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MedicineStockDto> medicineStock = new ArrayList<>(Arrays.asList(oldMedicineStock, newMedicineStock));
        try {
            kafkaApi.publish(MedicineStockTopics.UpdateMedicineStockSagaInit, objectMapper.writeValueAsString(medicineStock));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert(MedicineStockDto object, String sagaId) {

    }
}
