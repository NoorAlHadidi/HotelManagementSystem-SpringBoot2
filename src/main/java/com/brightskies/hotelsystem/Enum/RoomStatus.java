package com.brightskies.hotelsystem.Enum;

public enum RoomStatus {
    available("available"),
    booked("booked");

    private String name;
    RoomStatus(String name) {
        this.name = name;
    }
}
