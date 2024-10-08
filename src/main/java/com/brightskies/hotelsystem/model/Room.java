package com.brightskies.hotelsystem.model;

import com.brightskies.hotelsystem.enums.RoomStatus;
import com.brightskies.hotelsystem.enums.RoomType;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String section;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    public Room() {

    }

    public Room(String section, RoomType type, RoomStatus status) {
        this.section = section;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getSection() {
        return section;
    }

    public RoomType getType() {
        return type;
    }

    public RoomStatus getStatus() {
        return status;
    }
}
