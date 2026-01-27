package com.nm.clinicbooking.service.impl;

import com.nm.clinicbooking.dto.LoginRequest;
import com.nm.clinicbooking.dto.RegisterRequest;
import com.nm.clinicbooking.entity.Doctor;
import com.nm.clinicbooking.entity.Role;
import com.nm.clinicbooking.entity.User;
import com.nm.clinicbooking.reposiory.DoctorRepository;
import com.nm.clinicbooking.reposiory.UserRepository;
import com.nm.clinicbooking.security.JwtUtil;
import com.nm.clinicbooking.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, DoctorRepository doctorRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
        // 2. If role is DOCTOR, also create Doctor profile
        if (request.getRole() == Role.DOCTOR) {

            Doctor doctor = new Doctor();
            doctor.setUser(user);   // link doctor to login account
            doctor.setName(request.getName()); // or separate field from request
            doctor.setEmail(request.getEmail());
            doctor.setSpecialization(request.getSpecialization()); // from request
            doctor.setExperience(request.getExperience()); // from request

            doctorRepository.save(doctor);
        }
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
    }
}
