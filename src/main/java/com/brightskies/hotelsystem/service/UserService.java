package com.brightskies.hotelsystem.service;

import com.brightskies.hotelsystem.dto.SignUpDTO;
import com.brightskies.hotelsystem.dto.UserDTO;
import com.brightskies.hotelsystem.model.User;
import com.brightskies.hotelsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserDTO> displayUsers() {
        return (userRepository.findAll())
                .stream()
                .map(user -> new UserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getRole()))
                .collect(Collectors.toList());
    }

    public UserDTO addUser(SignUpDTO signupDTO) throws Exception {
        if (userRepository.findByEmailOrPhone(signupDTO.email(), signupDTO.phone()).isPresent()) {
            throw new Exception("A user with the same email/phone number is present.");
        }
        User user = new User(signupDTO.name(), signupDTO.email(), passwordEncoder.encode(signupDTO.password()), signupDTO.phone(), signupDTO.role());
        userRepository.save(user);
        return new UserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getRole());
    }

//    public User existingUser(String email, String password) throws Exception {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        return userRepository.findByEmail(email).orElseThrow(() -> new Exception("User does not exist"));
//    }

    public String existingUser(String email, String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(email);
        }
        else {
            throw new Exception("User not authenticated");
        }
    }

    public void deleteUser(String email) throws Exception {
        if(userRepository.findByEmail(email).isEmpty()) {
            throw new Exception("User does not exist");
        }
        userRepository.delete(userRepository.findByEmail(email).get());
    }
}
