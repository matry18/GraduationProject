package com.graduationproject.bosted.service;

import com.graduationproject.bosted.dto.RoleDto;
import com.graduationproject.bosted.entity.Role;
import com.graduationproject.bosted.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addRole(RoleDto roleDto) {
        roleDto.setId(UUID.randomUUID().toString());
        return roleRepository.save(new Role(roleDto));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role deleteRole(String id) {
        Role role = roleRepository.getById(id);
        roleRepository.deleteById(id);
        return role;
    }
}
