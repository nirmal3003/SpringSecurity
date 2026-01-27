package com.nm.clinicbooking.controller;

import com.nm.clinicbooking.dto.BookingRequest;
import com.nm.clinicbooking.dto.BookingResponse;
import com.nm.clinicbooking.service.BookingService;
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

    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingRequest request,
                                         @RequestHeader("userId") Long patientId) {
        return bookingService.createBooking(patientId, request);
    }

    @GetMapping("/my")
    public List<BookingResponse> myBookings(@RequestHeader("userId") Long patientId) {
        return bookingService.getMyBookings(patientId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id,
                                    @RequestHeader("userId") Long patientId) {
        bookingService.cancelBooking(id, patientId);
        return ResponseEntity.ok("Cancelled");
    }
}
