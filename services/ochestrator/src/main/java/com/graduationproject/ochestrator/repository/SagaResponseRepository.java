package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.SagaResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SagaResponseRepository extends JpaRepository<SagaResponse, String> {

    public void deleteBySagaId(String sagaId);

    public long countDistinctByServiceNameInAndAndSagaId(List<String> serviceNames, String sagaId);

    public List<SagaResponse> getAllBySagaStatus(String sagaStatus);
}
