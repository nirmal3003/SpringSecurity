package com.nm.clinicbooking.controller;

import com.nm.clinicbooking.dto.LoginRequest;
import com.nm.clinicbooking.dto.LoginResponse;
import com.nm.clinicbooking.dto.RegisterRequest;
import com.nm.clinicbooking.entity.User;
import com.nm.clinicbooking.reposiory.UserRepository;
import com.nm.clinicbooking.security.JwtUtil;
import com.nm.clinicbooking.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        String token = authService.login(request);
//        return ResponseEntity.ok(token);
        // 1. Authenticate using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Load FULL user from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 3. Generate token with REAL role & userId
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),   // MUST be DOCTOR / ADMIN / PATIENT
                user.getId()             // MUST NOT be null
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
