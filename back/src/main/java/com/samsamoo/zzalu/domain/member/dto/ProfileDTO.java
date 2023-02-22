package com.samsamoo.zzalu.domain.member.dto;

import com.samsamoo.zzalu.domain.award.dto.AwardCountResponse;
import com.samsamoo.zzalu.domain.board.dto.MembersBoardList;
import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileDTO {

    private Long id;
    private String username;
    private String nickname;
    private Boolean isManager;
    private String userEmail;
    private LocalDate enrollDate;
    private String profileMessage;
    private String profilePath;
    private int followingCnt;
    private int followerCnt;
    private MembersBoardList boardList;

    private AwardCountResponse awardCount;

    public ProfileDTO(Member member, MembersBoardList membersBoardList , AwardCountResponse awardCountResponse) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.isManager = member.getRoles().contains("MANAGER");
        this.userEmail = member.getUserEmail();
        this.nickname = member.getNickname();
        this.enrollDate = LocalDate.from(member.getEnrollDate());
        this.profileMessage = member.getProfileMessage();
        this.profilePath = member.getProfilePath();
        this.followingCnt = member.getFollowing().size();
        this.followerCnt = member.getFollower().size();
        //수상 기록 넣어주기
        this.awardCount = awardCountResponse;
        // 보드 리스트 넣어주기
        this.boardList = membersBoardList;
    }
}
