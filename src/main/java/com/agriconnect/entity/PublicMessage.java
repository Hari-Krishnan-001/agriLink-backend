package com.agriconnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "public_messages")
public class PublicMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ========================================
    // SENDER
    // ========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sender_id",
            nullable = false
    )
    private User sender;


    // ========================================
    // MESSAGE
    // ========================================

    @Column(
            nullable = false,
            length = 2000
    )
    private String message;


    // ========================================
    // SENT TIME
    // ========================================

    @Column(nullable = false)
    private LocalDateTime sentAt;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PublicMessage() {
    }


    // ========================================
    // SET SENT TIME AUTOMATICALLY
    // ========================================

    @PrePersist
    public void prePersist() {

        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }


    // ========================================
    // GET ID
    // ========================================

    public Long getId() {
        return id;
    }


    // ========================================
    // GET SENDER
    // ========================================

    public User getSender() {
        return sender;
    }


    // ========================================
    // SET SENDER
    // ========================================

    public void setSender(User sender) {
        this.sender = sender;
    }


    // ========================================
    // GET MESSAGE
    // ========================================

    public String getMessage() {
        return message;
    }


    // ========================================
    // SET MESSAGE
    // ========================================

    public void setMessage(String message) {
        this.message = message;
    }


    // ========================================
    // GET SENT TIME
    // ========================================

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}