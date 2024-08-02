package com.brightskies.hotelsystem.Service;

import com.brightskies.hotelsystem.DTO.UserDTO;
import com.brightskies.hotelsystem.Model.User;
import com.brightskies.hotelsystem.Repository.UserRepo;
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
        return (userRepository.findAll()).stream().map(user -> new UserDTO(user.getName(), user.getEmail(), user.getPhone())).collect(Collectors.toList());
    }

    public boolean addUser(UserDTO userDTO) {
        User user = new User(userDTO.name(), userDTO.email(), userDTO.phone());
        if(userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone()).isEmpty()) {
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
