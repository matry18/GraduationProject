package com.graduationproject.bosted.testFixtures;

import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.entity.Resident;

public class DepartmentFixture {

    private String id = "1234";
    private String departmentName = "TestDepartment";

    public static Department createDepartment() {
        return builder().build();
    }

    public static DepartmentFixture builder() {
        return new DepartmentFixture();
    }

    public Department build() {
        Department department = new Department();
        department.setId(id);
        department.setDepartmentName(departmentName);
        return department;
    }
}
