package com.agriconnect.dto;

import lombok.Data;

@Data
public class FarmerRequest {

    private String state;

    private String district;

    private String village;

    private String address;

    private Double farmSize;

    private String farmType;

    private String about;
}