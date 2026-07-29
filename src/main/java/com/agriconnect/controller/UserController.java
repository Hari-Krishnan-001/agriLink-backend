package com.agriconnect.controller;

import com.agriconnect.dto.UserRequest;
import com.agriconnect.entity.User;
import com.agriconnect.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "AgriConnect Backend is Working!";
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRequest request) {
        return userService.registerUser(request);
    }
}