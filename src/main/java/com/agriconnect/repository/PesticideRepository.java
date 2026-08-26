package com.agriconnect.repository;

import com.agriconnect.entity.Pesticide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PesticideRepository
        extends JpaRepository<Pesticide, Long> {


    // ========================================
    // FIND BY EXACT NAME
    // ========================================

    Optional<Pesticide> findByNameIgnoreCase(
            String name
    );


    // ========================================
    // SEARCH BY NAME
    // ========================================

    List<Pesticide> findByNameContainingIgnoreCase(
            String name
    );


    // ========================================
    // SEARCH BY TYPE
    // ========================================

    List<Pesticide> findByTypeIgnoreCase(
            String type
    );
}