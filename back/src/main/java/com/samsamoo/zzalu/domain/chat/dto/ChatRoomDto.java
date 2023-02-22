package com.samsamoo.zzalu.domain.chat.dto;

import com.samsamoo.zzalu.domain.chat.entity.ChatRoom;
import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@RedisHash(value="CHAT_ROOM")
public class ChatRoomDto implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
    private String roomId;
    private String roomName;
    private String userName;
    private Long memberId;
    private Long likeCount;

    private List<ChatMessageDto> chatMessageDtos;
    private List<Member> likeMembers;
    private String imagePath;
    private String description;
    private String tags;
    private LocalDateTime enrollDate;
    private LocalDateTime lastActivation;

    public static ChatRoomDto create(ChatRoomEnroll chatRoomEnroll) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.userName = chatRoomEnroll.getUserName();
        chatRoomDto.memberId = chatRoomEnroll.getMemberId();
        chatRoomDto.roomName = chatRoomEnroll.getRoomName();
        chatRoomDto.imagePath = chatRoomEnroll.getImagePath();
        chatRoomDto.description = chatRoomEnroll.getDescription();
        chatRoomDto.tags = chatRoomEnroll.getTags();
        chatRoomDto.chatMessageDtos = new LinkedList<ChatMessageDto>();
        chatRoomDto.likeMembers = new ArrayList<>();
        chatRoomDto.likeCount = 0L;
        chatRoomDto.enrollDate = LocalDateTime.now();
        chatRoomDto.lastActivation = LocalDateTime.now();
        return chatRoomDto;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .userName(userName)
                .memberId(memberId)
                .likeCount(likeCount)
                .likeMembers(likeMembers)
                .imagePath(imagePath)
                .description(description)
                .tags(tags)
                .enrollDate(enrollDate)
                .lastActivation(lastActivation)
                .build();
    }
}
