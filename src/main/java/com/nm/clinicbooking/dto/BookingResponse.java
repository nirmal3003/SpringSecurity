package com.nm.clinicbooking.dto;

import com.nm.clinicbooking.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class BookingResponse {
    private Long bookingId;
    private String doctorName;
    private LocalDate date;
    private LocalTime startTime;
    private BookingStatus status;
}

