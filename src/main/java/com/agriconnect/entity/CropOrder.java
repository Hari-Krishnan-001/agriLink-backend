package com.agriconnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "crop_orders")
public class CropOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "business_firm_id", nullable = false)
    private BusinessFirm businessFirm;

    private Double quantity;

    private Double totalPrice;

    private String status;

    private LocalDateTime orderDate;

    @PrePersist
    public void prePersist() {

        if (status == null) {
            status = "PENDING";
        }

        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
    }

    public CropOrder() {
    }

    public Long getId() {
        return id;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public BusinessFirm getBusinessFirm() {
        return businessFirm;
    }

    public void setBusinessFirm(BusinessFirm businessFirm) {
        this.businessFirm = businessFirm;
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