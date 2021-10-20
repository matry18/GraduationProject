package com.graduationproject.ochestrator.repository;

import com.graduationproject.ochestrator.entities.Department;
import com.graduationproject.ochestrator.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    void deleteBySagaId(String sagaId);
    Employee findEmployeeBySagaId(String sagaId);
    Long countByDepartment(Department department);

}
