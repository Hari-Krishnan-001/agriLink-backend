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

    @PostMapping
    public ProjectResponse createProject(
            @RequestBody ProjectRequest request,
            Authentication authentication) {

        return projectService.createProject(
                request,
                authentication);
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {

        return projectService.getAllProjects();
    }

    @GetMapping("/my-projects")
    public List<ProjectResponse> getMyProjects(
            Authentication authentication) {

        return projectService.getMyProjects(
                authentication);
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id) {

        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @RequestBody ProjectRequest request) {

        return projectService.updateProject(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(
            @PathVariable Long id) {

        return projectService.deleteProject(id);
    }
}