package com.graduationproject.bosted.dto;

import com.graduationproject.bosted.entity.AccessRight;

public class AccessRightDto {
    private String id;
    private String name;

    public AccessRightDto(AccessRight accessRight) {
        this.id = accessRight.getId();
        this.name = accessRight.getName();
    }

    public AccessRightDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
