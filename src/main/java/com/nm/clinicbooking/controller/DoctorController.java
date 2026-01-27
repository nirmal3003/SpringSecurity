package com.nm.clinicbooking.controller;

import com.nm.clinicbooking.entity.AvailabilitySlot;
import com.nm.clinicbooking.entity.Doctor;
import com.nm.clinicbooking.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/slots")
    public ResponseEntity<?> createSlot(@RequestBody AvailabilitySlot slot,
                                        Authentication authentication) {
        String email = authentication.getName(); // from JWT

        Doctor doctor = doctorService.findDoctorByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorService.createSlot(doctor.getId(), slot);
        return ResponseEntity.ok("Slot created");
    }

    @GetMapping("/slots")
    public List<AvailabilitySlot> getSlots(@RequestParam Long doctorId,
                                           @RequestParam LocalDate date) {
        return doctorService.getSlots(doctorId, date);
    }
}
