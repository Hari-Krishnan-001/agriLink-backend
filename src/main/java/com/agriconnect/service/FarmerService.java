package com.agriconnect.service;

import com.agriconnect.dto.FarmerRequest;
import com.agriconnect.dto.FarmerResponse;
import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.User;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    public FarmerService(FarmerRepository farmerRepository,
                         UserRepository userRepository) {
        this.farmerRepository = farmerRepository;
        this.userRepository = userRepository;
    }

    public FarmerResponse createFarmerProfile(FarmerRequest request,
                                              Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (farmerRepository.existsByUser(user)) {
            throw new RuntimeException("Farmer profile already exists.");
        }

        Farmer farmer = new Farmer();

        farmer.setUser(user);
        farmer.setState(request.getState());
        farmer.setDistrict(request.getDistrict());
        farmer.setVillage(request.getVillage());
        farmer.setAddress(request.getAddress());
        farmer.setFarmSize(request.getFarmSize());
        farmer.setFarmType(request.getFarmType());
        farmer.setAbout(request.getAbout());

        Farmer savedFarmer = farmerRepository.save(farmer);

        return convertToResponse(savedFarmer);
    }

    public FarmerResponse getFarmerProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Farmer farmer = farmerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Farmer profile not found."));

        return convertToResponse(farmer);
    }
    public FarmerResponse updateFarmerProfile(FarmerRequest request,
                                              Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Farmer farmer = farmerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Farmer profile not found."));

        farmer.setState(request.getState());
        farmer.setDistrict(request.getDistrict());
        farmer.setVillage(request.getVillage());
        farmer.setAddress(request.getAddress());
        farmer.setFarmSize(request.getFarmSize());
        farmer.setFarmType(request.getFarmType());
        farmer.setAbout(request.getAbout());

        Farmer updatedFarmer = farmerRepository.save(farmer);

        return convertToResponse(updatedFarmer);
    }
    public String deleteFarmerProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Farmer farmer = farmerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Farmer profile not found."));

        farmerRepository.delete(farmer);

        return "Farmer profile deleted successfully.";
    }

    private FarmerResponse convertToResponse(Farmer farmer) {

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