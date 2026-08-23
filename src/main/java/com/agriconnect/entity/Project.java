package com.agriconnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ========================================
    // STUDENT
    // ========================================

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    // ========================================
    // PROJECT INFORMATION
    // ========================================

    private String title;

    @Column(columnDefinition = "TEXT")
    private String problem;

    @Column(columnDefinition = "TEXT")
    private String solution;

    private String technology;

    private Double fundingRequired;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;


    // ========================================
    // PROJECT STATISTICS
    // ========================================

    @Column(nullable = false)
    private Long views = 0L;

    @Column(nullable = false)
    private Long likes = 0L;


    // ========================================
    // CREATED DATE
    // ========================================

    private LocalDateTime createdAt;


    // ========================================
    // PRE PERSIST
    // ========================================

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (status == null) {
            status = "OPEN";
        }

        if (views == null) {
            views = 0L;
        }

        if (likes == null) {
            likes = 0L;
        }
    }


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public Project() {
    }


    // ========================================
    // GETTERS / SETTERS
    // ========================================

    public Long getId() {
        return id;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }


    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }


    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }


    public Double getFundingRequired() {
        return fundingRequired;
    }

    public void setFundingRequired(
            Double fundingRequired) {

        this.fundingRequired = fundingRequired;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }


    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }


    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}