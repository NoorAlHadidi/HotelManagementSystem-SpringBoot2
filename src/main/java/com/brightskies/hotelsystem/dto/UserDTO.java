package com.brightskies.hotelsystem.dto;

import com.brightskies.hotelsystem.enums.UserRole;

public record UserDTO(String name, String email, String phone, UserRole role) {
}
