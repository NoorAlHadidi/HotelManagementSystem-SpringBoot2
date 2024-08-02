package com.brightskies.hotelsystem.Controller;

import com.brightskies.hotelsystem.DTO.RoomDTO;
import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;
import com.brightskies.hotelsystem.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/room")
@RestController
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/display")
    public ResponseEntity<List<RoomDTO>> displayRooms() {
        return ResponseEntity.ok(roomService.displayRooms());
    }

    @GetMapping("/display/status/{status}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByStatus(@PathVariable String status) {
        RoomStatus roomStatus;
        try {
            roomStatus = RoomStatus.valueOf(status.toLowerCase());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(roomService.filterByStatus(roomStatus));
    }

    @GetMapping("/display/type/{type}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByType(@PathVariable String type) {
        RoomType roomType;
        try {
            roomType = RoomType.valueOf(type.toLowerCase());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(roomService.filterByType(roomType));
    }

    @GetMapping("/display/section/{section}")
    public ResponseEntity<List<RoomDTO>> filterRoomsBySection(@PathVariable String section) {
        if(roomService.displaySections().contains(section)) {
            return ResponseEntity.ok(roomService.filterBySection(section));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/add")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO) {
        if(roomService.addRoom(roomDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomDTO);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
