package com.samsamoo.zzalu.domain.titlehakwon.entity;


import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @CreationTimestamp
    private LocalDateTime createdDate;


    @Builder
    public CommentLike(Member member, Comment comment, LocalDateTime createdDate) {
        this.member = member;
        this.comment = comment;
        this.createdDate = createdDate;
    }
}
