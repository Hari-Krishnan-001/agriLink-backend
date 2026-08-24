package com.agriconnect.service;

import com.agriconnect.dto.FarmerRequest;
import com.agriconnect.dto.FarmerResponse;
import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.User;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    public FarmerService(
            FarmerRepository farmerRepository,
            UserRepository userRepository) {

        this.farmerRepository = farmerRepository;
        this.userRepository = userRepository;
    }


    // ========================================
    // CREATE FARMER PROFILE
    // ========================================

    public FarmerResponse createFarmerProfile(
            FarmerRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        if (farmerRepository.existsByUser(user)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Farmer profile already exists"
            );
        }

        Farmer farmer = new Farmer();

        farmer.setUser(user);

        farmer.setState(
                request.getState());

        farmer.setDistrict(
                request.getDistrict());

        farmer.setVillage(
                request.getVillage());

        farmer.setAddress(
                request.getAddress());

        farmer.setFarmSize(
                request.getFarmSize());

        farmer.setFarmType(
                request.getFarmType());

        farmer.setAbout(
                request.getAbout());

        Farmer savedFarmer =
                farmerRepository.save(farmer);

        return convertToResponse(savedFarmer);
    }


    // ========================================
    // GET FARMER PROFILE
    // ========================================

    public FarmerResponse getFarmerProfile(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        Farmer farmer =
                farmerRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Farmer profile not found"
                                ));

        return convertToResponse(farmer);
    }


    // ========================================
    // UPDATE FARMER PROFILE
    // ========================================

    public FarmerResponse updateFarmerProfile(
            FarmerRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        Farmer farmer =
                farmerRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Farmer profile not found"
                                ));

        farmer.setState(
                request.getState());

        farmer.setDistrict(
                request.getDistrict());

        farmer.setVillage(
                request.getVillage());

        farmer.setAddress(
                request.getAddress());

        farmer.setFarmSize(
                request.getFarmSize());

        farmer.setFarmType(
                request.getFarmType());

        farmer.setAbout(
                request.getAbout());

        Farmer updatedFarmer =
                farmerRepository.save(farmer);

        return convertToResponse(updatedFarmer);
    }


    // ========================================
    // DELETE FARMER PROFILE
    // ========================================

    public String deleteFarmerProfile(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        Farmer farmer =
                farmerRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Farmer profile not found"
                                ));

        farmerRepository.delete(farmer);

        return "Farmer profile deleted successfully";
    }


    // ========================================
    // CONVERT ENTITY → RESPONSE
    // ========================================

    private FarmerResponse convertToResponse(
            Farmer farmer) {

        User user = farmer.getUser();

        return new FarmerResponse(

                farmer.getId(),

                user.getFullName(),

                user.getEmail(),

                user.getPhone(),

                farmer.getState(),

                farmer.getDistrict(),

                farmer.getVillage(),

                farmer.getAddress(),

                farmer.getFarmSize(),

                farmer.getFarmType(),

                farmer.getAbout()
        );
    }
}