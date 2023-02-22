package com.samsamoo.zzalu.domain.chat.repository;

import com.samsamoo.zzalu.domain.chat.dto.ChatRoomDto;
import com.samsamoo.zzalu.domain.chat.dto.ChatRoomEnroll;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRedisRepository {
    private Map<String, ChatRoomDto> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoomDto findRoomById(String Id) {
        return chatRoomMap.get(Id);
    }

    public ChatRoomDto createChatRoom(ChatRoomEnroll chatRoomEnroll) {
        ChatRoomDto chatRoomDto = ChatRoomDto.create(chatRoomEnroll);
        chatRoomMap.put(chatRoomDto.getRoomId(), chatRoomDto);
        return chatRoomDto;
    }
}
