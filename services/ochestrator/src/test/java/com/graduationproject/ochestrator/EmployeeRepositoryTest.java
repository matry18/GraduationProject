package com.graduationproject.ochestrator;

import com.graduationproject.ochestrator.TestFixtures.EmployeeFixture;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.repository.AccessRightRepository;
import com.graduationproject.ochestrator.repository.DepartmentRepository;
import com.graduationproject.ochestrator.repository.EmployeeRepository;
import com.graduationproject.ochestrator.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository testee;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccessRightRepository accessRightRepository;

    @Test
    public void saveEmployeeAndGetBySagaId_returnsSavedEmployee() {
        Employee employee = EmployeeFixture.builder().setSagaId("4321").build();
        departmentRepository.save(employee.getDepartment());
        employee.getRole().getAccessRights().forEach(accessRight -> {
            accessRightRepository.save(accessRight);
        });
        roleRepository.save(employee.getRole());
        testee.save(employee);
        Employee result = testee.findEmployeeBySagaId(employee.getSagaId());
        assertThat(result.getId(), is(employee.getId()));
    }
}
