package com.agriconnect.controller;

import com.agriconnect.dto.StudentRequest;
import com.agriconnect.dto.StudentResponse;
import com.agriconnect.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create Student Profile
    @PostMapping
    public StudentResponse createStudentProfile(
            @RequestBody StudentRequest request,
            Authentication authentication) {

        return studentService.createStudentProfile(
                request,
                authentication
        );
    }

    // Get Student Profile
    @GetMapping("/profile")
    public StudentResponse getStudentProfile(
            Authentication authentication) {

        return studentService.getStudentProfile(
                authentication
        );
    }
}