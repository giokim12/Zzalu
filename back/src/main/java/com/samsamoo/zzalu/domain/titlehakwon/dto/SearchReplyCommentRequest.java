package com.samsamoo.zzalu.domain.titlehakwon.dto;


import lombok.Getter;

@Getter
public class SearchReplyCommentRequest {


    /** 부모 댓글 ID **/
    private Long parentCommentId;

    /** 마지막으로 출력한 댓글 ID **/
    private Long lastCommentId;

    /** 몇개의 댓글을 읽어올건지 **/
    private int size;



}
