package com.graduationproject.ochestrator.service;

import com.graduationproject.ochestrator.entities.SagaResponse;
import com.graduationproject.ochestrator.repository.SagaResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SagaResponseService {

    private final SagaResponseRepository sagaResponseRepository;

    @Autowired
    public SagaResponseService(SagaResponseRepository sagaResponseRepository) {
        this.sagaResponseRepository = sagaResponseRepository;
    }

    public void addSagaResponse(SagaResponse sagaResponse) {
        sagaResponseRepository.save(sagaResponse);
    }

    public void deleteSagaResponseById(String sagaId) {
        sagaResponseRepository.deleteBySagaId(sagaId);
    }

    public boolean hasEveryServiceGivenASagaResponse(List<String> services, String sagaId) {
        return services.size() == sagaResponseRepository.countDistinctByServiceNameInAndAndSagaId(services, sagaId);
    }

    public List<SagaResponse> fetchAllSagaResponses() {
        return sagaResponseRepository.findAll();
    }

}
