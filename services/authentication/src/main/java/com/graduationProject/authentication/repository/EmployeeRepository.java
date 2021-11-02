package com.graduationProject.authentication.repository;

import com.graduationProject.authentication.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    public boolean existsEmployeeById(String id);
    public boolean existsEmployeeByUsernameAndPassword(String username, String password);
    public Employee getEmployeeByUsername(String username);
}
