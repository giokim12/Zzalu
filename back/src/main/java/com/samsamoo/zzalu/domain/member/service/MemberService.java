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
        // ????????? ??????
        if (!checkUniqueUsername(signupRequest.getUsername())) {
            throw new NotMatchException("?????? ???????????? ??????????????????.");
        }
        // ????????? ??????
        if (!checkUniqueNickname(signupRequest.getNickname())) {
            throw new NotMatchException("?????? ???????????? ??????????????????.");
        }
        validatePassword(signupRequest.getPassword(),signupRequest.getPasswordConfirmation()); // ??????, ???????????? ?????? ??????
        mailService.checkUniqueUserEmail(signupRequest.getUserEmail()); // ????????? ?????? > ?????? ?????? ??????

        // ???????????? ?????????
        String rawPassword = signupRequest.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        signupRequest.setPassword(encPassword);
        memberRepository.save(signupRequest.toEntity());

    }

    // ??????
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

    // ?????? ?????? ?????? ??? ?????? ??????
    public void checkToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

    public TokenInfo login(String username, String password) {
        // username ?????? ??? ??????
        Member selectedMember = getMemberbyUsername(username);

        checkPasswordMatch(selectedMember, password);

        // exception ??? ????????? ?????? ??????
        // 1. Login ID/PW ??? ???????????? Authentication ?????? ??????
        // ?????? authentication ??? ?????? ????????? ???????????? authenticated ?????? false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. ?????? ?????? (????????? ???????????? ??????)??? ??????????????? ??????
        // authenticate ???????????? ????????? ??? CustomUserDetailsService ?????? ?????? loadUserByUsername ???????????? ??????
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. ?????? ????????? ???????????? JWT ?????? ??????
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;

    }

    private void checkPasswordMatch(Member member, String password) {
        // password ??????
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidPasswordException("??????????????? ????????????.");
        }
    }

    private Member getMemberbyUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }


    public MembersBoardList getMembersBoard(String username) {
        // user ??????
        Member member = getMemberbyUsername(username);

        // user??? board ????????????
        List<Board> boards = member.getBoards();

        // ??? ????????? ?????????
        List<MembersBoardInfo> membersBoardInfos = new ArrayList<>();

        // for??? ????????? info dto??? add
        for(Board board : boards) {
            String thumbnailPath = null;
            if (board.getGifs().size() >= 1) {
                thumbnailPath = board.getGifs().get(0).getGifPath();
            }
            MembersBoardInfo boardInfo = new MembersBoardInfo(board.getId(), board.getBoardName(), thumbnailPath);
            membersBoardInfos.add(boardInfo);
        }
        // ????????? ???????????? list dto??? ???????????? ??????
        return new MembersBoardList(membersBoardInfos);

    }

    // ---------------------????????? ???????????? ?????? ?????? ?????? ?????? ---------------------------------
    public AwardCountResponse getAwardCountRecord (String username){

        Member member = getMemberbyUsername(username);

        RankDtoInterface rankDtoInterface = awardRecordRepository.getAwardRecordByMemberId(member.getId());

        AwardCountResponse awardCount = new AwardCountResponse(rankDtoInterface.get1st(),rankDtoInterface.get2nd(),rankDtoInterface.get3rd());

        return awardCount;

    }

    //---------------------????????? ???????????? ?????? ?????? ????????? ?????? --------------------------------

    public List<AwardResponse> getAwardRecordList (String username, String sort){

        List<AwardRecord> awardRecordList = new ArrayList<>();
        Member member = getMemberbyUsername(username);
        //????????? ?????????
        if(sort.equals("LATEST")){
            awardRecordList = awardRecordRepository.findByMember_IdOrderByIdDesc(member.getId());
            return AwardResponse.converToDtoList(awardRecordList);
        }else{
            //????????? ??? ??????
            awardRecordList = awardRecordRepository.findByMember_IdOrderById(member.getId());
            return AwardResponse.converToDtoList(awardRecordList);
        }


    }

// ---------------------????????? ????????? ??????---------------------------------
    public ProfileDTO getProfile(String username) {
        Member member = getMemberbyUsername(username);

        MembersBoardList membersBoardList = getMembersBoard(username);
        AwardCountResponse awardCount = getAwardCountRecord(username);
        ProfileDTO profile = new ProfileDTO(member, membersBoardList ,awardCount);
        return profile;
    }

    public UpdateMember updateMember(String token, UpdateMemberRequest request) throws IOException {
        // ?????? ??????
        checkToken(token);
        // ????????? ?????? ????????
        if (!checkUniqueNickname(request.getNickname())) {
            throw new NotMatchException("???????????? ???????????????.");
        }
        UpdateMember updateMember = new UpdateMember();
        // s3??? ????????? ????????? ????????? ??? ??????
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
        // ???????????? Member ??????
        Member me = jwtTokenProvider.getMember(token);
        // Member ??????
        me.update(updateMember);
        // Member ??????
        memberRepository.save(me);
        return updateMember;
    }

    @Transactional
    public void deleteMember(String token, String rawPassword) {
        checkToken(token);
        Member me = jwtTokenProvider.getMember(token);

        checkPasswordMatch(me, rawPassword);

        // ?????? ????????? ???????????? ??????(you)?????? follower?????? ?????? ??????
        List<Member> followings = me.getFollowing();
        Iterator<Member> followingItr = followings.iterator();
        while (followingItr.hasNext()) {
            Member you = followingItr.next();
            // ?????? ??????
            you.getFollower().remove(me);
            memberRepository.save(you);
        }

        // ?????? ????????? ???????????? ???????????? following?????? ?????? ??????
        List<Member> followers = me.getFollower();
        Iterator<Member> followerItr = followers.iterator();
        while (followerItr.hasNext()) {
            Member you = followerItr.next();
            // ?????? ??????
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
        // member ??????
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new MemberNotFoundException());

        // ???????????? ????????? ???????????????
        String newPass = request.getNewPassword();
        validatePassword(newPass, request.getNewPasswordConfirmation());

        // ???????????? ????????? ????????? ??????
        Pattern passPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$");
        Matcher passMatcher = passPattern.matcher(newPass);
        if(!passMatcher.find()){
            throw new InvalidPasswordException("??????????????? ????????? ???????????? ????????? ???????????? 8~20???????????? ?????????.");
        }

        // member??? ???????????? ??????
        member.changePass(passwordEncoder.encode(newPass));

        // member ??????
        memberRepository.save(member);
    }

    public void requestManager(String token) {
        Member member = jwtTokenProvider.getMember(token);
        if(member.getPermittedCount() >= 5) {
            if(!member.getRoles().contains("MANAGER")) {
                member.makeManager();
                memberRepository.save(member);
            } else {
                throw new BadRequestException("?????? ????????? ????????? ????????????.");// ?????? ??????
            }
        } else {
            throw new BadRequestException("??? ????????? ??? ?????? ?????? ????????? 5??? ???????????????.");// ?????? ??????
        }
    }
}
