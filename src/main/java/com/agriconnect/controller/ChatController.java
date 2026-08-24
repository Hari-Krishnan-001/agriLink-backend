package com.agriconnect.controller;

import com.agriconnect.dto.ChatMessageRequest;
import com.agriconnect.dto.ChatMessageResponse;
import com.agriconnect.dto.ChatResponse;
import com.agriconnect.service.ChatService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // ==========================================
    // GET OR CREATE CHAT FOR ORDER
    // GET /api/chats/order/{orderId}
    // ==========================================

    @GetMapping("/order/{orderId}")
    public ChatResponse getOrCreateChat(
            @PathVariable Long orderId,
            Authentication authentication) {

        return chatService.getOrCreateChat(
                orderId,
                authentication);
    }


    // ==========================================
    // GET CHAT MESSAGES
    // GET /api/chats/order/{orderId}/messages
    // ==========================================

    @GetMapping("/order/{orderId}/messages")
    public List<ChatMessageResponse> getMessages(
            @PathVariable Long orderId,
            Authentication authentication) {

        return chatService.getMessages(
                orderId,
                authentication);
    }


    // ==========================================
    // SEND MESSAGE
    // POST /api/chats/order/{orderId}/messages
    // ==========================================

    @PostMapping("/order/{orderId}/messages")
    public ChatMessageResponse sendMessage(
            @PathVariable Long orderId,
            @RequestBody ChatMessageRequest request,
            Authentication authentication) {

        return chatService.sendMessage(
                orderId,
                request,
                authentication);
    }
}