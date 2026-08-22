package com.agriconnect.service;

import com.agriconnect.dto.CropOrderRequest;
import com.agriconnect.dto.CropOrderResponse;
import com.agriconnect.entity.*;
import com.agriconnect.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropOrderService {

    private final CropOrderRepository cropOrderRepository;
    private final CropRepository cropRepository;
    private final BusinessFirmRepository businessFirmRepository;
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;

    public CropOrderService(
            CropOrderRepository cropOrderRepository,
            CropRepository cropRepository,
            BusinessFirmRepository businessFirmRepository,
            UserRepository userRepository,
            FarmerRepository farmerRepository) {

        this.cropOrderRepository = cropOrderRepository;
        this.cropRepository = cropRepository;
        this.businessFirmRepository = businessFirmRepository;
        this.userRepository = userRepository;
        this.farmerRepository = farmerRepository;
    }

    // ==========================================
    // PLACE ORDER
    // ==========================================

    public CropOrderResponse placeOrder(
            CropOrderRequest request,
            Authentication authentication) {

        // Validate crop ID
        if (request.getCropId() == null) {
            throw new RuntimeException(
                    "Crop ID is required.");
        }

        // Validate quantity
        if (request.getQuantity() == null ||
                request.getQuantity() <= 0) {

            throw new RuntimeException(
                    "Order quantity must be greater than zero.");
        }

        String email = authentication.getName();

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        // Find business profile
        BusinessFirm businessFirm =
                businessFirmRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business profile not found"));

        // Find crop
        Crop crop = cropRepository.findById(
                        request.getCropId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found"));

        // Check availability
        if (!Boolean.TRUE.equals(crop.getAvailable())) {

            throw new RuntimeException(
                    "This crop is currently unavailable.");
        }

        // Check requested quantity
        if (request.getQuantity() > crop.getQuantity()) {

            throw new RuntimeException(
                    "Requested quantity exceeds available quantity.");
        }

        // ==========================================
        // CREATE ORDER
        // ==========================================

        CropOrder order = new CropOrder();

        order.setCrop(crop);

        order.setBusinessFirm(businessFirm);

        order.setQuantity(request.getQuantity());

        // Calculate total price using
        // the price stored in the database
        order.setTotalPrice(
                request.getQuantity()
                        * crop.getPricePerUnit());

        // ==========================================
        // REDUCE CROP QUANTITY
        // ==========================================

        crop.setQuantity(
                crop.getQuantity()
                        - request.getQuantity());

        // If no quantity remains
        // mark crop as unavailable
        if (crop.getQuantity() <= 0) {

            crop.setQuantity(0.0);

            crop.setAvailable(false);
        }

        // Save updated crop
        cropRepository.save(crop);

        // Save order
        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(savedOrder);
    }


    // ==========================================
    // GET MY ORDERS
    // ==========================================

    public List<CropOrderResponse> getMyOrders(
            Authentication authentication) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        BusinessFirm businessFirm =
                businessFirmRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business profile not found"));

        return cropOrderRepository
                .findByBusinessFirm(businessFirm)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ==========================================
    // GET FARMER ORDERS
    // ==========================================

    public List<CropOrderResponse> getFarmerOrders(
            Authentication authentication) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        Farmer farmer = farmerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Farmer profile not found"));

        return cropOrderRepository
                .findByCropFarmer(farmer)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ==========================================
    // APPROVE ORDER
    // ==========================================

    public CropOrderResponse approveOrder(Long id) {

        CropOrder order =
                cropOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        // Only pending orders can be approved
        if (!"PENDING".equals(order.getStatus())) {

            throw new RuntimeException(
                    "Only pending orders can be approved.");
        }

        order.setStatus("APPROVED");

        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(savedOrder);
    }


    // ==========================================
    // REJECT ORDER
    // ==========================================

    public CropOrderResponse rejectOrder(Long id) {

        CropOrder order =
                cropOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        // Only pending orders can be rejected
        if (!"PENDING".equals(order.getStatus())) {

            throw new RuntimeException(
                    "Only pending orders can be rejected.");
        }

        // ==========================================
        // RESTORE CROP QUANTITY
        // ==========================================

        Crop crop = order.getCrop();

        crop.setQuantity(
                crop.getQuantity()
                        + order.getQuantity());

        // Make the crop available again
        // because rejected quantity is returned
        crop.setAvailable(true);

        cropRepository.save(crop);

        // Mark order rejected
        order.setStatus("REJECTED");

        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(savedOrder);
    }


    // ==========================================
    // CONVERT ENTITY TO RESPONSE
    // ==========================================

    private CropOrderResponse convertToResponse(
            CropOrder order) {

        CropOrderResponse response =
                new CropOrderResponse();

        response.setId(
                order.getId());

        response.setCropName(
                order.getCrop()
                        .getCropName());

        response.setBusinessFirmName(
                order.getBusinessFirm()
                        .getCompanyName());

        response.setQuantity(
                order.getQuantity());

        response.setTotalPrice(
                order.getTotalPrice());

        response.setStatus(
                order.getStatus());

        response.setOrderDate(
                order.getOrderDate());

        return response;
    }
}