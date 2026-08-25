package com.agriconnect.controller;

import com.agriconnect.dto.PublicMessageRequest;
import com.agriconnect.dto.PublicMessageResponse;
import com.agriconnect.service.PublicMessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class PublicMessageController {

    private final PublicMessageService publicMessageService;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PublicMessageController(
            PublicMessageService publicMessageService
    ) {

        this.publicMessageService =
                publicMessageService;
    }


    // ========================================
    // GET ALL PUBLIC MESSAGES
    // ========================================

    @GetMapping
    public ResponseEntity<List<PublicMessageResponse>> getMessages(
            Authentication authentication
    ) {

        List<PublicMessageResponse> messages =
                publicMessageService.getAllMessages(
                        authentication
                );

        return ResponseEntity.ok(messages);
    }


    // ========================================
    // SEND PUBLIC MESSAGE
    // ========================================

    @PostMapping
    public ResponseEntity<PublicMessageResponse> sendMessage(
            @RequestBody PublicMessageRequest request,
            Authentication authentication
    ) {

        PublicMessageResponse response =
                publicMessageService.sendMessage(
                        request,
                        authentication
                );

        return ResponseEntity.ok(response);
    }
}