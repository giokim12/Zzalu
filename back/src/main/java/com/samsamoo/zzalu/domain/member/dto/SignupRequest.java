package com.samsamoo.zzalu.domain.member.dto;

import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.*;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9]{2,11}",
            message = "아이디는 영문, 숫자만 가능하며 2 ~ 12자리까지 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자(@$!%*#?&) 포함, 8자 이상 20자 이하여야 합니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호 확인은 영문, 숫자, 특수문자(@$!%*#?&) 포함, 8자 이상 20자 이하여야 합니다.")
    @Size(min = 8, max = 20, message = "비밀번호 확인은 8자 이상 20자 이하여야 합니다.")
    private String passwordConfirmation;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 10, message = "닉네임는 1자 이상 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String userEmail;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .userEmail(userEmail)
                .nickname(nickname)
                .build();
    }
}
