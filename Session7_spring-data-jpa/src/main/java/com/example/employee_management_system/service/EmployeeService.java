package com.example.employee_management_system.service;

import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployeesByDepartment(String department){
        return employeeRepository.findByDepartment(department);
    }

    public List<Employee> getEmployeesWithSalaryGreaterThan(BigDecimal salary){
        return employeeRepository.findBySalaryGreaterThan(salary);
    }

    public List<Employee> getEmployeesWithLastNameContaining(String lastName){
        return employeeRepository.findByLastNameContaining(lastName);
    }

    public List<Employee> getEmployeesByDepartmentWithSalaryGreaterThan(String department, BigDecimal salary){
        return employeeRepository.findByDepartmentAndSalaryGreaterThan(department, salary);
    }

    public List<Employee> getEmployeesHiredAfter(LocalDate hireDate){
        return employeeRepository.findEmployeesHiredAfter(hireDate);
    }

    public List<Employee> getEmployeesWithSalaryGreaterThanNativeQuery(BigDecimal salary){
        return employeeRepository.findBySalaryGreaterThanNativeQuery(salary);
    }

    public Page<Employee> getEmployeesPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> getEmployeesSortedBySalaryDesc(){
        return employeeRepository.findAllByOrderBySalaryDesc();
    }
}
