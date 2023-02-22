package com.samsamoo.zzalu.domain.chat.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRoomEnroll {
    private String userName;
    private Long memberId;
    private String roomName;
    private String imagePath;
    private String description;
    private String tags;

}
