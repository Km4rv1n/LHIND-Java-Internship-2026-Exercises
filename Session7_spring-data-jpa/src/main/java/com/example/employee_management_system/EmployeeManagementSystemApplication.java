package com.example.employee_management_system;

import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import com.example.employee_management_system.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EmployeeService employeeService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n===== Employee Management =====");
                System.out.println("1 - Find employees by department");
                System.out.println("2 - Find employees with salary greater than");
                System.out.println("3 - Find employees whose last name contains");
                System.out.println("4 - Find employees hired after a date");
                System.out.println("5 - Execute native salary query");
                System.out.println("6 - Show first page (5 employees)");
                System.out.println("7 - Show employees sorted by salary (descending)");
                System.out.println("0 - Exit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> {
                        System.out.print("Department: ");
                        String department = scanner.nextLine();
                        // TODO:
                        // Execute the repository method that finds
                        // employees by department and print the results.
                        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
                        employees.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Minimum salary: ");
                        BigDecimal salary = new BigDecimal(scanner.nextLine());
                        // TODO:
                        // Execute the repository method that finds
                        // employees with salary greater than the given value
                        // and print the results.
                        List<Employee> employees = employeeService.getEmployeesWithSalaryGreaterThan(salary);
                        employees.forEach(System.out::println);
                    }
                    case 3 -> {
                        System.out.print("Last name contains: ");
                        String text = scanner.nextLine();
                        // TODO:
                        // Execute the repository method that finds
                        // employees whose last name contains the given text
                        // and print the results.
                        List<Employee> employees = employeeService.getEmployeesWithLastNameContaining(text);
                        employees.forEach(System.out::println);
                    }
                    case 4 -> {
                        System.out.print("Hire date (yyyy-MM-dd): ");
                        LocalDate hireDate = LocalDate.parse(scanner.nextLine());
                        // TODO:
                        // Execute the JPQL query method that returns
                        // employees hired after the given date.
                        List<Employee> employees = employeeService.getEmployeesHiredAfter(hireDate);
                        employees.forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.print("Minimum salary: ");
                        BigDecimal salary = new BigDecimal(scanner.nextLine());
                        // TODO:
                        // Execute the native query that returns employees
                        // whose salary is greater than the given value.
                        List<Employee> employees = employeeService.getEmployeesWithSalaryGreaterThanNativeQuery(salary);
                        employees.forEach(System.out::println);
                    }
                    case 6 -> {
                        // TODO:
                        // Retrieve the first page containing
                        // 5 employees and print the results.
                        Page<Employee> employeePage = employeeService.getEmployeesPagination(0,5);
                        employeePage.getContent().forEach(System.out::println);
                    }
                    case 7 -> {
                        // TODO:
                        // Retrieve all employees sorted
                        // by salary in descending order
                        // and print the results.
                        List<Employee> employees = employeeService.getEmployeesSortedBySalaryDesc();
                        employees.forEach(System.out::println);
                    }
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        };
    }
}



