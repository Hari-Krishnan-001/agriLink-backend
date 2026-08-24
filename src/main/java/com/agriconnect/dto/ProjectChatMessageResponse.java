package com.agriconnect.dto;

import java.time.LocalDateTime;

public class ProjectChatMessageResponse {

    private Long id;

    private String senderName;

    private String senderEmail;

    private String message;

    private LocalDateTime sentAt;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectChatMessageResponse() {
    }


    // ========================================
    // ID
    // ========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // ========================================
    // SENDER NAME
    // ========================================

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    // ========================================
    // SENDER EMAIL
    // ========================================

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }


    // ========================================
    // MESSAGE
    // ========================================

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    // ========================================
    // SENT AT
    // ========================================

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}