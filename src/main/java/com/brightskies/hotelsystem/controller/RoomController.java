package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.RoomDTO;
import com.brightskies.hotelsystem.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/room")
@RestController
@Tag(name = "Room Controller", description = "API for managing rooms")
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Adds a new room", description = "Combination of room type and room section must be unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Room type exists in section"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PreAuthorize("hasRole('admin')")
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

    @Operation(summary = "Displays all rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/display")
    public ResponseEntity<List<RoomDTO>> displayRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.displayRooms());
    }

    @Operation(summary = "Filters rooms by status", description = "Room status must either be available or booked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/display/status/{status}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByStatus(@PathVariable String status) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.filterByStatus(status));
    }

    @Operation(summary = "Filters rooms by type", description = "Room type must be singleroom, doubleroom, familyroom or suite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/display/type/{type}")
    public ResponseEntity<List<RoomDTO>> filterRoomsByType(@PathVariable String type) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.filterByType(type));
    }

    @Operation(summary = "Filters rooms by section")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "400", description = "Section does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/display/section/{section}")
    public ResponseEntity<?> filterRoomsBySection(@PathVariable String section) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roomService.filterBySection(section));
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
