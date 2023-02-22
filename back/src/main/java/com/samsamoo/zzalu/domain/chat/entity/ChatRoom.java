package com.samsamoo.zzalu.domain.chat.entity;

import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_ID")
    private Long id;

    @Column(name = "ROOM_ID")
    private String roomId;

    @Column(name = "ROOM_NAME")
    private String roomName;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "LIKE_COUNT")
    private Long likeCount;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
    @Builder.Default
    private List<ChatMessage> chatMessages  = new LinkedList<>();


    // Member 다대다 적용
    @ManyToMany(mappedBy = "likeChatRooms")
    @Builder.Default
    private List<Member> likeMembers = new ArrayList<>();

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TAGS")
    private String tags;

    @Column(name = "ENROLL_DATE")
    private LocalDateTime enrollDate;

    @Column(name = "LAST_ACTIVATION")
    private LocalDateTime lastActivation;

    public void addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
        if(chatMessage.getChatRoom() != this) {
            chatMessage.setChatRoom(this);
        }
    }

    public void deleteLikeMember(Member member) {
        this.likeMembers.remove(member);
        if(member.getLikeChatRooms().contains(this)) {
            member.deleteLikeChatRoom(this);
        }
    }

    public void addLikeMembers(Member member) {
        likeMembers.add(member);
    }

}
