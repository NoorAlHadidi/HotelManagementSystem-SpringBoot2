package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.UserDTO;
import com.brightskies.hotelsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
@Tag(name = "User Controller", description = "API for managing users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Adds a new user.", description = "User must have a unique email and phone number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Email/phone number in use"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) throws Exception {
        try {
            userService.addUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @Operation(summary = "Displays all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @GetMapping("/display")
    public ResponseEntity<List<UserDTO>> displayUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.displayUsers());
    }
}
