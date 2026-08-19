package com.agriconnect.repository;

import com.agriconnect.entity.Crop;
import com.agriconnect.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    List<Crop> findByFarmer(Farmer farmer);

    Optional<Crop> findByIdAndFarmer(Long id, Farmer farmer);

    List<Crop> findByAvailableTrue();
    long countByFarmer(Farmer farmer);

}