package com.samsamoo.zzalu.domain.chat.dto;

import com.samsamoo.zzalu.domain.chat.entity.ChatRoom;
import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRoomListDto {
    private String roomId;
    private String roomName;
    private String userName;
    private Long memberId;
    private Long likeCount;

    private List<Long> chatMessageId;
    private List<Long> likeMemberId;
    private String imagePath;
    private String description;
    private String tags;
    private LocalDateTime enrollDate;
    private LocalDateTime lastActivation;

    public ChatRoomListDto(ChatRoom chatRoom) {
        roomId = chatRoom.getRoomId();
        roomName = chatRoom.getRoomName();
        userName = chatRoom.getUserName();
        memberId = chatRoom.getMemberId();
        likeCount = chatRoom.getLikeCount();
        likeMemberId = new ArrayList<>();
        for(Member member : chatRoom.getLikeMembers()) {
            likeMemberId.add(member.getId());
        }
        imagePath = chatRoom.getImagePath();
        description = chatRoom.getDescription();
        tags = chatRoom.getTags();
        enrollDate = chatRoom.getEnrollDate();
        lastActivation = chatRoom.getLastActivation();
    }
}
