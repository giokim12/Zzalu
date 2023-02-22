package com.samsamoo.zzalu.domain.award.entity;


import com.samsamoo.zzalu.domain.titlehakwon.entity.TitleHakwon;
import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardRecord {

    @Id
    @Column(name = "AWARD_RECORD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /** 작성자 아이디 **/
    // N:1 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;



    /** 제목학원 고유 아이디 **/
    //N:1 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TITLEHAKWON_ID")
    private TitleHakwon titleHakwon;


    private int rank;

}
