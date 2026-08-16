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

    @PostMapping
    public BusinessFirmResponse createProfile(
            @RequestBody BusinessFirmRequest request,
            Authentication authentication) {

        return businessFirmService.createProfile(
                request,
                authentication);
    }

    @GetMapping("/profile")
    public BusinessFirmResponse getProfile(
            Authentication authentication) {

        return businessFirmService.getProfile(
                authentication);
    }
}