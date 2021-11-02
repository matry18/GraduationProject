package com.graduationproject.bosted.testFixtures;

import com.graduationproject.bosted.entity.AccessRight;
import com.graduationproject.bosted.entity.Role;

import java.util.Collections;
import java.util.List;

public class RoleFixture {

    private String id = "1234";

    private String name = "RoleName";

    private List<AccessRight> accessRights = Collections.singletonList(AccessRightFixture.createAccessRight());

    public static Role createRole() {
        return builder().build();
    }

    public static RoleFixture builder() {
        return new RoleFixture();
    }

    public Role build() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setAccessRights(accessRights);
        return role;
    }
}
