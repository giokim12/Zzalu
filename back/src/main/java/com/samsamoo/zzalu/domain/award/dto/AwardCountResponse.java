package com.samsamoo.zzalu.domain.award.dto;

import lombok.Getter;

@Getter
public class AwardCountResponse {

    int count1st;
    int count2nd;
    int count3nd;


    public AwardCountResponse(int count1st, int count2nd, int count3nd) {
        this.count1st = count1st;
        this.count2nd = count2nd;
        this.count3nd = count3nd;
    }
}
