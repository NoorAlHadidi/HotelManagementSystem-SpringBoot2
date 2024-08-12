package com.brightskies.hotelsystem.enums;

public enum UserRole {
    admin("admin"),
    staff("staff"),
    customer("customer");

    private String name;
    UserRole(String name) {
        this.name = name;
    }
}
