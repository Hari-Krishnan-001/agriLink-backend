package com.agriconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmerResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String state;

    private String district;

    private String village;

    private String address;

    private Double farmSize;

    private String farmType;

    private String about;
}
