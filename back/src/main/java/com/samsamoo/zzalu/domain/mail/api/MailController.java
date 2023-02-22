package com.samsamoo.zzalu.domain.mail.api;

import com.samsamoo.zzalu.domain.mail.dto.ChangePassEmailRequest;
import com.samsamoo.zzalu.domain.mail.dto.EmailRequest;
import com.samsamoo.zzalu.domain.mail.dto.EmailResponse;
import com.samsamoo.zzalu.domain.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MailController {
    private final MailService mailService;

    //--------------------------------------회원가입 이메일 인증-------------------------------------------
    @PostMapping("/mail/signup")
    public ResponseEntity<EmailResponse> sendSignupEmail(@RequestBody @Valid EmailRequest emailRequest) {
        EmailResponse emailResponse = mailService.sendMail(emailRequest.getUserEmail());
        return ResponseEntity.ok().body(emailResponse);
    }

    //--------------------------------------비번 변경 이메일 인증-------------------------------------------
    @PostMapping("/mail/pass")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody @Valid ChangePassEmailRequest request) {

        EmailResponse emailResponse = mailService.sendChangeMail(request.getUserEmail(), request.getUsername());
        return ResponseEntity.ok().body(emailResponse);
    }
    //--------------------------------------아이디 찾기(아이디 전송)-----------------------------------------
    @PostMapping("/mail/username")
    public ResponseEntity<Map<String, String>> findUsername(@RequestBody @Valid EmailRequest emailRequest) {

        mailService.findUsername(emailRequest);
        return ResponseEntity.ok().body(Map.of("message", "success"));
    }

}
