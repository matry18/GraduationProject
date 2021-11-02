package com.graduationproject.bosted.dto;

import com.graduationproject.bosted.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDto {
    private String id;
    private String name;
    private List<AccessRightDto> accessRights;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.accessRights = role.getAccessRights().stream()
                .map(AccessRightDto::new)
                .collect(Collectors.toList());
    }

    public RoleDto() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AccessRightDto> getAccessRights() {
        return accessRights;
    }
}
