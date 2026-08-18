package com.agriconnect.dto;

public class CropOrderRequest {

    private Long cropId;

    private Double quantity;

    public CropOrderRequest() {
    }

    public Long getCropId() {
        return cropId;
    }

    public void setCropId(Long cropId) {
        this.cropId = cropId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}