package com.agriconnect.repository;

import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Project;
import com.agriconnect.entity.ProjectChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectChatRepository
        extends JpaRepository<ProjectChat, Long> {

    // ========================================
    // FIND CHAT FOR PROJECT + BUSINESS
    // ========================================

    Optional<ProjectChat> findByProjectAndBusinessFirm(
            Project project,
            BusinessFirm businessFirm
    );


    // ========================================
    // CHECK WHETHER CHAT EXISTS
    // ========================================

    boolean existsByProjectAndBusinessFirm(
            Project project,
            BusinessFirm businessFirm
    );


    // ========================================
    // GET ALL CHATS FOR A PROJECT
    // ========================================

    List<ProjectChat> findByProject(
            Project project
    );


    // ========================================
    // GET ALL CHATS FOR A BUSINESS
    // ========================================

    List<ProjectChat> findByBusinessFirm(
            BusinessFirm businessFirm
    );
}