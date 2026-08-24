package com.agriconnect.repository;

import com.agriconnect.entity.Chat;
import com.agriconnect.entity.CropOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByOrder(CropOrder order);

    boolean existsByOrder(CropOrder order);
}