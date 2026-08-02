package com.agriconnect.controller;

import com.agriconnect.dto.FarmerRequest;
import com.agriconnect.dto.FarmerResponse;
import com.agriconnect.service.FarmerService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    // Create Farmer Profile
    @PostMapping
    public FarmerResponse createFarmerProfile(
            @RequestBody FarmerRequest request,
            Authentication authentication
    ) {
        return farmerService.createFarmerProfile(request, authentication);
    }

    // Get Logged-in Farmer Profile
    @GetMapping("/profile")
    public FarmerResponse getFarmerProfile(Authentication authentication) {
        return farmerService.getFarmerProfile(authentication);
    }

    // Update Farmer Profile
    @PutMapping("/profile")
    public FarmerResponse updateFarmerProfile(
            @RequestBody FarmerRequest request,
            Authentication authentication
    ) {
        return farmerService.updateFarmerProfile(request, authentication);
    }

    // Delete Farmer Profile
    @DeleteMapping("/profile")
    public String deleteFarmerProfile(Authentication authentication) {
        return farmerService.deleteFarmerProfile(authentication);
    }
}