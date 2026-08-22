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

    // ==========================================
    // CREATE CROP
    // POST /api/crops
    // ==========================================
    @PostMapping
    public CropResponse createCrop(
            @RequestBody CropRequest request,
            Authentication authentication
    ) {
        return cropService.createCrop(request, authentication);
    }


    // ==========================================
    // GET MY CROPS
    // GET /api/crops/my-crops
    // ==========================================
    @GetMapping("/my-crops")
    public List<CropResponse> getMyCrops(
            Authentication authentication
    ) {
        return cropService.getMyCrops(authentication);
    }


    // ==========================================
    // GET ALL AVAILABLE CROPS
    // GET /api/crops/available
    // ==========================================
    @GetMapping("/available")
    public List<CropResponse> getAvailableCrops() {
        return cropService.getAvailableCrops();
    }


    // ==========================================
    // GET SINGLE CROP
    // GET /api/crops/{id}
    // ==========================================
    @GetMapping("/{id}")
    public CropResponse getCropById(
            @PathVariable Long id
    ) {
        return cropService.getCropById(id);
    }


    // ==========================================
    // UPDATE CROP
    // PUT /api/crops/{id}
    // ==========================================
    @PutMapping("/{id}")
    public CropResponse updateCrop(
            @PathVariable Long id,
            @RequestBody CropRequest request,
            Authentication authentication
    ) {
        return cropService.updateCrop(
                id,
                request,
                authentication
        );
    }


    // ==========================================
    // DELETE CROP
    // DELETE /api/crops/{id}
    // ==========================================
    @DeleteMapping("/{id}")
    public String deleteCrop(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return cropService.deleteCrop(
                id,
                authentication
        );
    }
}