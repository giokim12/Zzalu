package com.samsamoo.zzalu.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateMemberRequest {
    private MultipartFile profileMultipartFile;
    private String nickname;
    private String profileMessage;
}
