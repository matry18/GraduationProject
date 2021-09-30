package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    void deleteBySagaId(String sagaId);
}
