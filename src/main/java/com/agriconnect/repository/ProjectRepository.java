package com.agriconnect.repository;

import com.agriconnect.entity.Project;
import com.agriconnect.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    List<Project> findByStudent(Student student);
    long countByStudent(Student student);
}