package com.agriconnect.service;

import com.agriconnect.dto.UserRequest;
import com.agriconnect.entity.User;
import com.agriconnect.exception.UserAlreadyExistsException;
import com.agriconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new UserAlreadyExistsException("Phone number already exists.");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return userRepository.save(user);
    }
}