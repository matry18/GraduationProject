package com.graduationproject.bosted.webservice;

import com.graduationproject.bosted.dto.AccessRightDto;
import com.graduationproject.bosted.entity.AccessRight;
import com.graduationproject.bosted.service.AccessRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class AccessRightWebservice {
    private final AccessRightService accessRightService;

    @Autowired
    public AccessRightWebservice(AccessRightService accessRightService) {
        this.accessRightService = accessRightService;
    }

    @PostMapping("bosted/access-rights")
    public AccessRightDto createAccessRight(@RequestBody AccessRightDto accessRightDto) {
        return new AccessRightDto(accessRightService.addAccessRight(accessRightDto));
    }

    @GetMapping("bosted/access-rights")
    public List<AccessRightDto> getAllAccessRights() {
        return accessRightService.getAllAccessRights().stream()
                .map(AccessRightDto::new)
                .collect(Collectors.toList());
    }
}
