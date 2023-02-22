package com.samsamoo.zzalu.global.auth.sevice;


import com.samsamoo.zzalu.global.auth.dto.TokenInfo;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.exception.MemberNotFoundException;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtTokenProvider {
    // 나중에 key 변경 후, application.yml gitgnore하기

    private final Key key;
    private final MemberRepository memberRepository;
    public JwtTokenProvider(@Value("${jwt.token.secret}") String secretKey, MemberRepository memberRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberRepository = memberRepository;
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

//        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());


        // Access token 생성
        Date accessTokenExpiresIn = new Date(now + 86400000*3); // 유효기간 1일*3
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("username", authentication.getName())
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 방식
                .compact();

        // Refresh token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String username = authentication.getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));
        String nickname = member.getNickname();
        Boolean isManager = member.getRoles().contains("MANAGER");


        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(member.getId())
                .username(username)
                .nickname(nickname)
                .isManager(isManager)
                .build();
    }

    // JWT token 복호화 하여 토큰에 들어있는 정보를 꺼내는 매서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e) {   // Token이 만료된 경우 Exception이 발생한다.
            log.error("Token Expired");

        }catch(JwtException e) {        // Token이 변조된 경우 Exception이 발생한다.
            log.error("Token Error");
        }
        return false;
    }
    public Member getMember(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        String username = String.valueOf(claims.getBody().get("username"));
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("로그인된 사용자를 찾을 수 없습니다."));
    }

    public String getUserNameWithToken (String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        String username = String.valueOf(claims.getBody().get("username"));

        return username;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getToken (String token){
        return  token == null ? null : token.substring(7);
    }


}
