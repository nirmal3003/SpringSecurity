package com.nm.clinicbooking.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long doctorId;
    private Long slotId;
}

