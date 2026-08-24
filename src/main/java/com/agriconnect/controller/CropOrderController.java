package com.agriconnect.controller;

import com.agriconnect.dto.CropOrderRequest;
import com.agriconnect.dto.CropOrderResponse;
import com.agriconnect.service.CropOrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class CropOrderController {

    private final CropOrderService cropOrderService;

    public CropOrderController(
            CropOrderService cropOrderService) {

        this.cropOrderService = cropOrderService;
    }


    // ==========================================
    // PLACE ORDER
    // POST /api/orders
    // ==========================================

    @PostMapping
    public CropOrderResponse placeOrder(
            @RequestBody CropOrderRequest request,
            Authentication authentication) {

        return cropOrderService.placeOrder(
                request,
                authentication);
    }


    // ==========================================
    // GET MY ORDERS
    // GET /api/orders/my-orders
    // ==========================================

    @GetMapping("/my-orders")
    public List<CropOrderResponse> getMyOrders(
            Authentication authentication) {

        return cropOrderService.getMyOrders(
                authentication);
    }


    // ==========================================
    // GET FARMER ORDERS
    // GET /api/orders/farmer
    // ==========================================

    @GetMapping("/farmer")
    public List<CropOrderResponse> getFarmerOrders(
            Authentication authentication) {

        return cropOrderService.getFarmerOrders(
                authentication);
    }


    // ==========================================
    // APPROVE ORDER
    // PUT /api/orders/{id}/approve
    // ==========================================

    @PutMapping("/{id}/approve")
    public CropOrderResponse approveOrder(
            @PathVariable Long id,
            Authentication authentication) {

        return cropOrderService.approveOrder(
                id,
                authentication);
    }


    // ==========================================
    // REJECT ORDER
    // PUT /api/orders/{id}/reject
    // ==========================================

    @PutMapping("/{id}/reject")
    public CropOrderResponse rejectOrder(
            @PathVariable Long id,
            Authentication authentication) {

        return cropOrderService.rejectOrder(
                id,
                authentication);
    }
}