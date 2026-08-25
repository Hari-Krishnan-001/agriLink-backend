package com.agriconnect.service;

import com.agriconnect.dto.PublicMessageRequest;
import com.agriconnect.dto.PublicMessageResponse;
import com.agriconnect.entity.PublicMessage;
import com.agriconnect.entity.User;
import com.agriconnect.repository.PublicMessageRepository;
import com.agriconnect.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PublicMessageService {

    private final PublicMessageRepository publicMessageRepository;
    private final UserRepository userRepository;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PublicMessageService(
            PublicMessageRepository publicMessageRepository,
            UserRepository userRepository
    ) {

        this.publicMessageRepository =
                publicMessageRepository;

        this.userRepository =
                userRepository;
    }


    // ========================================
    // GET ALL PUBLIC MESSAGES
    // ========================================

    public List<PublicMessageResponse> getAllMessages(
            Authentication authentication
    ) {

        // Make sure the user is authenticated
        getAuthenticatedUser(authentication);

        return publicMessageRepository
                .findAllByOrderBySentAtAsc()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ========================================
    // SEND PUBLIC MESSAGE
    // ========================================

    public PublicMessageResponse sendMessage(
            PublicMessageRequest request,
            Authentication authentication
    ) {

        // Get logged-in user
        User user =
                getAuthenticatedUser(authentication);


        // ========================================
        // VALIDATE REQUEST
        // ========================================

        if (request == null ||
                request.getMessage() == null ||
                request.getMessage().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message cannot be empty."
            );
        }


        // ========================================
        // CLEAN MESSAGE
        // ========================================

        String messageText =
                request.getMessage().trim();


        // ========================================
        // MESSAGE LENGTH
        // ========================================

        if (messageText.length() > 2000) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message cannot exceed 2000 characters."
            );
        }


        // ========================================
        // CREATE MESSAGE
        // ========================================

        PublicMessage publicMessage =
                new PublicMessage();

        publicMessage.setSender(user);

        publicMessage.setMessage(messageText);


        // ========================================
        // SAVE
        // ========================================

        PublicMessage savedMessage =
                publicMessageRepository.save(
                        publicMessage
                );


        // ========================================
        // RESPONSE
        // ========================================

        return convertToResponse(
                savedMessage
        );
    }


    // ========================================
    // GET AUTHENTICATED USER
    // ========================================

    private User getAuthenticatedUser(
            Authentication authentication
    ) {

        if (authentication == null ||
                authentication.getName() == null ||
                authentication.getName().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required."
            );
        }


        String email =
                authentication.getName();


        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found."
                        )
                );
    }


    // ========================================
    // CONVERT ENTITY → RESPONSE
    // ========================================

    private PublicMessageResponse convertToResponse(
            PublicMessage publicMessage
    ) {

        User sender =
                publicMessage.getSender();


        return new PublicMessageResponse(

                publicMessage.getId(),

                sender.getId(),

                sender.getFullName(),

                sender.getEmail(),

                sender.getRole(),

                publicMessage.getMessage(),

                publicMessage.getSentAt()
        );
    }
}