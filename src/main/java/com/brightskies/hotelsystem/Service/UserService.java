package com.brightskies.hotelsystem.Service;

import com.brightskies.hotelsystem.Model.User;
import com.brightskies.hotelsystem.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepo userRepository;

    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> displayUsers() {
        return userRepository.findAll();
    }

    public boolean addUser(User newUser) {
        if(userRepository.findByNameAndPhone(newUser.getName(), newUser.getPhone()).isPresent()) {
            userRepository.save(newUser);
            return true;
        }
        return false;
    }
}
