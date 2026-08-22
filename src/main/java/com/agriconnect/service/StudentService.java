package com.agriconnect.service;

import com.agriconnect.dto.StudentRequest;
import com.agriconnect.dto.StudentResponse;
import com.agriconnect.entity.Student;
import com.agriconnect.entity.User;
import com.agriconnect.repository.StudentRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(
            StudentRepository studentRepository,
            UserRepository userRepository) {

        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }


    // ========================================
    // CREATE STUDENT PROFILE
    // POST /api/student
    // ========================================

    public StudentResponse createStudentProfile(
            StudentRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        // Check whether profile already exists
        if (studentRepository.existsByUser(user)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student profile already exists"
            );
        }

        Student student = new Student();

        student.setUser(user);
        student.setCollege(request.getCollege());
        student.setDepartment(request.getDepartment());
        student.setYear(request.getYear());
        student.setSkills(request.getSkills());
        student.setResearchInterests(
                request.getResearchInterests()
        );
        student.setAbout(request.getAbout());

        Student savedStudent =
                studentRepository.save(student);

        return convertToResponse(savedStudent);
    }


    // ========================================
    // GET STUDENT PROFILE
    // GET /api/student/profile
    // ========================================

    public StudentResponse getStudentProfile(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        Student student =
                studentRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student profile not found"
                                ));

        return convertToResponse(student);
    }


    // ========================================
    // UPDATE STUDENT PROFILE
    // PUT /api/student/profile
    // ========================================

    public StudentResponse updateStudentProfile(
            StudentRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        Student student =
                studentRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student profile not found"
                                ));

        student.setCollege(request.getCollege());
        student.setDepartment(request.getDepartment());
        student.setYear(request.getYear());
        student.setSkills(request.getSkills());
        student.setResearchInterests(
                request.getResearchInterests()
        );
        student.setAbout(request.getAbout());

        Student updatedStudent =
                studentRepository.save(student);

        return convertToResponse(updatedStudent);
    }


    // ========================================
    // CONVERT ENTITY → RESPONSE
    // ========================================

    private StudentResponse convertToResponse(
            Student student) {

        User user = student.getUser();

        return new StudentResponse(
                student.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                student.getCollege(),
                student.getDepartment(),
                student.getYear(),
                student.getSkills(),
                student.getResearchInterests(),
                student.getAbout()
        );
    }
}