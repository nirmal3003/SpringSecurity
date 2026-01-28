package com.nm.clinicbooking.controller;

import com.nm.clinicbooking.entity.AvailabilitySlot;
import com.nm.clinicbooking.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final DoctorService doctorService;

    public PublicController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ✅ PATIENT CAN VIEW SLOTS
    @GetMapping("/slots")
    public List<AvailabilitySlot> viewSlots(@RequestParam Long doctorId,
                                            @RequestParam LocalDate date) {
        return doctorService.getSlots(doctorId, date);
    }
}
