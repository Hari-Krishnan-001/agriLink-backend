package com.agriconnect.dto;

import java.time.LocalDateTime;

public class ProjectChatResponse {

    private Long id;

    private Long projectId;

    private String projectTitle;

    private String studentName;

    private String studentEmail;

    private String businessName;

    private LocalDateTime createdAt;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectChatResponse() {
    }


    // ========================================
    // CHAT ID
    // ========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // ========================================
    // PROJECT ID
    // ========================================

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }


    // ========================================
    // PROJECT TITLE
    // ========================================

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }


    // ========================================
    // STUDENT NAME
    // ========================================

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    // ========================================
    // STUDENT EMAIL
    // ========================================

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }


    // ========================================
    // BUSINESS NAME
    // ========================================

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    // ========================================
    // CREATED DATE
    // ========================================

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
