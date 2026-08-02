package com.agriconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String message;
}