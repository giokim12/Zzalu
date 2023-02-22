package com.samsamoo.zzalu.domain.member.service;

import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.member.dto.follow.*;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.exception.NotMatchException;
import com.samsamoo.zzalu.domain.member.exception.InvalidTokenException;
import com.samsamoo.zzalu.domain.member.exception.MemberNotFoundException;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public FollowResponse follow(String token, Long memberId) {
        // 토큰 검증
        checkToken(token);

        // 팔로우 과정
        Member me = jwtTokenProvider.getMember(token);
        Member you = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("팔로우 대상을 찾을 수 없습니다."));

        // 자신 팔로우 막기
        checkSelfFollow(me, you);

        me.followMember(you);
        memberRepository.save(me);
        memberRepository.save(you);

        return new FollowResponse(me.getId(), you.getId());

    }

    public void unfollow(String token, Long memberId) {
        // 토큰 검증
        checkToken(token);

        // 언 팔로우 과정
        Member me = jwtTokenProvider.getMember(token);
        Member you = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("언팔로우 대상을 찾을 수 없습니다."));
        // 자신 언 팔로우 막기
        checkSelfFollow(me, you);

        me.unfollowMember(you);
        memberRepository.save(me);
        memberRepository.save(you);

    }

    // 토큰 검증 실패 시 에외 발생
    private void checkToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

    // 자신을 팔로우, 언팔로우 요청 예외 발생
     private void checkSelfFollow(Member me, Member you) {
         if (me.equals(you)) {
             throw new NotMatchException("자신을 팔로우하거나 언팔로우 할 수 없습니다.");
         }
    }

    public List<Object> getFollowingList(String token, Long memberId) {
        // 토큰 검증
        checkToken(token);

        // token에 저장된 Member > 요청한 사람
        Member me = jwtTokenProvider.getMember(token);

        // 팔로잉이 궁금한 Member
        Member target = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        // 내가 팔로잉하고 있는 맴버들 불러오기
        List<Member> targetFollowings =  target.getFollowing();

        List<Object> followingList = new ArrayList<>();

        for (Iterator<Member> itr = targetFollowings.iterator(); itr.hasNext();) {
            Member entity = itr.next();
            // 내가 팔로우 하고 있는지 여부를 넣어줘야 한다.
            List<Member> followers = entity.getFollower();
            Boolean isFollowing = followers.contains(me);
            // entity를 dto로 변환
            FollowMemberDTO dto = new FollowMemberDTO(entity, isFollowing);
            followingList.add(dto);
        }
        return followingList;
    }

    public List<Object> getFollowerList(String token, Long memberId) {
        // 토큰 검증
        checkToken(token);

        // token에 저장된 Member > 요청한 사람
        Member me = jwtTokenProvider.getMember(token);

        // 팔로워가 궁금한 Member
        Member target = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        // target의 팔로워 맴버들 불러오기
        List<Member> targetFollowers =  target.getFollower();

        List<Object> followerList = new ArrayList<>();

        // 둘이 다를 경우 > FollowMemberDTO 사용
        for (Iterator<Member> itr = targetFollowers.iterator(); itr.hasNext();) {
            Member entity = itr.next();
            // 내가 팔로우 하고 있는지 여부를 넣어줘야 한다.
            List<Member> followers = entity.getFollower();
            Boolean isFollowing = followers.contains(me);
            // entity를 dto로 변환
            FollowMemberDTO dto = new FollowMemberDTO(entity, isFollowing);
            followerList.add(dto);
        }
        return followerList;
    }

    public FollowStateResponse getFollowState(String token, Long memberId) {
        // 토큰 검증
        checkToken(token);

        // 팔로우 상태 확인
        Member me = jwtTokenProvider.getMember(token);
        if (me.getId().equals(memberId)) {
            throw new NotMatchException("자신과의 팔로우 상태는 확인할 수 없습니다.");
        }
        Member you = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        Boolean followState = me.getFollowing().contains(you);
        return new FollowStateResponse(me.getId(), you.getId(), followState);
    }
}
