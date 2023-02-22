package com.samsamoo.zzalu.domain.member.api;

import com.samsamoo.zzalu.domain.award.dto.AwardCountResponse;
import com.samsamoo.zzalu.domain.award.dto.AwardResponse;
import com.samsamoo.zzalu.global.auth.dto.LoginRequest;
import com.samsamoo.zzalu.global.auth.dto.TokenInfo;
import com.samsamoo.zzalu.domain.member.dto.*;
import com.samsamoo.zzalu.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    //--------------------------------------회원가입-------------------------------------------
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return ResponseEntity.created(URI.create("/members/" + signupRequest.getUsername())).build();
    }

    //--------------------------------------아이디 중복 체크-------------------------------------------
    @GetMapping(value = "/exists", params = "username")
    public ResponseEntity<UniqueResponse> validateUniqueUsername(@RequestParam String username) {
        // username 유효성 검사.
        Boolean unique = memberService.checkUniqueUsername(username);
        return ResponseEntity.ok().body(new UniqueResponse(unique));
    }

    //--------------------------------------닉네임 중복 체크-------------------------------------------
    @GetMapping(value = "/exists", params = "nickname")
    public ResponseEntity<UniqueResponse> validateUniqueNickname(@RequestParam String nickname) {
        Boolean unique = memberService.checkUniqueNickname(nickname);
        return ResponseEntity.ok().body(new UniqueResponse(unique));
    }

    //--------------------------------------로그인-------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody LoginRequest loginRequest) {
        TokenInfo tokenInfo = memberService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok().body(tokenInfo);
    }

    //--------------------------------------나의 프로필 확인(안씀)------------------------------------
    @GetMapping(value = "/my-profile")
    public ResponseEntity getMyProfile(@RequestHeader(value = "Authorization") String bearerToken) {
        return ResponseEntity.ok().body("OK");
    }

    //--------------------------------------프로필 확인-------------------------------------------
    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@RequestParam String username) {
        ProfileDTO profile = memberService.getProfile(username);
        return ResponseEntity.ok().body(profile);
    }

    //--------------------------------------회원 정보 수정-------------------------------------------
    @PatchMapping
    public ResponseEntity<UpdateMember> updateMember(@RequestHeader(value = "Authorization") String bearerToken, @ModelAttribute UpdateMemberRequest request) throws IOException {
        String token = bearerToken.substring(7);
        UpdateMember response = memberService.updateMember(token, request);
        return ResponseEntity.ok().body(response);
    }
    //--------------------------------------회원 탈퇴-------------------------------------------
    @DeleteMapping
    public ResponseEntity deleteMember(@RequestHeader(value = "Authorization") String bearerToken, @RequestBody PasswordDTO passwordDTO) {
        String token = bearerToken.substring(7);
        memberService.deleteMember(token, passwordDTO.getRawPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //--------------------------------------비밀번호 변경-------------------------------------------
    @PatchMapping("/pass")
    public ResponseEntity changePass(@Valid @RequestBody ChangePassRequest request) {
        memberService.changePass(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //-------------------------------------- 수상 횟수(실제 불리진 않음)-------------------------------------------
    @GetMapping("/awards-count/{username}")
    public ResponseEntity<AwardCountResponse> getAwardCount (@PathVariable String username){
        AwardCountResponse awardCountResponse = memberService.getAwardCountRecord(username);
        return  ResponseEntity.ok().body(awardCountResponse);
    }

    //-------------------------------------- 전체 수상 내역 조회-------------------------------------
    @GetMapping("/awards/{username}")
    public ResponseEntity<List<AwardResponse>> getAwardList (@PathVariable String username , @RequestParam String sort){
       List<AwardResponse> awardResponseList = memberService.getAwardRecordList(username,sort);
        return ResponseEntity.ok().body(awardResponseList);
    }

    //---------------------------------------매니저 권한 요청-----------------------------------------
    @PostMapping("/manager")
    public ResponseEntity requestManager(@RequestHeader(value = "Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        memberService.requestManager(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}