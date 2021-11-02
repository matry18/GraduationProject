package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.AccessRight;
import com.graduationproject.ochestrator.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {
    public void deleteBySagaId(String sagaId);

    public Role findBySagaId(String sagaId);

    public boolean existsRoleByAccessRightsIn(Set<AccessRight> accessRights);
}
