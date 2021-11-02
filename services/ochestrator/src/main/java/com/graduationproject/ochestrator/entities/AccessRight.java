package com.graduationproject.ochestrator.entities;

import com.graduationproject.ochestrator.dto.AccessRightDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccessRight {

    private String sagaId;

    @Id
    private String id;

    private String name;

    public AccessRight() {
        
    }

    public AccessRight(AccessRightDto accessRightDto) {
        this.id = accessRightDto.getId();
        this.name = accessRightDto.getName();
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

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }
}
