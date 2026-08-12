package com.agriconnect.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "crops")
@Data
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @Column(nullable = false)
    private String cropName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Double pricePerUnit;

    @Column(length = 1000)
    private String description;

    private LocalDateTime harvestDate;

    @Column(nullable = false)
    private Boolean available = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}