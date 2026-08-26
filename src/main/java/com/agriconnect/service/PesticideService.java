package com.agriconnect.service;

import com.agriconnect.entity.Pesticide;
import com.agriconnect.repository.PesticideRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PesticideService {

    private final PesticideRepository pesticideRepository;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PesticideService(
            PesticideRepository pesticideRepository
    ) {

        this.pesticideRepository =
                pesticideRepository;
    }


    // ========================================
    // GET ALL
    // ========================================

    public List<Pesticide> getAllPesticides() {

        return pesticideRepository.findAll();
    }


    // ========================================
    // GET BY ID
    // ========================================

    public Pesticide getPesticideById(
            Long id
    ) {

        return pesticideRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pesticide not found"
                        )
                );
    }


    // ========================================
    // SEARCH
    // ========================================

    public List<Pesticide> searchPesticides(
            String name
    ) {

        if (
                name == null ||
                        name.trim().isEmpty()
        ) {

            return pesticideRepository.findAll();
        }


        return pesticideRepository
                .findByNameContainingIgnoreCase(
                        name.trim()
                );
    }


    // ========================================
    // FIND EXACT NAME
    // ========================================

    public Pesticide getPesticideByName(
            String name
    ) {

        return pesticideRepository
                .findByNameIgnoreCase(name.trim())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pesticide not found"
                        )
                );
    }


    // ========================================
    // GET BY TYPE
    // ========================================

    public List<Pesticide> getPesticidesByType(
            String type
    ) {

        return pesticideRepository
                .findByTypeIgnoreCase(
                        type.trim()
                );
    }
}