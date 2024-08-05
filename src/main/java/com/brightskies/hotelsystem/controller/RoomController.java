package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.RoomDTO;
import com.brightskies.hotelsystem.enums.RoomStatus;
import com.brightskies.hotelsystem.enums.RoomType;
import com.brightskies.hotelsystem.service.RoomService;
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
        return ResponseEntity.ok(roomService.filterByStatus(status));
    }

    @GetMapping("/display/type/{type}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByType(@PathVariable String type) {
        return ResponseEntity.ok(roomService.filterByType(type));
    }

    @GetMapping("/display/section/{section}")
    public ResponseEntity<?> filterRoomsBySection(@PathVariable String section) {
        try {
            return ResponseEntity.ok(roomService.filterBySection(section));
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoom(@RequestBody RoomDTO roomDTO) {
        try {
            roomService.addRoom(roomDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(roomDTO);
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }
}
