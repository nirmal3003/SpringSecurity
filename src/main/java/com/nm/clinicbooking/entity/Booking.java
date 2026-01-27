package com.nm.clinicbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User patient;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private AvailabilitySlot slot;

    private LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}

