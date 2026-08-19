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

    public CropOrderResponse placeOrder(
            CropOrderRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        BusinessFirm businessFirm =
                businessFirmRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business profile not found"));

        Crop crop = cropRepository.findById(
                        request.getCropId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found"));

        CropOrder order = new CropOrder();

        order.setCrop(crop);

        order.setBusinessFirm(businessFirm);

        order.setQuantity(request.getQuantity());

        order.setTotalPrice(
                request.getQuantity()
                        * crop.getPricePerUnit());

        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(savedOrder);
    }

    public List<CropOrderResponse> getMyOrders(
            Authentication authentication) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

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

    public List<CropOrderResponse> getFarmerOrders(
            Authentication authentication) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

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

    public CropOrderResponse approveOrder(Long id) {

        CropOrder order =
                cropOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        order.setStatus("APPROVED");

        return convertToResponse(
                cropOrderRepository.save(order));
    }

    public CropOrderResponse rejectOrder(Long id) {

        CropOrder order =
                cropOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        order.setStatus("REJECTED");

        return convertToResponse(
                cropOrderRepository.save(order));
    }

    private CropOrderResponse convertToResponse(
            CropOrder order) {

        CropOrderResponse response =
                new CropOrderResponse();

        response.setId(order.getId());

        response.setCropName(
                order.getCrop().getCropName());

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