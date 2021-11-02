package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Employee;
import com.graduationproject.ochestrator.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    void deleteBySagaId(String sagaId);

    Employee findEmployeeBySagaId(String sagaId);

    Long countByDepartment(Department department);

    public boolean existsEmployeeByRole(Role role);

}
