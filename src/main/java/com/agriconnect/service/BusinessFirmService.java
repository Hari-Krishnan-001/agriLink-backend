package com.agriconnect.service;

import com.agriconnect.dto.BusinessFirmRequest;
import com.agriconnect.dto.BusinessFirmResponse;
import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.User;
import com.agriconnect.repository.BusinessFirmRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BusinessFirmService {

    private final BusinessFirmRepository businessFirmRepository;
    private final UserRepository userRepository;

    public BusinessFirmService(
            BusinessFirmRepository businessFirmRepository,
            UserRepository userRepository) {

        this.businessFirmRepository =
                businessFirmRepository;

        this.userRepository =
                userRepository;
    }


    // ========================================
    // CREATE BUSINESS PROFILE
    // ========================================

    public BusinessFirmResponse createProfile(
            BusinessFirmRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));


        // Check whether profile already exists

        if (businessFirmRepository.existsByUser(user)) {

            throw new RuntimeException(
                    "Business profile already exists");
        }


        // Create BusinessFirm entity

        BusinessFirm businessFirm =
                new BusinessFirm();


        businessFirm.setUser(user);

        businessFirm.setCompanyName(
                request.getCompanyName());

        businessFirm.setIndustry(
                request.getIndustry());

        businessFirm.setWebsite(
                request.getWebsite());

        businessFirm.setPhone(
                request.getPhone());

        businessFirm.setLocation(
                request.getLocation());

        businessFirm.setDescription(
                request.getDescription());


        // Save

        BusinessFirm savedBusinessFirm =
                businessFirmRepository.save(
                        businessFirm);


        return convertToResponse(
                savedBusinessFirm);
    }


    // ========================================
    // GET BUSINESS PROFILE
    // ========================================

    public BusinessFirmResponse getProfile(
            Authentication authentication) {

        String email = authentication.getName();


        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));


        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business profile not found"));


        return convertToResponse(
                businessFirm);
    }


    // ========================================
    // UPDATE BUSINESS PROFILE
    // ========================================

    public BusinessFirmResponse updateProfile(
            BusinessFirmRequest request,
            Authentication authentication) {

        String email = authentication.getName();


        // Find logged-in user

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));


        // Find existing business profile

        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business profile not found"));


        // Update fields

        businessFirm.setCompanyName(
                request.getCompanyName());

        businessFirm.setIndustry(
                request.getIndustry());

        businessFirm.setWebsite(
                request.getWebsite());

        businessFirm.setPhone(
                request.getPhone());

        businessFirm.setLocation(
                request.getLocation());

        businessFirm.setDescription(
                request.getDescription());


        // Save updated profile

        BusinessFirm updatedBusinessFirm =
                businessFirmRepository.save(
                        businessFirm);


        return convertToResponse(
                updatedBusinessFirm);
    }


    // ========================================
    // CONVERT ENTITY → RESPONSE
    // ========================================

    private BusinessFirmResponse convertToResponse(
            BusinessFirm businessFirm) {

        BusinessFirmResponse response =
                new BusinessFirmResponse();


        response.setId(
                businessFirm.getId());


        response.setFullName(
                businessFirm
                        .getUser()
                        .getFullName());


        response.setEmail(
                businessFirm
                        .getUser()
                        .getEmail());


        response.setCompanyName(
                businessFirm.getCompanyName());


        response.setIndustry(
                businessFirm.getIndustry());


        response.setWebsite(
                businessFirm.getWebsite());


        response.setPhone(
                businessFirm.getPhone());


        response.setLocation(
                businessFirm.getLocation());


        response.setDescription(
                businessFirm.getDescription());


        return response;
    }
}