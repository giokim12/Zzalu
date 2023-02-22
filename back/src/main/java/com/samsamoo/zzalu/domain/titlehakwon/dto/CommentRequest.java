package com.samsamoo.zzalu.domain.titlehakwon.dto;

import lombok.Getter;

@Getter
public class CommentRequest {

    /** 사용자 닉네임 **/
    private String username;
    /** 댓글 내용 **/
    private String content;

    /** 제목 학원 ID **/
    private Long titleHakwonId;

}
