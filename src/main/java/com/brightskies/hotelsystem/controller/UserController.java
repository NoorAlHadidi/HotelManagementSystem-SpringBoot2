package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.SignUpDTO;
import com.brightskies.hotelsystem.dto.UserDTO;
import com.brightskies.hotelsystem.model.User;
import com.brightskies.hotelsystem.service.JWTService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
@Tag(name = "User Controller", description = "API for managing users")
public class UserController {
    private UserService userService;
    private JWTService jwtService;

    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Sign up a new user", description = "User must have a unique email and phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = SignUpDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Email/phone number in use"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json")})
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signupDTO) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(signupDTO));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @Operation(summary = "Log in as an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "User not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestParam String email, @RequestParam String password) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.existingUser(email, password));
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @Operation(summary = "Deletes a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "User does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @Operation(summary = "Displays all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @GetMapping("/display")
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    public ResponseEntity<List<UserDTO>> displayUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.displayUsers());
    }
}
