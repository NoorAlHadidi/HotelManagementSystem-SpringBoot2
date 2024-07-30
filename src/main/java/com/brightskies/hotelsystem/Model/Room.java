package com.brightskies.hotelsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Room {
    @Id
    private int id;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    enum RoomType {
        singleroom,
        doubleroom,
        familyroom,
        suite
    }
    enum RoomStatus {
        available,
        booked
    }
}
