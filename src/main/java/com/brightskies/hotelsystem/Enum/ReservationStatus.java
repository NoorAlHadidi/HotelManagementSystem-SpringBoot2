package com.brightskies.hotelsystem.Enum;

public enum ReservationStatus {
    pending("pending"),
    booked("booked"),
    cancelled("cancelled");

    private String name;
    ReservationStatus(String name) {
        this.name = name;
    }
}
