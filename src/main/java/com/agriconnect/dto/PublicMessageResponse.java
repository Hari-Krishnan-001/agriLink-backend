package com.agriconnect.dto;

import java.time.LocalDateTime;

public class PublicMessageResponse {

    private Long id;

    private Long senderId;

    private String senderName;

    private String senderEmail;

    private String senderRole;

    private String message;

    private LocalDateTime sentAt;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PublicMessageResponse(
            Long id,
            Long senderId,
            String senderName,
            String senderEmail,
            String senderRole,
            String message,
            LocalDateTime sentAt
    ) {

        this.id = id;

        this.senderId = senderId;

        this.senderName = senderName;

        this.senderEmail = senderEmail;

        this.senderRole = senderRole;

        this.message = message;

        this.sentAt = sentAt;
    }


    // ========================================
    // GETTERS
    // ========================================

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}