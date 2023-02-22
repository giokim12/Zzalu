package com.samsamoo.zzalu.domain.member.dto.follow;

import lombok.Getter;

@Getter
public class FollowStateResponse {
    private Long myId;
    private Long yourId;
    private Boolean followState;

    public FollowStateResponse(Long myId, Long yourId, Boolean followState) {
        this.myId = myId;
        this.yourId = yourId;
        this.followState = followState;
    }

}
