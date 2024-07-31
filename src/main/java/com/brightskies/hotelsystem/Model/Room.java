package com.brightskies.hotelsystem.Model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

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

    public Integer getId() {
        return id;
    }

    public RoomType getType() {
        return type;
    }

    public RoomStatus getStatus() {
        return status;
    }
}
