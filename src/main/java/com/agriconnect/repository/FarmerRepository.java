package com.agriconnect.repository;

import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    Optional<Farmer> findByUser(User user);

    boolean existsByUser(User user);
}