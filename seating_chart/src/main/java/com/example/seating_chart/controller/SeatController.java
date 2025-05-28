package com.example.seating_chart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.example.seating_chart.model.Employee;
import com.example.seating_chart.model.SeatingChart;
import com.example.seating_chart.service.SeatService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    // 查詢全部座位
    @GetMapping("/seats")
    public List<SeatingChart> getSeats() {
        return seatService.getAllSeats();
    }

    // 查詢全部員工
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return seatService.getAllEmployees();
    }

    // 指派座位
    @PostMapping("/seats/assign")
    public void assignSeat(@RequestParam Integer empId, @RequestParam Integer seatSeq) {
        seatService.assignSeat(empId, seatSeq);
    }

    // 清除座位
    @PostMapping("/seats/clear")
    public void clearSeat(@RequestParam Integer empId) {
        seatService.clearSeat(empId);
    }
    
}
