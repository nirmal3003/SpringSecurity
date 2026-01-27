package com.nm.clinicbooking.service;

import com.nm.clinicbooking.dto.LoginRequest;
import com.nm.clinicbooking.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    String login(LoginRequest request);
}
