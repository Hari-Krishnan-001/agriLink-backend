package com.agriconnect.service;

import com.agriconnect.dto.ProjectChatMessageRequest;
import com.agriconnect.dto.ProjectChatMessageResponse;
import com.agriconnect.dto.ProjectChatResponse;
import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Project;
import com.agriconnect.entity.ProjectChat;
import com.agriconnect.entity.ProjectChatMessage;
import com.agriconnect.entity.Student;
import com.agriconnect.entity.User;
import com.agriconnect.repository.BusinessFirmRepository;
import com.agriconnect.repository.ProjectChatMessageRepository;
import com.agriconnect.repository.ProjectChatRepository;
import com.agriconnect.repository.ProjectRepository;
import com.agriconnect.repository.StudentRepository;
import com.agriconnect.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectChatService {

    private final ProjectChatRepository projectChatRepository;
    private final ProjectChatMessageRepository projectChatMessageRepository;
    private final ProjectRepository projectRepository;
    private final BusinessFirmRepository businessFirmRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectChatService(
            ProjectChatRepository projectChatRepository,
            ProjectChatMessageRepository projectChatMessageRepository,
            ProjectRepository projectRepository,
            BusinessFirmRepository businessFirmRepository,
            StudentRepository studentRepository,
            UserRepository userRepository) {

        this.projectChatRepository =
                projectChatRepository;

        this.projectChatMessageRepository =
                projectChatMessageRepository;

        this.projectRepository =
                projectRepository;

        this.businessFirmRepository =
                businessFirmRepository;

        this.studentRepository =
                studentRepository;

        this.userRepository =
                userRepository;
    }


    // ========================================
    // GET OR CREATE CHAT FOR PROJECT
    // ========================================

    public ProjectChatResponse getOrCreateChat(
            Long projectId,
            Authentication authentication) {

        User currentUser =
                getCurrentUser(authentication);

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));


        // ====================================
        // CHECK BUSINESS
        // ====================================

        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(currentUser)
                        .orElse(null);


        if (businessFirm != null) {

            ProjectChat chat =
                    projectChatRepository
                            .findByProjectAndBusinessFirm(
                                    project,
                                    businessFirm
                            )
                            .orElseGet(() -> {

                                ProjectChat newChat =
                                        new ProjectChat();

                                newChat.setProject(project);

                                newChat.setBusinessFirm(
                                        businessFirm
                                );

                                return projectChatRepository.save(
                                        newChat
                                );
                            });

            return convertToResponse(chat);
        }


        // ====================================
        // CHECK STUDENT
        // ====================================

        Student student =
                studentRepository
                        .findByUser(currentUser)
                        .orElse(null);


        if (student != null) {

            verifyStudentProjectOwnership(
                    project,
                    currentUser
            );


            List<ProjectChat> chats =
                    projectChatRepository
                            .findByProject(project);


            if (chats.isEmpty()) {

                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No business chat exists for this project"
                );
            }


            /*
             * If the student has multiple business
             * conversations, the first one is returned
             * for backward compatibility.
             *
             * The student dashboard will use the
             * /student endpoint to show every chat.
             */

            return convertToResponse(
                    chats.get(0)
            );
        }


        // ====================================
        // FORBIDDEN
        // ====================================

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Only students and businesses can access project chat"
        );
    }


    // ========================================
    // GET STUDENT PROJECT CHATS
    //
    // GET /api/project-chats/student
    // ========================================

    public List<ProjectChatResponse> getStudentChats(
            Authentication authentication) {

        User currentUser =
                getCurrentUser(authentication);


        Student student =
                studentRepository
                        .findByUser(currentUser)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.FORBIDDEN,
                                        "Only students can access student project chats"
                                ));


        List<Project> projects =
                projectRepository.findByStudent(student);


        List<ProjectChatResponse> responses =
                new ArrayList<>();


        for (Project project : projects) {

            List<ProjectChat> chats =
                    projectChatRepository
                            .findByProject(project);


            for (ProjectChat chat : chats) {

                responses.add(
                        convertToResponse(chat)
                );
            }
        }


        return responses;
    }


    // ========================================
    // GET BUSINESS PROJECT CHATS
    //
    // GET /api/project-chats/business
    // ========================================

    public List<ProjectChatResponse> getBusinessChats(
            Authentication authentication) {

        User currentUser =
                getCurrentUser(authentication);


        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(currentUser)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.FORBIDDEN,
                                        "Only businesses can access business project chats"
                                ));


        List<ProjectChat> chats =
                projectChatRepository
                        .findByBusinessFirm(businessFirm);


        return chats.stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ========================================
    // GET SPECIFIC CHAT
    // ========================================

    public ProjectChatResponse getChat(
            Long projectId,
            Authentication authentication) {

        ProjectChat chat =
                getAuthorizedChat(
                        projectId,
                        authentication
                );

        return convertToResponse(chat);
    }


    // ========================================
    // GET CHAT MESSAGES
    // ========================================

    public List<ProjectChatMessageResponse> getMessages(
            Long projectId,
            Authentication authentication) {

        ProjectChat chat =
                getAuthorizedChat(
                        projectId,
                        authentication
                );


        return projectChatMessageRepository
                .findByProjectChatOrderBySentAtAsc(chat)
                .stream()
                .map(this::convertMessageToResponse)
                .toList();
    }


    // ========================================
    // SEND MESSAGE
    // ========================================

    public ProjectChatMessageResponse sendMessage(
            Long projectId,
            ProjectChatMessageRequest request,
            Authentication authentication) {

        if (request == null ||
                request.getMessage() == null ||
                request.getMessage().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message cannot be empty"
            );
        }


        ProjectChat chat =
                getAuthorizedChat(
                        projectId,
                        authentication
                );


        User sender =
                getCurrentUser(authentication);


        ProjectChatMessage projectChatMessage =
                new ProjectChatMessage();


        projectChatMessage.setProjectChat(
                chat
        );


        projectChatMessage.setSender(
                sender
        );


        projectChatMessage.setMessage(
                request.getMessage().trim()
        );


        ProjectChatMessage savedMessage =
                projectChatMessageRepository.save(
                        projectChatMessage
                );


        return convertMessageToResponse(
                savedMessage
        );
    }


    // ========================================
    // AUTHORIZE PROJECT CHAT
    // ========================================

    private ProjectChat getAuthorizedChat(
            Long projectId,
            Authentication authentication) {

        User currentUser =
                getCurrentUser(authentication);


        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));


        // ====================================
        // STUDENT
        // ====================================

        Student student =
                studentRepository
                        .findByUser(currentUser)
                        .orElse(null);


        if (student != null) {

            verifyStudentProjectOwnership(
                    project,
                    currentUser
            );


            List<ProjectChat> chats =
                    projectChatRepository
                            .findByProject(project);


            if (chats.isEmpty()) {

                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Project chat not found"
                );
            }


            /*
             * Backward-compatible behavior.
             *
             * The dashboard will show individual
             * conversations later.
             */

            return chats.get(0);
        }


        // ====================================
        // BUSINESS
        // ====================================

        BusinessFirm businessFirm =
                businessFirmRepository
                        .findByUser(currentUser)
                        .orElse(null);


        if (businessFirm != null) {

            return projectChatRepository
                    .findByProjectAndBusinessFirm(
                            project,
                            businessFirm
                    )
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.FORBIDDEN,
                                    "You are not authorized to access this project chat"
                            ));
        }


        // ====================================
        // FORBIDDEN
        // ====================================

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not authorized to access this project chat"
        );
    }


    // ========================================
    // VERIFY STUDENT PROJECT OWNERSHIP
    // ========================================

    private void verifyStudentProjectOwnership(
            Project project,
            User currentUser) {

        if (project.getStudent() == null ||
                project.getStudent().getUser() == null) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Project owner could not be determined"
            );
        }


        String ownerEmail =
                project.getStudent()
                        .getUser()
                        .getEmail();


        String currentEmail =
                currentUser.getEmail();


        if (!ownerEmail.equals(currentEmail)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to access this project chat"
            );
        }
    }


    // ========================================
    // GET CURRENT USER
    // ========================================

    private User getCurrentUser(
            Authentication authentication) {

        if (authentication == null ||
                authentication.getName() == null) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }


        return userRepository
                .findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "User not found"
                        ));
    }


    // ========================================
    // ENTITY → CHAT RESPONSE
    // ========================================

    private ProjectChatResponse convertToResponse(
            ProjectChat chat) {

        ProjectChatResponse response =
                new ProjectChatResponse();


        response.setId(
                chat.getId()
        );


        response.setProjectId(
                chat.getProject().getId()
        );


        response.setProjectTitle(
                chat.getProject().getTitle()
        );


        response.setStudentName(
                chat.getProject()
                        .getStudent()
                        .getUser()
                        .getFullName()
        );


        response.setStudentEmail(
                chat.getProject()
                        .getStudent()
                        .getUser()
                        .getEmail()
        );


        response.setBusinessName(
                chat.getBusinessFirm()
                        .getCompanyName()
        );


        response.setCreatedAt(
                chat.getCreatedAt()
        );


        return response;
    }


    // ========================================
    // ENTITY → MESSAGE RESPONSE
    // ========================================

    private ProjectChatMessageResponse
    convertMessageToResponse(
            ProjectChatMessage message) {

        ProjectChatMessageResponse response =
                new ProjectChatMessageResponse();


        response.setId(
                message.getId()
        );


        response.setSenderName(
                message.getSender()
                        .getFullName()
        );


        response.setSenderEmail(
                message.getSender()
                        .getEmail()
        );


        response.setMessage(
                message.getMessage()
        );


        response.setSentAt(
                message.getSentAt()
        );


        return response;
    }
}