package com.agriconnect.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pesticides")
@Data
public class Pesticide {

    // ========================================
    // ID
    // ========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ========================================
    // NAME
    // ========================================

    @Column(nullable = false, unique = true)
    private String name;


    // ========================================
    // TYPE
    // ========================================

    @Column(nullable = false)
    private String type;


    // ========================================
    // ACTIVE INGREDIENT
    // ========================================

    @Column(name = "active_ingredient")
    private String activeIngredient;


    // ========================================
    // DESCRIPTION
    // ========================================

    @Column(length = 2000)
    private String description;


    // ========================================
    // USES
    // ========================================

    @Column(length = 2000)
    private String uses;


    // ========================================
    // SUGGESTED CROPS
    // ========================================

    /*
     * Keep the Java property name "crops"
     * because existing backend code uses:
     *
     * pesticide.setCrops(...)
     *
     * But store it in the database using
     * the required column name:
     *
     * suggested_crops
     */

    @Column(name = "suggested_crops", length = 1500)
    private String crops;


    // ========================================
    // TARGET PESTS
    // ========================================

    @Column(name = "target_pests", length = 1500)
    private String targetPests;


    // ========================================
    // SAFETY MEASURES
    // ========================================

    @Column(name = "safety_measures", length = 2500)
    private String safetyMeasures;


    // ========================================
    // WARNINGS
    // ========================================

    @Column(length = 2500)
    private String warnings;
}