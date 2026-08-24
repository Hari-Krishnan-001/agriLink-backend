package com.agriconnect.service;

import com.agriconnect.dto.CropRequest;
import com.agriconnect.dto.CropResponse;
import com.agriconnect.entity.Crop;
import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.User;
import com.agriconnect.repository.CropRepository;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CropService {

    private final CropRepository cropRepository;
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;

    public CropService(
            CropRepository cropRepository,
            UserRepository userRepository,
            FarmerRepository farmerRepository) {

        this.cropRepository = cropRepository;
        this.userRepository = userRepository;
        this.farmerRepository = farmerRepository;
    }


    // ========================================
    // CREATE CROP
    // ========================================

    public CropResponse createCrop(
            CropRequest request,
            Authentication authentication) {

        Farmer farmer =
                getAuthenticatedFarmer(authentication);

        Crop crop = new Crop();

        crop.setFarmer(farmer);

        crop.setCropName(
                request.getCropName());

        crop.setCategory(
                request.getCategory());

        crop.setQuantity(
                request.getQuantity());

        crop.setUnit(
                request.getUnit());

        crop.setPricePerUnit(
                request.getPricePerUnit());

        crop.setDescription(
                request.getDescription());

        crop.setHarvestDate(
                request.getHarvestDate());

        if (request.getAvailable() != null) {

            crop.setAvailable(
                    request.getAvailable());

        } else {

            crop.setAvailable(true);
        }

        Crop savedCrop =
                cropRepository.save(crop);

        return convertToResponse(savedCrop);
    }


    // ========================================
    // GET MY CROPS
    // ========================================

    public List<CropResponse> getMyCrops(
            Authentication authentication) {

        Farmer farmer =
                getAuthenticatedFarmer(authentication);

        return cropRepository
                .findByFarmer(farmer)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ========================================
    // GET ALL AVAILABLE CROPS
    // ========================================

    public List<CropResponse> getAvailableCrops() {

        return cropRepository
                .findByAvailableTrue()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ========================================
    // GET SINGLE CROP
    // ========================================

    public CropResponse getCropById(Long id) {

        Crop crop =
                cropRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Crop not found"
                                ));

        return convertToResponse(crop);
    }


    // ========================================
    // UPDATE CROP
    // ========================================

    public CropResponse updateCrop(
            Long id,
            CropRequest request,
            Authentication authentication) {

        Farmer farmer =
                getAuthenticatedFarmer(authentication);

        Crop crop =
                cropRepository
                        .findByIdAndFarmer(id, farmer)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Crop not found or you are not authorized to modify it"
                                ));

        crop.setCropName(
                request.getCropName());

        crop.setCategory(
                request.getCategory());

        crop.setQuantity(
                request.getQuantity());

        crop.setUnit(
                request.getUnit());

        crop.setPricePerUnit(
                request.getPricePerUnit());

        crop.setDescription(
                request.getDescription());

        crop.setHarvestDate(
                request.getHarvestDate());

        if (request.getAvailable() != null) {

            crop.setAvailable(
                    request.getAvailable());
        }

        Crop updatedCrop =
                cropRepository.save(crop);

        return convertToResponse(updatedCrop);
    }


    // ========================================
    // DELETE CROP
    // ========================================
    // SOFT DELETE
    // ========================================

    public String deleteCrop(
            Long id,
            Authentication authentication) {

        Farmer farmer =
                getAuthenticatedFarmer(authentication);

        Crop crop =
                cropRepository
                        .findByIdAndFarmer(id, farmer)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Crop not found or you are not authorized to remove it"
                                ));

        /*
         * We do not physically delete the crop.
         *
         * A crop may already be referenced by
         * an order.
         *
         * Therefore, mark it unavailable instead.
         */

        crop.setAvailable(false);

        cropRepository.save(crop);

        return "Crop removed from marketplace successfully";
    }


    // ========================================
    // GET AUTHENTICATED FARMER
    // ========================================

    private Farmer getAuthenticatedFarmer(
            Authentication authentication) {

        if (authentication == null ||
                authentication.getName() == null) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }

        String email =
                authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User not found"
                                ));

        return farmerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Farmer profile not found. Please create a farmer profile first."
                        ));
    }


    // ========================================
    // CONVERT ENTITY → RESPONSE
    // ========================================

    private CropResponse convertToResponse(
            Crop crop) {

        Farmer farmer =
                crop.getFarmer();

        User user =
                farmer.getUser();

        return new CropResponse(

                crop.getId(),

                user.getFullName(),

                user.getEmail(),

                crop.getCropName(),

                crop.getCategory(),

                crop.getQuantity(),

                crop.getUnit(),

                crop.getPricePerUnit(),

                crop.getDescription(),

                crop.getHarvestDate(),

                crop.getAvailable(),

                crop.getCreatedAt()
        );
    }
}