package com.brightskies.hotelsystem.service;

import com.brightskies.hotelsystem.dto.UserDTO;
import com.brightskies.hotelsystem.model.User;
import com.brightskies.hotelsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepo userRepository;

    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> displayUsers() {
        return (userRepository.findAll())
                .stream()
                .map(user -> new UserDTO(user.getName(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public void addUser(UserDTO userDTO) throws Exception {
        User user = new User(userDTO.name(), userDTO.email(), userDTO.phone());
        if(userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone()).isPresent()) {
            throw new Exception("A user with the same email/phone number is present.");
        }
        userRepository.save(user);
    }
}
