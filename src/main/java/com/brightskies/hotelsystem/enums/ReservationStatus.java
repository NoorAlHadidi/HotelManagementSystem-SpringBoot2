package com.brightskies.hotelsystem.enums;

public enum ReservationStatus {
    pending("pending"),
    completed("completed"),
    cancelled("cancelled");

    private String name;
    ReservationStatus(String name) {
        this.name = name;
    }
}
