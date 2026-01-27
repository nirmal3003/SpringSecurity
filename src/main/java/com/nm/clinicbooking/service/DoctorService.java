package com.nm.clinicbooking.service;

import com.nm.clinicbooking.entity.AvailabilitySlot;
import com.nm.clinicbooking.entity.Doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    void createSlot(Long doctorId, AvailabilitySlot slot);
    List<AvailabilitySlot> getSlots(Long doctorId, LocalDate date);

    Optional<Doctor> findDoctorByEmail(String email);
}
