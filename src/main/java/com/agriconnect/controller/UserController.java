package com.agriconnect.controller;

import com.agriconnect.dto.LoginRequest;
import com.agriconnect.dto.LoginResponse;
import com.agriconnect.dto.UserRequest;
import com.agriconnect.entity.User;
import com.agriconnect.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // ========================================
    // TEST
    // ========================================

    @GetMapping("/test")
    public String test() {

        return "AgriConnect Backend is Working!";
    }


    // ========================================
    // REGISTER
    // ========================================

    @PostMapping("/register")
    public User registerUser(
            @RequestBody UserRequest request
    ) {

        return userService.registerUser(request);
    }


    // ========================================
    // LOGIN
    // ========================================

    @PostMapping("/login")
    public LoginResponse loginUser(
            @RequestBody LoginRequest request
    ) {

        return userService.loginUser(request);
    }


    // ========================================
    // GET CURRENT USER PROFILE
    // ========================================

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(
            Authentication authentication
    ) {

        User user =
                userService.getUserByEmail(
                        authentication.getName()
                );


        Map<String, Object> profile =
                new HashMap<>();


        profile.put(
                "id",
                user.getId()
        );

        profile.put(
                "fullName",
                user.getFullName()
        );

        profile.put(
                "email",
                user.getEmail()
        );

        profile.put(
                "phone",
                user.getPhone()
        );

        profile.put(
                "role",
                user.getRole()
        );

        profile.put(
                "profilePicture",
                user.getProfilePicture()
        );


        return ResponseEntity.ok(profile);
    }
}