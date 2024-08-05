package com.brightskies.hotelsystem.service;

import com.brightskies.hotelsystem.dto.RoomDTO;
import com.brightskies.hotelsystem.enums.RoomStatus;
import com.brightskies.hotelsystem.enums.RoomType;
import com.brightskies.hotelsystem.model.Room;
import com.brightskies.hotelsystem.repository.RoomRepo;
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
        return (roomRepository.findAll())
                .stream()
                .map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus()))
                .collect(Collectors.toList());
    }

    public boolean addRoom(RoomDTO roomDTO) {
        Room room = new Room(roomDTO.section(), roomDTO.type(), roomDTO.status());
        if(roomRepository.findBySectionAndType(room.getSection(), room.getType()).isEmpty()) {
            roomRepository.save(room);
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

    public List<RoomDTO> filterBySection(String section) {
        return (roomRepository.findBySection(section)).stream().map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus())).collect(Collectors.toList());
    }

    public List<String> displaySections() {
        return (roomRepository.findSections());
    }
}
