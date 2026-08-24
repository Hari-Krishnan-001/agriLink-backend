package com.agriconnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "chats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chat_order",
                        columnNames = "order_id"
                )
        }
)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One chat belongs to one order
    @OneToOne
    @JoinColumn(
            name = "order_id",
            nullable = false,
            unique = true
    )
    private CropOrder order;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public CropOrder getOrder() {
        return order;
    }

    public void setOrder(CropOrder order) {
        this.order = order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}