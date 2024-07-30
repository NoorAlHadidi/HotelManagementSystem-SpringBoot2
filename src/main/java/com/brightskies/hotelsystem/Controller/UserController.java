package com.brightskies.hotelsystem.Controller;

import com.brightskies.hotelsystem.Model.User;
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

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        if(userService.addUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> displayUsers() {
        return ResponseEntity.ok(userService.displayUsers());
    }
}
