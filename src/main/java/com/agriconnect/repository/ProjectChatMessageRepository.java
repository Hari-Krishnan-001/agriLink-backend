package com.agriconnect.repository;

import com.agriconnect.entity.ProjectChat;
import com.agriconnect.entity.ProjectChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectChatMessageRepository
        extends JpaRepository<ProjectChatMessage, Long> {

    // ========================================
    // GET MESSAGES FOR A PROJECT CHAT
    // ========================================

    List<ProjectChatMessage> findByProjectChatOrderBySentAtAsc(
            ProjectChat projectChat
    );
}