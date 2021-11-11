package com.graduationproject.ochestrator.entities;

import com.graduationproject.ochestrator.dto.RoleDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Role {

    @Column(unique=true)
    private String sagaId;

    @Id
    private String id;

    private String name;

    @ManyToMany
    private List<AccessRight> accessRights;

    public Role() {

    }

    public Role(RoleDto roleDto) {
        this.id = roleDto.getId();
        this.name = roleDto.getName();
        this.accessRights = roleDto.getAccessRights().stream()
                .map(AccessRight::new)
                .collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AccessRight> getAccessRights() {
        return accessRights;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessRights(List<AccessRight> accessRights) {
        this.accessRights = accessRights;
    }
}
