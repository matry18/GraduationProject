package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.AccessRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AccessRightRepository extends JpaRepository<AccessRight, String> {
    public void deleteBySagaId(String sagaId);

    public Set<AccessRight> getAccessRightBySagaId(String sagaId);
}
