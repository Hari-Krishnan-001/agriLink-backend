package com.agriconnect.repository;

import com.agriconnect.entity.Student;
import com.agriconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);

    boolean existsByUser(User user);
}