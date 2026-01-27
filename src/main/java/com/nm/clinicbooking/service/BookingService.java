package com.nm.clinicbooking.service;

import com.nm.clinicbooking.dto.BookingRequest;
import com.nm.clinicbooking.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(Long patientId, BookingRequest request);
    List<BookingResponse> getMyBookings(Long patientId);
    void cancelBooking(Long bookingId, Long patientId);
}
