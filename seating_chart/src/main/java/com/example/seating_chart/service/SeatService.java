package com.example.seating_chart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.seating_chart.model.Employee;
import com.example.seating_chart.model.SeatingChart;
import com.example.seating_chart.repository.EmployeeRepository;
import com.example.seating_chart.repository.SeatingChartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {
    
    private final EmployeeRepository empRepo;

    private final SeatingChartRepository seatRepo;

    // 查詢所有座位
    public List<SeatingChart> getAllSeats() {
        return seatRepo.findAll();
    }

    // 查詢所有員工
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    // 指派座位
    @Transactional
    public void assignSeat(Integer empId, Integer seatSeq) {
        Employee emp = empRepo.findById(empId).orElseThrow();
        SeatingChart seat = seatRepo.findById(seatSeq).orElseThrow();
        emp.setSeat(seat);
        empRepo.save(emp);
    }

    // 清除座位
    @Transactional
    public void clearSeat(Integer empId) {
        Employee emp = empRepo.findById(empId).orElseThrow();
        emp.setSeat(null);
        empRepo.save(emp);
    }
}
