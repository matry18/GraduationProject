package com.graduationproject.ochestrator.dto;

import java.util.List;

public class RoleDto {
    private String id;
    private String name;
    private List<AccessRightDto> accessRights;

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
