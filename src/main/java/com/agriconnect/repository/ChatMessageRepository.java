package com.agriconnect.repository;

import com.agriconnect.entity.Chat;
import com.agriconnect.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatOrderBySentAtAsc(Chat chat);
}