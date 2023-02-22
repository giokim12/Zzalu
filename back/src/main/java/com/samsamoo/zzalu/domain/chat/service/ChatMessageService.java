package com.samsamoo.zzalu.domain.chat.service;

import com.samsamoo.zzalu.domain.chat.dto.ChatMessageDto;
import com.samsamoo.zzalu.domain.chat.entity.ChatMessage;
import com.samsamoo.zzalu.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatRepository chatRepository;

    public List<ChatMessage> findAllByRoomIdOrderBySendDateDesc(String roomId){
        List<ChatMessageDto> chatMessageDtoList = new LinkedList<>();
        return chatRepository.findAllByRoomIdOrderBySendDateDesc(roomId);
    }

}
