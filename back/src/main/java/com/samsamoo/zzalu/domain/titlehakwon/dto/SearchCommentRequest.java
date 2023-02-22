package com.samsamoo.zzalu.domain.titlehakwon.dto;

import lombok.Getter;

@Getter
public class SearchCommentRequest {


    /** 마지막으로 출력한 댓글 ID **/
    private Long lastCommentId;

    /** 댓글학원 ID **/
    private Long titleHakwonId;

    /** 몇개의 댓글을 읽어올건지 **/
    private int size;

    /** 현재 로그인 되어있는 사용자 아이디 **/
    private String username;


}
