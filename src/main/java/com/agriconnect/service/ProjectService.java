package com.agriconnect.service;

import com.agriconnect.dto.ProjectRequest;
import com.agriconnect.dto.ProjectResponse;
import com.agriconnect.entity.Project;
import com.agriconnect.entity.Student;
import com.agriconnect.entity.User;
import com.agriconnect.repository.ProjectRepository;
import com.agriconnect.repository.StudentRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          StudentRepository studentRepository,
                          UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public ProjectResponse createProject(ProjectRequest request,
                                         Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        Project project = new Project();

        project.setStudent(student);
        project.setTitle(request.getTitle());
        project.setProblem(request.getProblem());
        project.setSolution(request.getSolution());
        project.setTechnology(request.getTechnology());
        project.setFundingRequired(request.getFundingRequired());
        project.setDescription(request.getDescription());

        Project savedProject = projectRepository.save(project);

        return convertToResponse(savedProject);
    }

    public List<ProjectResponse> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ProjectResponse> getMyProjects(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        return projectRepository.findByStudent(student)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        return convertToResponse(project);
    }

    public ProjectResponse updateProject(Long id,
                                         ProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        project.setTitle(request.getTitle());
        project.setProblem(request.getProblem());
        project.setSolution(request.getSolution());
        project.setTechnology(request.getTechnology());
        project.setFundingRequired(request.getFundingRequired());
        project.setDescription(request.getDescription());

        Project updatedProject = projectRepository.save(project);

        return convertToResponse(updatedProject);
    }

    public String deleteProject(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        projectRepository.delete(project);

        return "Project deleted successfully";
    }

    private ProjectResponse convertToResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setStudentName(
                project.getStudent().getUser().getFullName());

        response.setStudentEmail(
                project.getStudent().getUser().getEmail());

        response.setTitle(project.getTitle());
        response.setProblem(project.getProblem());
        response.setSolution(project.getSolution());
        response.setTechnology(project.getTechnology());
        response.setFundingRequired(project.getFundingRequired());
        response.setStatus(project.getStatus());
        response.setDescription(project.getDescription());
        response.setCreatedAt(project.getCreatedAt());

        return response;
    }
}