package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, String> {

    void deleteBySagaId(String sagaId);
    Resident findResidentBySagaId(String sagaId);

}