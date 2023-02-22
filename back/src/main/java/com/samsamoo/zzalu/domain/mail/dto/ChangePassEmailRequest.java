package com.samsamoo.zzalu.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassEmailRequest {
    @NotBlank
    @Email
    private String userEmail;
    private String username;
}
