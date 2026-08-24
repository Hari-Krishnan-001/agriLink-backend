package com.agriconnect.service;

import com.agriconnect.dto.CropOrderRequest;
import com.agriconnect.dto.CropOrderResponse;
import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Crop;
import com.agriconnect.entity.CropOrder;
import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.User;
import com.agriconnect.repository.BusinessFirmRepository;
import com.agriconnect.repository.CropOrderRepository;
import com.agriconnect.repository.CropRepository;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        if (request.getCropId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Crop ID is required."
            );
        }

        if (request.getQuantity() == null ||
                request.getQuantity() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order quantity must be greater than zero."
            );
        }

        User user =
                getAuthenticatedUser(authentication);

        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Business profile not found."
                                ));

        Crop crop =
                cropRepository
                        .findById(request.getCropId())
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Crop not found."
                                ));

        // Prevent a farmer from ordering
        // their own crop through a business account
        if (crop.getFarmer() != null &&
                crop.getFarmer()
                        .getUser()
                        .getId()
                        .equals(user.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You cannot place an order for your own crop."
            );
        }

        if (!Boolean.TRUE.equals(
                crop.getAvailable())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This crop is currently unavailable."
            );
        }

        if (request.getQuantity()
                > crop.getQuantity()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Requested quantity exceeds available quantity."
            );
        }


        // ==========================================
        // CREATE ORDER
        // ==========================================

        CropOrder order = new CropOrder();

        order.setCrop(crop);

        order.setBusinessFirm(
                businessFirm);

        order.setQuantity(
                request.getQuantity());

        order.setTotalPrice(
                request.getQuantity()
                        * crop.getPricePerUnit());


        // ==========================================
        // REDUCE CROP QUANTITY
        // ==========================================

        double remainingQuantity =
                crop.getQuantity()
                        - request.getQuantity();

        if (remainingQuantity <= 0) {

            crop.setQuantity(0.0);

            crop.setAvailable(false);

        } else {

            crop.setQuantity(
                    remainingQuantity);
        }


        cropRepository.save(crop);

        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(
                savedOrder);
    }


    // ==========================================
    // GET MY ORDERS
    // ==========================================

    public List<CropOrderResponse> getMyOrders(
            Authentication authentication) {

        User user =
                getAuthenticatedUser(authentication);

        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Business profile not found."
                                ));

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

        User user =
                getAuthenticatedUser(authentication);

        Farmer farmer =
                farmerRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Farmer profile not found."
                                ));

        return cropOrderRepository
                .findByCropFarmer(farmer)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ==========================================
    // APPROVE ORDER
    // ==========================================

    public CropOrderResponse approveOrder(
            Long id,
            Authentication authentication) {

        Farmer farmer =
                getAuthenticatedFarmer(authentication);

        CropOrder order =
                cropOrderRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Order not found."
                                ));

        verifyFarmerOwnsOrder(
                order,
                farmer);

        if (!"PENDING".equals(
                order.getStatus())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only pending orders can be approved."
            );
        }

        order.setStatus("APPROVED");

        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(
                savedOrder);
    }


    // ==========================================
    // REJECT ORDER
    // ==========================================

    public CropOrderResponse rejectOrder(
            Long id,
            Authentication authentication) {

        Farmer farmer =
                getAuthenticatedFarmer(authentication);

        CropOrder order =
                cropOrderRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Order not found."
                                ));

        verifyFarmerOwnsOrder(
                order,
                farmer);

        if (!"PENDING".equals(
                order.getStatus())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only pending orders can be rejected."
            );
        }


        // ==========================================
        // RESTORE CROP QUANTITY
        // ==========================================

        Crop crop =
                order.getCrop();

        crop.setQuantity(
                crop.getQuantity()
                        + order.getQuantity());

        crop.setAvailable(true);

        cropRepository.save(crop);


        // ==========================================
        // MARK ORDER REJECTED
        // ==========================================

        order.setStatus("REJECTED");

        CropOrder savedOrder =
                cropOrderRepository.save(order);

        return convertToResponse(
                savedOrder);
    }


    // ==========================================
    // GET AUTHENTICATED USER
    // ==========================================

    private User getAuthenticatedUser(
            Authentication authentication) {

        if (authentication == null ||
                authentication.getName() == null) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required."
            );
        }

        return userRepository
                .findByEmail(
                        authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found."
                        ));
    }


    // ==========================================
    // GET AUTHENTICATED FARMER
    // ==========================================

    private Farmer getAuthenticatedFarmer(
            Authentication authentication) {

        User user =
                getAuthenticatedUser(
                        authentication);

        return farmerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Farmer profile not found."
                        ));
    }


    // ==========================================
    // VERIFY ORDER OWNERSHIP
    // ==========================================

    private void verifyFarmerOwnsOrder(
            CropOrder order,
            Farmer farmer) {

        if (order.getCrop() == null ||
                order.getCrop().getFarmer() == null ||
                !order.getCrop()
                        .getFarmer()
                        .getId()
                        .equals(farmer.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to manage this order."
            );
        }
    }


    // ==========================================
    // CONVERT ENTITY → RESPONSE
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