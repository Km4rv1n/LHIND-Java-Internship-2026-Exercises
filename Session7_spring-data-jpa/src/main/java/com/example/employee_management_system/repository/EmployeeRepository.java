package com.example.employee_management_system.repository;

import com.example.employee_management_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);

    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    List<Employee> findByLastNameContaining(String lastName);

    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, BigDecimal salary);

    @Query("SELECT e FROM Employee e WHERE e.hireDate > :hireDate")
    List<Employee> findEmployeesHiredAfter(@Param("hireDate") LocalDate hireDate);

    @Query(nativeQuery = true, value = "SELECT * FROM employee e where e.salary > :salary")
    List<Employee> findBySalaryGreaterThanNativeQuery(@Param("salary") BigDecimal salary);

    List<Employee> findAllByOrderBySalaryDesc();
}
