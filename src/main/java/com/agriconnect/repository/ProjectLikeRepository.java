package com.agriconnect.repository;

import com.agriconnect.entity.Project;
import com.agriconnect.entity.ProjectLike;
import com.agriconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeRepository
        extends JpaRepository<ProjectLike, Long> {

    Optional<ProjectLike> findByProjectAndUser(
            Project project,
            User user
    );

    boolean existsByProjectAndUser(
            Project project,
            User user
    );

    long countByProject(Project project);
}