package com.nm.clinicbooking.controller;

import com.nm.clinicbooking.dto.BookingRequest;
import com.nm.clinicbooking.dto.BookingResponse;
import com.nm.clinicbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ✅ CREATE BOOKING
    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingRequest request,
                                         HttpServletRequest httpRequest) {

        Long patientId = (Long) httpRequest.getAttribute("userId");
        return bookingService.createBooking(patientId, request);
    }

    // ✅ GET MY BOOKINGS
    @GetMapping("/my")
    public List<BookingResponse> myBookings(HttpServletRequest httpRequest) {

        Long patientId = (Long) httpRequest.getAttribute("userId");
        return bookingService.getMyBookings(patientId);
    }

    // ✅ CANCEL BOOKING
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id,
                                    HttpServletRequest httpRequest) {

        Long patientId = (Long) httpRequest.getAttribute("userId");
        bookingService.cancelBooking(id, patientId);
        return ResponseEntity.ok("Cancelled");
    }
}
