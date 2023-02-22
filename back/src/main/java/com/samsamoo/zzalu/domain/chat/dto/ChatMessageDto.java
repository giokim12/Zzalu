package com.samsamoo.zzalu.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.samsamoo.zzalu.domain.chat.entity.ChatMessage;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessageDto implements Serializable {
    public enum MessageType {
        ENTER, JOIN, TALK, @JsonEnumDefaultValue UNKNOWN;
    }

    private MessageType type;
    private String roomId;
    private Long memberId;
    private String memberName;
    private Long gifId;
    private String sender;
    private String message;
    private String profilePath;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sendDate;
    private String accessToken;

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .gifId(gifId)
                .type(type)
                .roomId(roomId)
                .sender(sender)
                .message(message)
                .sendDate(sendDate)
                .memberId(memberId)
                .build();
    }
}
