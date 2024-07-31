package com.brightskies.hotelsystem.Controller;

import com.brightskies.hotelsystem.DTO.RoomDTO;
import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;
import com.brightskies.hotelsystem.Model.Room;
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

    @GetMapping("/display/{status}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByStatus(@PathVariable RoomStatus status) {
        return ResponseEntity.ok(roomService.filterByStatus(status));
    }

    @GetMapping("/display/{type}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByType(@PathVariable RoomType type) {
        return ResponseEntity.ok(roomService.filterByType(type));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addRoom(@RequestBody RoomDTO room) {
        if(roomService.addRoom(new Room(room.type(), room.status()))) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
