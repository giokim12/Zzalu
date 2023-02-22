package com.samsamoo.zzalu.domain.member.service;

import com.samsamoo.zzalu.domain.award.RankDtoInterface;
import com.samsamoo.zzalu.domain.award.dto.AwardCountResponse;
import com.samsamoo.zzalu.domain.award.dto.AwardResponse;
import com.samsamoo.zzalu.domain.award.entity.AwardRecord;
import com.samsamoo.zzalu.domain.award.repository.AwardRecordRepository;
import com.samsamoo.zzalu.global.advice.BadRequestException;
import com.samsamoo.zzalu.infra.amazonS3.upLoader.S3Uploader;
import com.samsamoo.zzalu.global.auth.dto.TokenInfo;
import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.board.dto.MembersBoardInfo;
import com.samsamoo.zzalu.domain.board.dto.MembersBoardList;
import com.samsamoo.zzalu.domain.board.entity.Board;
import com.samsamoo.zzalu.domain.mail.service.MailService;
import com.samsamoo.zzalu.domain.member.dto.*;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.exception.*;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import com.samsamoo.zzalu.domain.tempGif.entity.TempGif;
import com.samsamoo.zzalu.domain.tempGif.repository.TempGifRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    private final S3Uploader s3Uploader;
    private final AwardRecordRepository awardRecordRepository;
    private final TempGifRepository tempGifRepository;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        // 아이디 중복
        if (!checkUniqueUsername(signupRequest.getUsername())) {
            throw new NotMatchException("이미 존재하는 아이디입니다.");
        }
        // 닉네임 중복
        if (!checkUniqueNickname(signupRequest.getNickname())) {
            throw new NotMatchException("이미 존재하는 닉네임입니다.");
        }
        validatePassword(signupRequest.getPassword(),signupRequest.getPasswordConfirmation()); // 비번, 비번확인 일치 여부
        mailService.checkUniqueUserEmail(signupRequest.getUserEmail()); // 이메일 중복 > 바로 예외 발생

        // 비밀번호 인코딩
        String rawPassword = signupRequest.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        signupRequest.setPassword(encPassword);
        memberRepository.save(signupRequest.toEntity());

    }

    // 수정
    private void validatePassword(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new PasswordConfirmationException();
        }
    }
    public Boolean checkUniqueUsername(String username) {
        return !memberRepository.existsMemberByUsername(username);
    }

    public Boolean checkUniqueNickname(String nickname) {
        return !memberRepository.existsMemberByNickname(nickname);
    }

    // 토큰 검증 실패 시 에외 발생
    public void checkToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

    public TokenInfo login(String username, String password) {
        // username 없을 때 리턴
        Member selectedMember = getMemberbyUsername(username);

        checkPasswordMatch(selectedMember, password);

        // exception 안 났으면 토큰 발행
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;

    }

    private void checkPasswordMatch(Member member, String password) {
        // password 틀림
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 다릅니다.");
        }
    }

    private Member getMemberbyUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }


    public MembersBoardList getMembersBoard(String username) {
        // user 반환
        Member member = getMemberbyUsername(username);

        // user의 board 불러오기
        List<Board> boards = member.getBoards();

        // 새 리스트 만들기
        List<MembersBoardInfo> membersBoardInfos = new ArrayList<>();

        // for문 돌면서 info dto에 add
        for(Board board : boards) {
            String thumbnailPath = null;
            if (board.getGifs().size() >= 1) {
                thumbnailPath = board.getGifs().get(0).getGifPath();
            }
            MembersBoardInfo boardInfo = new MembersBoardInfo(board.getId(), board.getBoardName(), thumbnailPath);
            membersBoardInfos.add(boardInfo);
        }
        // 생성된 리스트를 list dto의 생성자로 넘김
        return new MembersBoardList(membersBoardInfos);

    }

    // ---------------------사용자 제목학원 수상 기록 개수 반환 ---------------------------------
    public AwardCountResponse getAwardCountRecord (String username){

        Member member = getMemberbyUsername(username);

        RankDtoInterface rankDtoInterface = awardRecordRepository.getAwardRecordByMemberId(member.getId());

        AwardCountResponse awardCount = new AwardCountResponse(rankDtoInterface.get1st(),rankDtoInterface.get2nd(),rankDtoInterface.get3rd());

        return awardCount;

    }

    //---------------------사용자 제목학원 수상 기록 리스트 반환 --------------------------------

    public List<AwardResponse> getAwardRecordList (String username, String sort){

        List<AwardRecord> awardRecordList = new ArrayList<>();
        Member member = getMemberbyUsername(username);
        //최신순 일경우
        if(sort.equals("LATEST")){
            awardRecordList = awardRecordRepository.findByMember_IdOrderByIdDesc(member.getId());
            return AwardResponse.converToDtoList(awardRecordList);
        }else{
            //과거순 일 경우
            awardRecordList = awardRecordRepository.findByMember_IdOrderById(member.getId());
            return AwardResponse.converToDtoList(awardRecordList);
        }


    }

