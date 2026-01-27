package com.nm.clinicbooking.reposiory;

import com.nm.clinicbooking.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    List<AvailabilitySlot> findByDoctorIdAndDate(Long doctorId, LocalDate date);
}
