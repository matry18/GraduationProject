package com.graduationproject.bosted.service;

import com.graduationproject.bosted.dto.AccessRightDto;
import com.graduationproject.bosted.entity.AccessRight;
import com.graduationproject.bosted.repository.AccessRightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessRightService {
    private final AccessRightRepository accessRightRepository;

    @Autowired
    public AccessRightService(AccessRightRepository accessRightRepository) {
        this.accessRightRepository = accessRightRepository;
    }

    public AccessRight addAccessRight(AccessRightDto accessRightDto) {
        return accessRightRepository.save(new AccessRight(accessRightDto));
    }

    public List<AccessRight> getAllAccessRights() {
        return accessRightRepository.findAll();
    }
}
