package com.agriconnect.dto;

public class ProjectRequest {

    private String title;
    private String problem;
    private String solution;
    private String technology;
    private Double fundingRequired;
    private String description;

    public ProjectRequest() {
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

    public void setFundingRequired(Double fundingRequired) {
        this.fundingRequired = fundingRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
