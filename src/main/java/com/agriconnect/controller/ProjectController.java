package com.agriconnect.controller;

import com.agriconnect.dto.ProjectRequest;
import com.agriconnect.dto.ProjectResponse;
import com.agriconnect.service.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    // ========================================
    // CREATE PROJECT
    // POST /api/projects
    // ========================================

    @PostMapping
    public ProjectResponse createProject(
            @RequestBody ProjectRequest request,
            Authentication authentication) {

        return projectService.createProject(
                request,
                authentication
        );
    }


    // ========================================
    // GET ALL PROJECTS
    // GET /api/projects
    // ========================================

    @GetMapping
    public List<ProjectResponse> getAllProjects() {

        return projectService.getAllProjects();
    }


    // ========================================
    // GET MY PROJECTS
    // GET /api/projects/my-projects
    // ========================================

    @GetMapping("/my-projects")
    public List<ProjectResponse> getMyProjects(
            Authentication authentication) {

        return projectService.getMyProjects(
                authentication
        );
    }


    // ========================================
    // GET PROJECT BY ID
    // GET /api/projects/{id}
    // ========================================

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id) {

        return projectService.getProjectById(id);
    }


    // ========================================
    // UPDATE PROJECT
    // PUT /api/projects/{id}
    // ========================================

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @RequestBody ProjectRequest request,
            Authentication authentication) {

        return projectService.updateProject(
                id,
                request,
                authentication
        );
    }


    // ========================================
    // DELETE PROJECT
    // DELETE /api/projects/{id}
    // ========================================

    @DeleteMapping("/{id}")
    public String deleteProject(
            @PathVariable Long id,
            Authentication authentication) {

        return projectService.deleteProject(
                id,
                authentication
        );
    }


    // ========================================
    // ADD PROJECT VIEW
    // POST /api/projects/{id}/view
    // ========================================

    @PostMapping("/{id}/view")
    public ProjectResponse addProjectView(
            @PathVariable Long id,
            Authentication authentication) {

        return projectService.addProjectView(
                id,
                authentication
        );
    }


    // ========================================
    // LIKE / UNLIKE PROJECT
    // POST /api/projects/{id}/like
    // ========================================

    @PostMapping("/{id}/like")
    public ProjectResponse toggleLike(
            @PathVariable Long id,
            Authentication authentication) {

        return projectService.toggleLike(
                id,
                authentication
        );
    }


    // ========================================
    // CHECK USER LIKE
    // GET /api/projects/{id}/liked
    // ========================================

    @GetMapping("/{id}/liked")
    public boolean hasUserLiked(
            @PathVariable Long id,
            Authentication authentication) {

        return projectService.hasUserLiked(
                id,
                authentication
        );
    }
}