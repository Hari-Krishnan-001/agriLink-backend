package com.agriconnect.service;

import com.agriconnect.dto.ProjectRequest;
import com.agriconnect.dto.ProjectResponse;
import com.agriconnect.entity.Project;
import com.agriconnect.entity.ProjectLike;
import com.agriconnect.entity.Student;
import com.agriconnect.entity.User;
import com.agriconnect.repository.ProjectLikeRepository;
import com.agriconnect.repository.ProjectRepository;
import com.agriconnect.repository.StudentRepository;
import com.agriconnect.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectLikeRepository projectLikeRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProjectService(
            ProjectRepository projectRepository,
            ProjectLikeRepository projectLikeRepository,
            StudentRepository studentRepository,
            UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.projectLikeRepository = projectLikeRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }


    // ========================================
    // CREATE PROJECT
    // ========================================

    public ProjectResponse createProject(
            ProjectRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = getUser(email);

        Student student =
                studentRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student profile not found"
                                ));

        Project project = new Project();

        project.setStudent(student);

        project.setTitle(
                request.getTitle()
        );

        project.setProblem(
                request.getProblem()
        );

        project.setSolution(
                request.getSolution()
        );

        project.setTechnology(
                request.getTechnology()
        );

        project.setFundingRequired(
                request.getFundingRequired()
        );

        project.setDescription(
                request.getDescription()
        );

        Project savedProject =
                projectRepository.save(project);

        return convertToResponse(
                savedProject,
                authentication
        );
    }


    // ========================================
    // GET ALL PROJECTS
    // ========================================

    public List<ProjectResponse> getAllProjects(
            Authentication authentication) {

        return projectRepository.findAll()
                .stream()
                .map(project ->
                        convertToResponse(
                                project,
                                authentication
                        ))
                .toList();
    }


    // ========================================
    // GET MY PROJECTS
    // ========================================

    public List<ProjectResponse> getMyProjects(
            Authentication authentication) {

        String email = authentication.getName();

        User user = getUser(email);

        return studentRepository.findByUser(user)
                .map(student ->
                        projectRepository
                                .findByStudent(student)
                                .stream()
                                .map(project ->
                                        convertToResponse(
                                                project,
                                                authentication
                                        ))
                                .toList()
                )
                .orElseGet(List::of);
    }


    // ========================================
    // GET PROJECT BY ID
    // ========================================

    public ProjectResponse getProjectById(
            Long id,
            Authentication authentication) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));

        return convertToResponse(
                project,
                authentication
        );
    }


    // ========================================
    // UPDATE PROJECT
    // ========================================

    public ProjectResponse updateProject(
            Long id,
            ProjectRequest request,
            Authentication authentication) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));

        verifyProjectOwnership(
                project,
                authentication
        );

        project.setTitle(
                request.getTitle()
        );

        project.setProblem(
                request.getProblem()
        );

        project.setSolution(
                request.getSolution()
        );

        project.setTechnology(
                request.getTechnology()
        );

        project.setFundingRequired(
                request.getFundingRequired()
        );

        project.setDescription(
                request.getDescription()
        );

        Project updatedProject =
                projectRepository.save(project);

        return convertToResponse(
                updatedProject,
                authentication
        );
    }


    // ========================================
    // DELETE PROJECT
    // ========================================

    public String deleteProject(
            Long id,
            Authentication authentication) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));

        verifyProjectOwnership(
                project,
                authentication
        );

        projectRepository.delete(project);

        return "Project deleted successfully";
    }


    // ========================================
    // ADD PROJECT VIEW
    // ========================================

    public ProjectResponse addProjectView(
            Long id,
            Authentication authentication) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));

        /*
         * Project owner should not increase
         * their own project view count.
         */

        if (authentication != null) {

            String email =
                    authentication.getName();

            String ownerEmail =
                    project.getStudent()
                            .getUser()
                            .getEmail();

            if (!email.equals(ownerEmail)) {

                Long currentViews =
                        project.getViews() == null
                                ? 0L
                                : project.getViews();

                project.setViews(
                        currentViews + 1
                );

                project =
                        projectRepository.save(project);
            }
        }

        return convertToResponse(
                project,
                authentication
        );
    }


    // ========================================
    // LIKE / UNLIKE PROJECT
    // ========================================

    public ProjectResponse toggleLike(
            Long id,
            Authentication authentication) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));

        String email =
                authentication.getName();

        User user = getUser(email);

        /*
         * Check whether this user has already
         * liked this project.
         */

        var existingLike =
                projectLikeRepository
                        .findByProjectAndUser(
                                project,
                                user
                        );

        if (existingLike.isPresent()) {

            // ====================================
            // UNLIKE
            // ====================================

            projectLikeRepository.delete(
                    existingLike.get()
            );

            long currentLikes =
                    project.getLikes() == null
                            ? 0L
                            : project.getLikes();

            project.setLikes(
                    Math.max(
                            0L,
                            currentLikes - 1
                    )
            );

        } else {

            // ====================================
            // LIKE
            // ====================================

            ProjectLike projectLike =
                    new ProjectLike();

            projectLike.setProject(project);

            projectLike.setUser(user);

            projectLikeRepository.save(
                    projectLike
            );

            long currentLikes =
                    project.getLikes() == null
                            ? 0L
                            : project.getLikes();

            project.setLikes(
                    currentLikes + 1
            );
        }

        Project updatedProject =
                projectRepository.save(project);

        return convertToResponse(
                updatedProject,
                authentication
        );
    }


    // ========================================
    // CHECK WHETHER USER LIKED PROJECT
    // ========================================

    public boolean hasUserLiked(
            Long id,
            Authentication authentication) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Project not found"
                                ));

        String email =
                authentication.getName();

        User user = getUser(email);

        return projectLikeRepository
                .existsByProjectAndUser(
                        project,
                        user
                );
    }


    // ========================================
    // GET USER
    // ========================================

    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));
    }


    // ========================================
    // VERIFY PROJECT OWNERSHIP
    // ========================================

    private void verifyProjectOwnership(
            Project project,
            Authentication authentication) {

        String loggedInEmail =
                authentication.getName();

        String projectOwnerEmail =
                project.getStudent()
                        .getUser()
                        .getEmail();

        if (!loggedInEmail.equals(
                projectOwnerEmail)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to modify this project"
            );
        }
    }


    // ========================================
    // CONVERT ENTITY → RESPONSE
    // ========================================

    private ProjectResponse convertToResponse(
            Project project,
            Authentication authentication) {

        ProjectResponse response =
                new ProjectResponse();

        response.setId(
                project.getId()
        );

        response.setStudentName(
                project.getStudent()
                        .getUser()
                        .getFullName()
        );

        response.setStudentEmail(
                project.getStudent()
                        .getUser()
                        .getEmail()
        );


        // ====================================
        // CHECK PROJECT OWNER
        // ====================================

        boolean isOwner = false;

        if (authentication != null) {

            String loggedInEmail =
                    authentication.getName();

            String projectOwnerEmail =
                    project.getStudent()
                            .getUser()
                            .getEmail();

            isOwner =
                    loggedInEmail.equals(
                            projectOwnerEmail
                    );
        }

        response.setOwner(isOwner);


        response.setTitle(
                project.getTitle()
        );

        response.setProblem(
                project.getProblem()
        );

        response.setSolution(
                project.getSolution()
        );

        response.setTechnology(
                project.getTechnology()
        );

        response.setFundingRequired(
                project.getFundingRequired()
        );

        response.setStatus(
                project.getStatus()
        );

        response.setDescription(
                project.getDescription()
        );

        response.setViews(
                project.getViews()
        );

        response.setLikes(
                project.getLikes()
        );

        response.setCreatedAt(
                project.getCreatedAt()
        );

        return response;
    }
}