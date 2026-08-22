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


    // ========================================
    // CREATE STUDENT PROFILE
    // POST /api/student
    // ========================================

    @PostMapping
    public StudentResponse createStudentProfile(
            @RequestBody StudentRequest request,
            Authentication authentication) {

        return studentService.createStudentProfile(
                request,
                authentication
        );
    }


    // ========================================
    // GET STUDENT PROFILE
    // GET /api/student/profile
    // ========================================

    @GetMapping("/profile")
    public StudentResponse getStudentProfile(
            Authentication authentication) {

        return studentService.getStudentProfile(
                authentication
        );
    }


    // ========================================
    // UPDATE STUDENT PROFILE
    // PUT /api/student/profile
    // ========================================

    @PutMapping("/profile")
    public StudentResponse updateStudentProfile(
            @RequestBody StudentRequest request,
            Authentication authentication) {

        return studentService.updateStudentProfile(
                request,
                authentication
        );
    }
}