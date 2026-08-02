package com.agriconnect.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "farmers")
@Data
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double farmSize;

    @Column(nullable = false)
    private String farmType;

    @Column(length = 500)
    private String about;
}