package com.graduationproject.bosted.testFixtures;

import com.graduationproject.bosted.entity.AccessRight;

public class AccessRightFixture {

    private String id = "1234";

    private String name = "Access Right";

    public static AccessRight createAccessRight() {
        return builder().build();
    }

    public static AccessRightFixture builder() {
        return new AccessRightFixture();
    }

    public AccessRight build() {
        AccessRight accessRight = new AccessRight();
        accessRight.setId(id);
        accessRight.setName(name);
        return accessRight;
    }
}
