package com.agriconnect.controller;

import com.agriconnect.entity.Pesticide;
import com.agriconnect.service.PesticideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pesticides")
public class PesticideController {

    private final PesticideService pesticideService;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PesticideController(
            PesticideService pesticideService
    ) {

        this.pesticideService =
                pesticideService;
    }


    // ========================================
    // GET ALL PESTICIDES
    // GET /api/pesticides
    // ========================================

    @GetMapping
    public List<Pesticide> getAllPesticides() {

        return pesticideService
                .getAllPesticides();
    }


    // ========================================
    // SEARCH PESTICIDES
    // GET /api/pesticides/search?name=confidor
    // ========================================

    @GetMapping("/search")
    public List<Pesticide> searchPesticides(
            @RequestParam String name
    ) {

        return pesticideService
                .searchPesticides(name);
    }


    // ========================================
    // FIND EXACT NAME
    // GET /api/pesticides/name/{name}
    // ========================================

    @GetMapping("/name/{name}")
    public Pesticide getPesticideByName(
            @PathVariable String name
    ) {

        return pesticideService
                .getPesticideByName(name);
    }


    // ========================================
    // GET BY TYPE
    // GET /api/pesticides/type/{type}
    // ========================================

    @GetMapping("/type/{type}")
    public List<Pesticide> getPesticidesByType(
            @PathVariable String type
    ) {

        return pesticideService
                .getPesticidesByType(type);
    }


    // ========================================
    // GET BY ID
    // GET /api/pesticides/{id}
    // ========================================

    @GetMapping("/{id}")
    public Pesticide getPesticideById(
            @PathVariable Long id
    ) {

        return pesticideService
                .getPesticideById(id);
    }
}