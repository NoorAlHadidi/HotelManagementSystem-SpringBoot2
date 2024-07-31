package com.brightskies.hotelsystem.Service;

import com.brightskies.hotelsystem.DTO.RoomDTO;
import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;
import com.brightskies.hotelsystem.Model.Room;
import com.brightskies.hotelsystem.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepo roomRepository;

    @Autowired
    public RoomService(RoomRepo roomRepository) {
        this.roomRepository =  roomRepository;
    }

    public List<RoomDTO> displayRooms() {
        return (roomRepository.findAll()).stream().map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus())).collect(Collectors.toList());
    }

    public boolean addRoom(Room newRoom) {
        if(roomRepository.findBySectionAndType(newRoom.getSection(), newRoom.getType()).isEmpty()) {
            roomRepository.save(newRoom);
            return true;
        }
        return false;
    }

    public List<RoomDTO> filterByStatus(RoomStatus status) {
        return (roomRepository.findByStatus(status)).stream().map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus())).collect(Collectors.toList());
    }

    public List<RoomDTO> filterByType(RoomType type) {
        return (roomRepository.findByType(type)).stream().map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus())).collect(Collectors.toList());
    }
}
