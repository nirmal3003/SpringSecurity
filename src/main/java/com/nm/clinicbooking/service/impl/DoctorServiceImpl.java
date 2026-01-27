package com.nm.clinicbooking.service.impl;

import com.nm.clinicbooking.entity.AvailabilitySlot;
import com.nm.clinicbooking.entity.Doctor;
import com.nm.clinicbooking.reposiory.DoctorRepository;
import com.nm.clinicbooking.reposiory.SlotRepository;
import com.nm.clinicbooking.service.DoctorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SlotRepository slotRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             SlotRepository slotRepository) {
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    public void createSlot(Long doctorId, AvailabilitySlot slot) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        slot.setDoctor(doctor);
        slot.setBooked(false);

        slotRepository.save(slot);
    }

    @Override
    public List<AvailabilitySlot> getSlots(Long doctorId, LocalDate date) {
        return slotRepository.findByDoctorIdAndDate(doctorId, date);
    }
    @Override
    public Optional<Doctor> findDoctorByEmail(String email){
        return doctorRepository.findByUserEmail(email);
    }
}
