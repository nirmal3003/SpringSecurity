package com.nm.clinicbooking.service.impl;

import com.nm.clinicbooking.dto.BookingRequest;
import com.nm.clinicbooking.dto.BookingResponse;
import com.nm.clinicbooking.entity.*;
import com.nm.clinicbooking.reposiory.BookingRepository;
import com.nm.clinicbooking.reposiory.DoctorRepository;
import com.nm.clinicbooking.reposiory.SlotRepository;
import com.nm.clinicbooking.reposiory.UserRepository;
import com.nm.clinicbooking.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              SlotRepository slotRepository,
                              DoctorRepository doctorRepository,
                              UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingResponse createBooking(Long patientId, BookingRequest request) {

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        AvailabilitySlot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        slot.setBooked(true);

        Booking booking = new Booking();
        booking.setPatient(patient);
        booking.setDoctor(doctor);
        booking.setSlot(slot);
        booking.setBookingDate(slot.getDate());
        booking.setStatus(BookingStatus.BOOKED);

        bookingRepository.save(booking);
        slotRepository.save(slot);

        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setDoctorName(doctor.getName());
        response.setDate(slot.getDate());
        response.setStartTime(slot.getStartTime());
        response.setStatus(booking.getStatus());

        return response;
    }

    @Override
    public List<BookingResponse> getMyBookings(Long patientId) {
        return bookingRepository.findByPatientId(patientId)
                .stream()
                .map(b -> {
                    BookingResponse r = new BookingResponse();
                    r.setBookingId(b.getId());
                    r.setDoctorName(b.getDoctor().getName());
                    r.setDate(b.getBookingDate());
                    r.setStartTime(b.getSlot().getStartTime());
                    r.setStatus(b.getStatus());
                    return r;
                })
                .toList();
    }

    @Override
    public void cancelBooking(Long bookingId, Long patientId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("Unauthorized");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.getSlot().setBooked(false);

        bookingRepository.save(booking);
        slotRepository.save(booking.getSlot());
    }
}
