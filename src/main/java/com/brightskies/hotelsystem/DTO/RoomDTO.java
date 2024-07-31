package com.brightskies.hotelsystem.DTO;

public record RoomDTO(RoomType type, RoomStatus status) {
    public enum RoomType {
        singleroom,
        doubleroom,
        familyroom,
        suite
    }
    public enum RoomStatus {
        available,
        booked
    }
}
