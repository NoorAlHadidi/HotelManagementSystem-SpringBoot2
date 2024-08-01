package com.brightskies.hotelsystem.Model;

import com.brightskies.hotelsystem.Enum.RoomSection;
import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoomSection section;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    public Room() {

    }

    public Room(RoomSection section, RoomType type, RoomStatus status) {
        this.section = section;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public RoomSection getSection() {
        return section;
    }

    public RoomType getType() {
        return type;
    }

    public RoomStatus getStatus() {
        return status;
    }
}
