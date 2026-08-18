package com.agriconnect.service;

import com.agriconnect.dto.CropOrderRequest;
import com.agriconnect.dto.CropOrderResponse;
import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Crop;
import com.agriconnect.entity.CropOrder;
import com.agriconnect.entity.User;
import com.agriconnect.repository.BusinessFirmRepository;
import com.agriconnect.repository.CropOrderRepository;
import com.agriconnect.repository.CropRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropOrderService {

    private final CropOrderRepository cropOrderRepository;
    private final CropRepository cropRepository;
    private final BusinessFirmRepository businessFirmRepository;
    private final UserRepository userRepository;

    public CropOrderService(
            CropOrderRepository cropOrderRepository,
            CropRepository cropRepository,
            BusinessFirmRepository businessFirmRepository,
            UserRepository userRepository) {

        this.cropOrderRepository = cropOrderRepository;
        this.cropRepository = cropRepository;
        this.businessFirmRepository = businessFirmRepository;
        this.userRepository = userRepository;
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

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
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