package com.brightskies.hotelsystem.dto;

import com.brightskies.hotelsystem.enums.UserRole;

public record SignUpDTO(String name, String email, String password, String phone, UserRole role) {
}
