package com.nm.clinicbooking.reposiory;

import com.nm.clinicbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByPatientId(Long patientId);
    List<Booking> findByDoctorId(Long doctorId);
}
