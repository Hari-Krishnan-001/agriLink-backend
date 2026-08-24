package com.agriconnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_chat_messages")
public class ProjectChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ========================================
    // PROJECT CHAT
    // ========================================

    @ManyToOne
    @JoinColumn(
            name = "project_chat_id",
            nullable = false
    )
    private ProjectChat projectChat;


    // ========================================
    // SENDER
    // ========================================

    @ManyToOne
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
    // PRE PERSIST
    // ========================================

    @PrePersist
    public void prePersist() {

        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectChatMessage() {
    }


    // ========================================
    // GET ID
    // ========================================

    public Long getId() {
        return id;
    }


    // ========================================
    // PROJECT CHAT
    // ========================================

    public ProjectChat getProjectChat() {
        return projectChat;
    }


    public void setProjectChat(
            ProjectChat projectChat) {

        this.projectChat = projectChat;
    }


    // ========================================
    // SENDER
    // ========================================

    public User getSender() {
        return sender;
    }


    public void setSender(User sender) {
        this.sender = sender;
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
    // SENT TIME
    // ========================================

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}