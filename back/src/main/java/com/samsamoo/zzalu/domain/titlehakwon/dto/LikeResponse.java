package com.samsamoo.zzalu.domain.titlehakwon.dto;

import lombok.Getter;


@Getter
public class LikeResponse {

    private Long id ;

    private int likeNum;

    public LikeResponse(Long id, int likeNum) {
        this.id = id;
        this.likeNum = likeNum;
    }
}
