package com.agriconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CropResponse {

    private Long id;

    private String farmerName;

    private String farmerEmail;

    private String cropName;

    private String category;

    private Double quantity;

    private String unit;

    private Double pricePerUnit;

    private String description;

    private LocalDateTime harvestDate;

    private Boolean available;

    private LocalDateTime createdAt;
}
