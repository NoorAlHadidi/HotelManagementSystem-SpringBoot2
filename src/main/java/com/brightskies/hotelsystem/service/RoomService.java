package com.brightskies.hotelsystem.service;

import com.brightskies.hotelsystem.dto.RoomDTO;
import com.brightskies.hotelsystem.dto.UserDTO;
import com.brightskies.hotelsystem.enums.RoomStatus;
import com.brightskies.hotelsystem.enums.RoomType;
import com.brightskies.hotelsystem.model.Room;
import com.brightskies.hotelsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository =  roomRepository;
    }

    public List<RoomDTO> displayRooms() {
        return (roomRepository.findAll())
                .stream()
                .map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus()))
                .collect(Collectors.toList());
    }

    public RoomDTO addRoom(RoomDTO roomDTO) throws Exception {
        if(roomRepository.findBySectionAndType(roomDTO.section(), roomDTO.type()).isPresent()) {
            throw new Exception("A room of that type exists in that section.");
        }
        Room room = new Room(roomDTO.section(), roomDTO.type(), roomDTO.status());
        roomRepository.save(room);
        return new RoomDTO(room.getSection(), room.getType(), room.getStatus());
    }

    public List<RoomDTO> filterByStatus(String status) {
        return (roomRepository.findByStatus(RoomStatus.valueOf(status)))
                .stream()
                .map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus()))
                .collect(Collectors.toList());
    }

    public List<RoomDTO> filterByType(String type) {
        return (roomRepository.findByType(RoomType.valueOf(type)))
                .stream()
                .map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus()))
                .collect(Collectors.toList());
    }

    public List<RoomDTO> filterBySection(String section) throws Exception {
        if(roomRepository.findSections().contains(section)) {
            return (roomRepository.findBySection(section))
                    .stream()
                    .map(room -> new RoomDTO(room.getSection(), room.getType(), room.getStatus()))
                    .collect(Collectors.toList());
        }
        throw new Exception("No such section in the hotel.");
    }

}
