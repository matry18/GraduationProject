package com.graduationproject.bosted;

import com.graduationproject.bosted.entity.AccessRight;
import com.graduationproject.bosted.entity.Employee;
import com.graduationproject.bosted.repository.AccessRightRepository;
import com.graduationproject.bosted.repository.DepartmentRepository;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.graduationproject.bosted.testFixtures.EmployeeFixture.createEmployee;
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
    public void save_employee_returns_saved_employee() {
        Employee employee = createEmployee();
        departmentRepository.save(employee.getDepartment());
        employee.getRole().getAccessRights().forEach(accessRight -> {
            accessRightRepository.save(accessRight);
        });
        roleRepository.save(employee.getRole());
        testee.save(employee);
        Employee result = testee.findById(employee.getId()).orElse(null);

        assertThat(result.getId(), is(employee.getId()));
    }
}
