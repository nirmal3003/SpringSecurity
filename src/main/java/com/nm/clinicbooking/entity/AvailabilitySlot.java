package com.nm.clinicbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "availability_slots")
@Data
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private boolean booked;
}

