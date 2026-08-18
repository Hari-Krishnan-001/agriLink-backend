package com.agriconnect.dto;

import java.time.LocalDateTime;

public class CropOrderResponse {

    private Long id;

    private String cropName;

    private String businessFirmName;

    private Double quantity;

    private Double totalPrice;

    private String status;

    private LocalDateTime orderDate;

    public CropOrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getBusinessFirmName() {
        return businessFirmName;
    }

    public void setBusinessFirmName(String businessFirmName) {
        this.businessFirmName = businessFirmName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}