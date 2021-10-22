package com.graduationproject.bosted.repository;

import com.graduationproject.bosted.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, String> {
    public boolean existsResidentById(String id);
}
