package com.agriconnect.dto;

public class DashboardResponse {

    private Long totalProjects;

    private Long totalCrops;

    private Long totalOrders;

    private Long approvedOrders;

    private Long rejectedOrders;

    private Double totalRevenue;

    private Double totalSpending;

    public DashboardResponse() {
    }

    public Long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(Long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public Long getTotalCrops() {
        return totalCrops;
    }

    public void setTotalCrops(Long totalCrops) {
        this.totalCrops = totalCrops;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getApprovedOrders() {
        return approvedOrders;
    }

    public void setApprovedOrders(Long approvedOrders) {
        this.approvedOrders = approvedOrders;
    }

    public Long getRejectedOrders() {
        return rejectedOrders;
    }

    public void setRejectedOrders(Long rejectedOrders) {
        this.rejectedOrders = rejectedOrders;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(Double totalSpending) {
        this.totalSpending = totalSpending;
    }
}