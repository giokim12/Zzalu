package com.samsamoo.zzalu.domain.chat.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.samsamoo.zzalu.domain.chat.dto.ChatMessageDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_MESSAGE_ID")
    private Long id;

    @Column(name = "GIF_ID")
    private Long gifId;

    @Column(name = "TYPE")
    private ChatMessageDto.MessageType type;

    @Column(name = "ROOM_ID")
    private String roomId;

    @Column(name = "SENDER")
    private String sender;

    @Column(name = "MESSAGE")
    private String message;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "SEND_DATE")
    private LocalDateTime sendDate;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    public void setChatRoom(ChatRoom chatRoom){
        if(this.chatRoom != null) {
            this.chatRoom.getChatMessages().remove(this);
        }
        this.chatRoom = chatRoom;
        if(!chatRoom.getChatMessages().contains(this)) {
            chatRoom.getChatMessages().add(this);
        }
    }

}
