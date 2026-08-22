package com.agriconnect.controller;

import com.agriconnect.dto.BusinessFirmRequest;
import com.agriconnect.dto.BusinessFirmResponse;
import com.agriconnect.service.BusinessFirmService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
public class BusinessFirmController {

    private final BusinessFirmService businessFirmService;

    public BusinessFirmController(
            BusinessFirmService businessFirmService) {

        this.businessFirmService = businessFirmService;
    }

    // ========================================
    // CREATE BUSINESS PROFILE
    // ========================================

    @PostMapping
    public BusinessFirmResponse createProfile(
            @RequestBody BusinessFirmRequest request,
            Authentication authentication) {

        return businessFirmService.createProfile(
                request,
                authentication);
    }


    // ========================================
    // GET BUSINESS PROFILE
    // ========================================

    @GetMapping("/profile")
    public BusinessFirmResponse getProfile(
            Authentication authentication) {

        return businessFirmService.getProfile(
                authentication);
    }


    // ========================================
    // UPDATE BUSINESS PROFILE
    // ========================================

    @PutMapping("/profile")
    public BusinessFirmResponse updateProfile(
            @RequestBody BusinessFirmRequest request,
            Authentication authentication) {

        return businessFirmService.updateProfile(
                request,
                authentication);
    }
}