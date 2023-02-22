package com.samsamoo.zzalu.domain.member.dto.follow;

import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FollowMemberDTO {
    private Long id;
    private String username;
    private String nickname;
    private String profilePath;
    private Boolean followState;
    public FollowMemberDTO(Member member, Boolean isFollowing) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profilePath = member.getProfilePath();
        this.followState = isFollowing;

    }
}