// ---------------------사용자 프로필 반환---------------------------------
    public ProfileDTO getProfile(String username) {
        Member member = getMemberbyUsername(username);

        MembersBoardList membersBoardList = getMembersBoard(username);
        AwardCountResponse awardCount = getAwardCountRecord(username);
        ProfileDTO profile = new ProfileDTO(member, membersBoardList ,awardCount);
        return profile;
    }

    public UpdateMember updateMember(String token, UpdateMemberRequest request) throws IOException {
        // 토큰 인증
        checkToken(token);
        // 닉네임 중복 체크??
        if (!checkUniqueNickname(request.getNickname())) {
            throw new NotMatchException("닉네임이 중복입니다.");
        }
        UpdateMember updateMember = new UpdateMember();
        // s3로 프로필 이미지 업로드 및 반환
        if (request.getProfileMultipartFile() != null) {
            String returnUrl =  s3Uploader.upload(request.getProfileMultipartFile(), "MemberProfile");
            updateMember.setProfilePath(returnUrl);
        }
        if (request.getNickname() != null) {
            updateMember.setNickname(request.getNickname());
        }
        if (request.getProfileMessage() != null) {
            updateMember.setProfileMessage(request.getProfileMessage());
        }
        // 토큰에서 Member 반환
        Member me = jwtTokenProvider.getMember(token);
        // Member 수정
        me.update(updateMember);
        // Member 저장
        memberRepository.save(me);
        return updateMember;
    }

    @Transactional
    public void deleteMember(String token, String rawPassword) {
        checkToken(token);
        Member me = jwtTokenProvider.getMember(token);

        checkPasswordMatch(me, rawPassword);

        // 나의 팔로잉 리스트의 유저(you)들의 follower에서 나를 삭제
        List<Member> followings = me.getFollowing();
        Iterator<Member> followingItr = followings.iterator();
        while (followingItr.hasNext()) {
            Member you = followingItr.next();
            // 나를 삭제
            you.getFollower().remove(me);
            memberRepository.save(you);
        }

        // 나의 팔로우 리스트의 유저들의 following에서 나를 삭제
        List<Member> followers = me.getFollower();
        Iterator<Member> followerItr = followers.iterator();
        while (followerItr.hasNext()) {
            Member you = followerItr.next();
            // 나를 삭제
            you.getFollowing().remove(me);
            memberRepository.save(you);
        }
        List<TempGif> tempGifs = tempGifRepository.findAllByAllowedMembersContaining(me);
        for (TempGif tempGif : tempGifs) {
            tempGif.getAllowedMembers().remove(me);
        }
        memberRepository.delete(me);
    }

    public void changePass(ChangePassRequest request) {
        // member 반환
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new MemberNotFoundException());

        // 비밀번호 확인과 일치하는지
        String newPass = request.getNewPassword();
        validatePassword(newPass, request.getNewPasswordConfirmation());

        // 비밀번호 형식이 맞는지 확인
        Pattern passPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$");
        Matcher passMatcher = passPattern.matcher(newPass);
        if(!passMatcher.find()){
            throw new InvalidPasswordException("비밀번호는 영문과 특수문자 숫자를 포함하며 8~20자이어야 합니다.");
        }

        // member의 비밀번호 변경
        member.changePass(passwordEncoder.encode(newPass));

        // member 저장
        memberRepository.save(member);
    }

    public void requestManager(String token) {
        Member member = jwtTokenProvider.getMember(token);
        if(member.getPermittedCount() >= 5) {
            if(!member.getRoles().contains("MANAGER")) {
                member.makeManager();
                memberRepository.save(member);
            } else {
                throw new BadRequestException("이미 매니저 권한이 있습니다.");// 수정 필요
            }
        } else {
            throw new BadRequestException("짤 업로드 및 수정 허가 횟수가 5번 미만입니다.");// 수정 필요
        }
    }
}
