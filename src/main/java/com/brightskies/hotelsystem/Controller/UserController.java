package com.brightskies.hotelsystem.Controller;

import com.brightskies.hotelsystem.DTO.UserDTO;
import com.brightskies.hotelsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        if(userService.addUser(userDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/display")
    public ResponseEntity<List<UserDTO>> displayUsers() {
        return ResponseEntity.ok(userService.displayUsers());
    }
}
