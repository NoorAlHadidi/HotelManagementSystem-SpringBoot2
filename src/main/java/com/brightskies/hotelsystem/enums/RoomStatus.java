package com.brightskies.hotelsystem.enums;

public enum RoomStatus {
    available("available"),
    booked("booked");

    private String name;
    RoomStatus(String name) {
        this.name = name;
    }
}
