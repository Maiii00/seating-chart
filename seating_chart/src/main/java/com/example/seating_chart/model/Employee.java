package com.example.seating_chart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Employee")
@NoArgsConstructor
@Data
public class Employee {
    
    @Id
    private Integer empId;

    private String name;

    private String email;

    @OneToOne
    @JoinColumn(name = "floor_seat_seq", unique = true)
    private SeatingChart seat;
}
