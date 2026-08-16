package com.agriconnect.repository;

import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessFirmRepository
        extends JpaRepository<BusinessFirm, Long> {

    Optional<BusinessFirm> findByUser(User user);

    boolean existsByUser(User user);
}