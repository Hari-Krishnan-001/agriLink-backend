package com.agriconnect.service;

import com.agriconnect.dto.ChatMessageRequest;
import com.agriconnect.dto.ChatMessageResponse;
import com.agriconnect.dto.ChatResponse;
import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Chat;
import com.agriconnect.entity.ChatMessage;
import com.agriconnect.entity.CropOrder;
import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.User;
import com.agriconnect.repository.BusinessFirmRepository;
import com.agriconnect.repository.ChatMessageRepository;
import com.agriconnect.repository.ChatRepository;
import com.agriconnect.repository.CropOrderRepository;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final CropOrderRepository cropOrderRepository;
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final BusinessFirmRepository businessFirmRepository;

    public ChatService(
            ChatRepository chatRepository,
            ChatMessageRepository chatMessageRepository,
            CropOrderRepository cropOrderRepository,
            UserRepository userRepository,
            FarmerRepository farmerRepository,
            BusinessFirmRepository businessFirmRepository) {

        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.cropOrderRepository = cropOrderRepository;
        this.userRepository = userRepository;
        this.farmerRepository = farmerRepository;
        this.businessFirmRepository = businessFirmRepository;
    }

    // ==========================================
    // GET OR CREATE CHAT
    // ==========================================

    public ChatResponse getOrCreateChat(
            Long orderId,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        CropOrder order = getOrder(orderId);

        // Only the farmer who owns the crop
        // OR the business that placed the order
        // can access the chat.
        verifyChatAccess(order, user);

        Chat chat = chatRepository
                .findByOrder(order)
                .orElseGet(() -> {

                    Chat newChat = new Chat();

                    newChat.setOrder(order);

                    return chatRepository.save(newChat);
                });

        return convertToChatResponse(chat);
    }


    // ==========================================
    // GET MESSAGES
    // ==========================================

    public List<ChatMessageResponse> getMessages(
            Long orderId,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        CropOrder order = getOrder(orderId);

        verifyChatAccess(order, user);

        Chat chat = chatRepository
                .findByOrder(order)
                .orElse(null);

        // No chat means no messages yet.
        if (chat == null) {
            return List.of();
        }

        return chatMessageRepository
                .findByChatOrderBySentAtAsc(chat)
                .stream()
                .map(this::convertToMessageResponse)
                .toList();
    }


    // ==========================================
    // SEND MESSAGE
    // ==========================================

    public ChatMessageResponse sendMessage(
            Long orderId,
            ChatMessageRequest request,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        CropOrder order = getOrder(orderId);

        verifyChatAccess(order, user);

        // Validate message
        if (request == null ||
                request.getMessage() == null ||
                request.getMessage().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message cannot be empty.");
        }

        String messageText =
                request.getMessage().trim();

        if (messageText.length() > 2000) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message cannot exceed 2000 characters.");
        }

        // Get existing chat or create one
        Chat chat = chatRepository
                .findByOrder(order)
                .orElseGet(() -> {

                    Chat newChat = new Chat();

                    newChat.setOrder(order);

                    return chatRepository.save(newChat);
                });

        // Create message
        ChatMessage chatMessage =
                new ChatMessage();

        chatMessage.setChat(chat);
        chatMessage.setSender(user);
        chatMessage.setMessage(messageText);

        ChatMessage savedMessage =
                chatMessageRepository.save(
                        chatMessage);

        return convertToMessageResponse(
                savedMessage);
    }


    // ==========================================
    // GET ORDER
    // ==========================================

    private CropOrder getOrder(Long orderId) {

        if (orderId == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order ID is required.");
        }

        return cropOrderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Order not found."));
    }


    // ==========================================
    // VERIFY CHAT ACCESS
    // ==========================================

    private void verifyChatAccess(
            CropOrder order,
            User user) {

        // -------------------------------
        // Check Farmer
        // -------------------------------

        if (order.getCrop() != null &&
                order.getCrop().getFarmer() != null &&
                order.getCrop()
                        .getFarmer()
                        .getUser() != null &&
                order.getCrop()
                        .getFarmer()
                        .getUser()
                        .getId()
                        .equals(user.getId())) {

            return;
        }


        // -------------------------------
        // Check Business
        // -------------------------------

        if (order.getBusinessFirm() != null &&
                order.getBusinessFirm().getUser() != null &&
                order.getBusinessFirm()
                        .getUser()
                        .getId()
                        .equals(user.getId())) {

            return;
        }


        // -------------------------------
        // Not authorized
        // -------------------------------

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not authorized to access this order chat.");
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
                    "Authentication required.");
        }

        return userRepository
                .findByEmail(
                        authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found."));
    }


    // ==========================================
    // CONVERT CHAT → RESPONSE
    // ==========================================

    private ChatResponse convertToChatResponse(
            Chat chat) {

        CropOrder order = chat.getOrder();

        ChatResponse response =
                new ChatResponse();

        response.setChatId(
                chat.getId());

        response.setOrderId(
                order.getId());

        response.setCropName(
                order.getCrop()
                        .getCropName());

        response.setFarmerName(
                order.getCrop()
                        .getFarmer()
                        .getUser()
                        .getFullName());

        response.setBusinessName(
                order.getBusinessFirm()
                        .getCompanyName());

        response.setCreatedAt(
                chat.getCreatedAt());

        return response;
    }


    // ==========================================
    // CONVERT MESSAGE → RESPONSE
    // ==========================================

    private ChatMessageResponse convertToMessageResponse(
            ChatMessage chatMessage) {

        User sender =
                chatMessage.getSender();

        ChatMessageResponse response =
                new ChatMessageResponse();

        response.setId(
                chatMessage.getId());

        response.setSenderId(
                sender.getId());

        response.setSenderName(
                sender.getFullName());

        response.setSenderRole(
                sender.getRole());

        response.setMessage(
                chatMessage.getMessage());

        response.setSentAt(
                chatMessage.getSentAt());

        return response;
    }
}