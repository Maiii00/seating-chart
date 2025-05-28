package com.example.seating_chart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.seating_chart.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
}
