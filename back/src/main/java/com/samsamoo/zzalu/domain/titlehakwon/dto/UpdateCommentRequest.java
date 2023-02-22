package com.samsamoo.zzalu.domain.titlehakwon.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {


    /** 댓글 ID or 대댓글 **/
    private  Long commentId;
    /** 사용자 닉네임 **/
    private String username;
    /** 댓글 내용 **/
    private String content;

    /** 제목 학원 ID **/
    private Long titleHakwonId;
}
