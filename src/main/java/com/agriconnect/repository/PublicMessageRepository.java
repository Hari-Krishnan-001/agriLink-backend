package com.agriconnect.repository;

import com.agriconnect.entity.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicMessageRepository
        extends JpaRepository<PublicMessage, Long> {

    List<PublicMessage> findAllByOrderBySentAtAsc();
}