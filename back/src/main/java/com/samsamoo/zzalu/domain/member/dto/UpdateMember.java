package com.samsamoo.zzalu.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateMember {
    private String profilePath;
    private String nickname;
    private String profileMessage;
}
