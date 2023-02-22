package com.samsamoo.zzalu.domain.chat.repository;

import com.samsamoo.zzalu.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomIdOrderBySendDateDesc(String roomId);
    List<ChatMessage> findTop200ByOrderBySendDateDesc();
}
