package com.agriconnect.controller;

import com.agriconnect.dto.ProjectChatMessageRequest;
import com.agriconnect.dto.ProjectChatMessageResponse;
import com.agriconnect.dto.ProjectChatResponse;
import com.agriconnect.service.ProjectChatService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-chats")
public class ProjectChatController {

    private final ProjectChatService projectChatService;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectChatController(
            ProjectChatService projectChatService) {

        this.projectChatService =
                projectChatService;
    }


    // ========================================
    // GET OR CREATE CHAT FOR PROJECT
    //
    // GET
    // /api/project-chats/project/{projectId}
    // ========================================

    @GetMapping("/project/{projectId}")
    public ProjectChatResponse getOrCreateChat(
            @PathVariable Long projectId,
            Authentication authentication) {

        return projectChatService.getOrCreateChat(
                projectId,
                authentication
        );
    }


    // ========================================
    // GET STUDENT PROJECT CHATS
    //
    // GET
    // /api/project-chats/student
    // ========================================

    @GetMapping("/student")
    public List<ProjectChatResponse> getStudentChats(
            Authentication authentication) {

        return projectChatService.getStudentChats(
                authentication
        );
    }


    // ========================================
    // GET BUSINESS PROJECT CHATS
    //
    // GET
    // /api/project-chats/business
    // ========================================

    @GetMapping("/business")
    public List<ProjectChatResponse> getBusinessChats(
            Authentication authentication) {

        return projectChatService.getBusinessChats(
                authentication
        );
    }


    // ========================================
    // GET SPECIFIC PROJECT CHAT
    //
    // GET
    // /api/project-chats/project/{projectId}/details
    // ========================================

    @GetMapping("/project/{projectId}/details")
    public ProjectChatResponse getChat(
            @PathVariable Long projectId,
            Authentication authentication) {

        return projectChatService.getChat(
                projectId,
                authentication
        );
    }


    // ========================================
    // GET CHAT MESSAGES
    //
    // GET
    // /api/project-chats/project/{projectId}/messages
    // ========================================

    @GetMapping(
            "/project/{projectId}/messages"
    )
    public List<ProjectChatMessageResponse> getMessages(
            @PathVariable Long projectId,
            Authentication authentication) {

        return projectChatService.getMessages(
                projectId,
                authentication
        );
    }


    // ========================================
    // SEND MESSAGE
    //
    // POST
    // /api/project-chats/project/{projectId}/messages
    // ========================================

    @PostMapping(
            "/project/{projectId}/messages"
    )
    public ProjectChatMessageResponse sendMessage(
            @PathVariable Long projectId,
            @RequestBody ProjectChatMessageRequest request,
            Authentication authentication) {

        return projectChatService.sendMessage(
                projectId,
                request,
                authentication
        );
    }
}