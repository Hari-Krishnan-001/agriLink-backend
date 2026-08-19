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

    @PostMapping
    public CropOrderResponse placeOrder(
            @RequestBody CropOrderRequest request,
            Authentication authentication) {

        return cropOrderService.placeOrder(
                request,
                authentication);
    }

    @GetMapping("/my-orders")
    public List<CropOrderResponse> getMyOrders(
            Authentication authentication) {

        return cropOrderService.getMyOrders(
                authentication);
    }

    @GetMapping("/farmer")
    public List<CropOrderResponse> getFarmerOrders(
            Authentication authentication) {

        return cropOrderService.getFarmerOrders(
                authentication);
    }

    @PutMapping("/{id}/approve")
    public CropOrderResponse approveOrder(
            @PathVariable Long id) {

        return cropOrderService.approveOrder(id);
    }

    @PutMapping("/{id}/reject")
    public CropOrderResponse rejectOrder(
            @PathVariable Long id) {

        return cropOrderService.rejectOrder(id);
    }
}