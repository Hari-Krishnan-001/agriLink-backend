package com.agriconnect.controller;

import com.agriconnect.dto.CropRequest;
import com.agriconnect.dto.CropResponse;
import com.agriconnect.service.CropService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    // Create Crop
    @PostMapping
    public CropResponse createCrop(
            @RequestBody CropRequest request,
            Authentication authentication
    ) {
        return cropService.createCrop(request, authentication);
    }

    // Get My Crops
    @GetMapping("/my-crops")
    public List<CropResponse> getMyCrops(
            Authentication authentication
    ) {
        return cropService.getMyCrops(authentication);
    }

    // Get All Available Crops
    @GetMapping
    public List<CropResponse> getAvailableCrops() {
        return cropService.getAvailableCrops();
    }

    // Get Single Crop
    @GetMapping("/{id}")
    public CropResponse getCropById(
            @PathVariable Long id
    ) {
        return cropService.getCropById(id);
    }

    // Update Crop
    @PutMapping("/{id}")
    public CropResponse updateCrop(
            @PathVariable Long id,
            @RequestBody CropRequest request,
            Authentication authentication
    ) {
        return cropService.updateCrop(id, request, authentication);
    }

    // Delete Crop
    @DeleteMapping("/{id}")
    public String deleteCrop(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return cropService.deleteCrop(id, authentication);
    }
}
