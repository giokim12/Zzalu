package com.samsamoo.zzalu.domain.statistics.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberTagStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    private Long memberId;

    @Column(name = "tag")
    private String tag;

    @Column(name = "COUNT")
    @Builder.Default
    private Long count = 0L;
}
