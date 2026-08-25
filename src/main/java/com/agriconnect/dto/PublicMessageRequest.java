package com.agriconnect.dto;

public class PublicMessageRequest {

    private String message;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PublicMessageRequest() {
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
}