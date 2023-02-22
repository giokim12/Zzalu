package com.samsamoo.zzalu.domain.notice.entity;

import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.notice.enums.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @Column(name = "NOTICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private NoticeType type;
    @NotNull
    @Builder.Default
    private Boolean isChecked = false;
    @NotNull
    private String message;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
