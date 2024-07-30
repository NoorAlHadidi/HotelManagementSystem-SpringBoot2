package com.brightskies.hotelsystem.Service;

import com.brightskies.hotelsystem.Model.Room;
import com.brightskies.hotelsystem.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private RoomRepo roomRepository;

    @Autowired
    public RoomService(RoomRepo roomRepository) {
        this.roomRepository =  roomRepository;
    }

    public List<Room> displayRooms() {
        return roomRepository.findAll();
    }

    public List<Room> filterByStatus(Room.RoomStatus status) {
        return roomRepository.findByRoomStatus(status);
    }

    public List<Room> filterRoomsByType(Room.RoomType type) {
        return roomRepository.findByRoomType(type);
    }
}
