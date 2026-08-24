package com.agriconnect.dto;

import java.time.LocalDateTime;

public class ProjectResponse {

    private Long id;

    private String studentName;

    private String studentEmail;

    // ========================================
    // OWNER
    // ========================================

    private boolean owner;

    private String title;

    private String problem;

    private String solution;

    private String technology;

    private Double fundingRequired;

    private String status;

    private String description;

    private Long views;

    private Long likes;

    private LocalDateTime createdAt;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectResponse() {
    }


    // ========================================
    // GETTERS / SETTERS
    // ========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(
            String studentName) {

        this.studentName = studentName;
    }


    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(
            String studentEmail) {

        this.studentEmail = studentEmail;
    }


    // ========================================
    // OWNER GETTER / SETTER
    // ========================================

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
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

    public void setProblem(
            String problem) {

        this.problem = problem;
    }


    public String getSolution() {
        return solution;
    }

    public void setSolution(
            String solution) {

        this.solution = solution;
    }


    public String getTechnology() {
        return technology;
    }

    public void setTechnology(
            String technology) {

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

    public void setStatus(
            String status) {

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

    public void setViews(
            Long views) {

        this.views = views;
    }


    public Long getLikes() {
        return likes;
    }

    public void setLikes(
            Long likes) {

        this.likes = likes;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }
}