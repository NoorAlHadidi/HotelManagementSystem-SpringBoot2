package com.brightskies.hotelsystem.Model;

import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
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
