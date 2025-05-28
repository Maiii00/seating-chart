package com.example.seating_chart.repository;
import com.example.seating_chart.model.SeatingChart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatingChartRepository extends JpaRepository<SeatingChart, Integer> {
    
}