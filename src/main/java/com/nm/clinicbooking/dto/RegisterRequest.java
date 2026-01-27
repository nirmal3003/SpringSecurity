package com.nm.clinicbooking.dto;

import com.nm.clinicbooking.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

    private String specialization;
    private int experience;
}

