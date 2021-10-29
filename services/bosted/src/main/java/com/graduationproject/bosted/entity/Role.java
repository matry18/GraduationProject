package com.graduationproject.bosted.entity;

import com.graduationproject.bosted.dto.RoleDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Role {
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
}
