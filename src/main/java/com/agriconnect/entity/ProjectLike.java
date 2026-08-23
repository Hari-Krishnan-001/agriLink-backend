package com.agriconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "project_likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "project_id",
                                "user_id"
                        }
                )
        }
)
public class ProjectLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ========================================
    // PROJECT
    // ========================================

    @ManyToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;


    // ========================================
    // USER
    // ========================================

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectLike() {
    }


    // ========================================
    // GETTERS / SETTERS
    // ========================================

    public Long getId() {
        return id;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}