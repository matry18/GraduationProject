package com.graduationproject.bosted.testFixtures;

import com.graduationproject.bosted.entity.Department;
import com.graduationproject.bosted.entity.Resident;

import static com.graduationproject.bosted.testFixtures.DepartmentFixture.createDepartment;

public class ResidentFixture {

    private String id = "1234";

    private String firstname ="John";

    private String lastname = "Doe";

    private String email = "JohnDoe@mail.com";

    private String phoneNumber = "12345678";

    private Department department = createDepartment();

    private String username = "JohnDoe";

    private String password = "secretPassword";

    public static Resident createResident() {
        return builder().build();
    }

    public static ResidentFixture builder() {
        return new ResidentFixture();
    }

    public Resident build() {
        Resident resident = new Resident();
        resident.setId(id);
        resident.setFirstname(firstname);
        resident.setLastname(lastname);
        resident.setEmail(email);
        resident.setPhoneNumber(phoneNumber);
        resident.setDepartment(department);
        resident.setUsername(username);
        resident.setPassword(password);
        return resident;
    }

    public ResidentFixture setId(String id) {
        this.id = id;
        return this;
    }

    public ResidentFixture setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ResidentFixture setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ResidentFixture setEmail(String email) {
        this.email = email;
        return this;
    }

    public ResidentFixture setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ResidentFixture setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public ResidentFixture setUsername(String username) {
        this.username = username;
        return this;
    }

    public ResidentFixture setPassword(String password) {
        this.password = password;
        return this;
    }
}
