package com.agriconnect.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CropRequest {

    private String cropName;

    private String category;

    private Double quantity;

    private String unit;

    private Double pricePerUnit;

    private String description;

    private LocalDateTime harvestDate;

    private Boolean available;
}
