package com.agriconnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "project_chats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_project_chat_project_business",
                        columnNames = {
                                "project_id",
                                "business_firm_id"
                        }
                )
        }
)
public class ProjectChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ========================================
    // PROJECT
    // ========================================

    @ManyToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;


    // ========================================
    // BUSINESS FIRM
    // ========================================

    @ManyToOne
    @JoinColumn(
            name = "business_firm_id",
            nullable = false
    )
    private BusinessFirm businessFirm;


    // ========================================
    // CREATED DATE
    // ========================================

    @Column(nullable = false)
    private LocalDateTime createdAt;


    // ========================================
    // PRE PERSIST
    // ========================================

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectChat() {
    }


    // ========================================
    // GET ID
    // ========================================

    public Long getId() {
        return id;
    }


    // ========================================
    // GET PROJECT
    // ========================================

    public Project getProject() {
        return project;
    }


    public void setProject(Project project) {
        this.project = project;
    }


    // ========================================
    // GET BUSINESS FIRM
    // ========================================

    public BusinessFirm getBusinessFirm() {
        return businessFirm;
    }


    public void setBusinessFirm(
            BusinessFirm businessFirm) {

        this.businessFirm = businessFirm;
    }


    // ========================================
    // GET CREATED DATE
    // ========================================

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}