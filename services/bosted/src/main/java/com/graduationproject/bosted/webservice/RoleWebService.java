package com.graduationproject.bosted.webservice;

import com.graduationproject.bosted.dto.RoleDto;
import com.graduationproject.bosted.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class RoleWebService {
    private final RoleService roleService;

    @Autowired
    public RoleWebService(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("bosted/roles")
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        roleService.addRole(roleDto);
        return roleDto;
    }

    @GetMapping("bosted/roles")
    public List<RoleDto> getRoles() {
        return roleService.getAllRoles().stream()
                .map(RoleDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("bosted/roles/{id}")
    public void deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
    }
}
