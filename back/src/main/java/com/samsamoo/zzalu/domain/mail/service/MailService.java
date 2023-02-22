package com.samsamoo.zzalu.domain.mail.service;

import com.samsamoo.zzalu.domain.mail.dto.EmailRequest;
import com.samsamoo.zzalu.domain.mail.exception.EmailExistException;
import com.samsamoo.zzalu.domain.mail.utils.MailUtils;
import com.samsamoo.zzalu.domain.mail.dto.EmailResponse;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.exception.MemberNotFoundException;
import com.samsamoo.zzalu.domain.member.exception.NotMatchException;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private int size;

    public void checkUniqueUserEmail(String email) {
        if (memberRepository.existsMemberByUserEmail(email)) {
            throw new EmailExistException();
        };
    }
    //인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    public EmailResponse sendMail(String userEmail) {
        // 이메일 있는지 검사
        checkUniqueUserEmail(userEmail);
        // 메일 실제로 보내는 메서드
        return realEmailSender(userEmail);

    }

    private EmailResponse realEmailSender(String userEmail) {
        // 4자리 난수 인증번호 생성
        String authKey = getKey(4);

        // 인증 메일 보내기
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("[Zzalu] 이메일 인증");
            sendMail.setText(new StringBuffer().append("<h2>[Zzalu]</h2>")
                    .append("<p>아래 인증코드를 앱에 입력해주세요.</p>")
                    .append("<p>인증코드: "+ authKey +"</p>")
                    .toString());
            sendMail.setFrom(username, "관리자");
            sendMail.setTo(userEmail);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new EmailResponse(authKey);
    }

    public EmailResponse sendChangeMail(String userEmail, String username) {
        // 둘이 같은지 확인
        Member memberA = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("해당 아이디의 회원을 찾을 수 없습니다."));

        Member memberB = memberRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new MemberNotFoundException("해당 이메일의 회원을 찾을 수 없습니다."));

        log.info("memberA ={}, memberB = {}", memberA.getId(), memberB.getId());
        if (!memberA.equals(memberB)) {
            throw new NotMatchException("회원 아이디와 이메일이 일치하지 않습니다.");
        }
        // send Email
        return realEmailSender(userEmail);
    }

    public void findUsername(EmailRequest emailRequest) {
        // email로 가입된 회원 있는지 확인 > 회원 가져오기
        String userEmail = emailRequest.getUserEmail();
        Member member = memberRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new MemberNotFoundException("해당 이메일로 가입된 회원이 없습니다."));
        // 회원의 username 반환
        String foundUsername = member.getUsername();

        // 이메일 전송
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("[Zzalu] 이메일 인증");
            sendMail.setText(new StringBuffer().append("<h2>[Zzalu]</h2>")
                    .append("<p>회원 아이디: "+ foundUsername +"</p>")
                    .toString());
            sendMail.setFrom(username, "관리자");
            sendMail.setTo(userEmail);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
