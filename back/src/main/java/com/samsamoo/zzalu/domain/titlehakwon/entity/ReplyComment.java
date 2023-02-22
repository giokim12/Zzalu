package com.samsamoo.zzalu.domain.titlehakwon.entity;

import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class ReplyComment {

    /** 대댓글 고유 아이디 **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMENT_ID")
    Long id;

    /** 대댓글 내용 **/
    @Column(nullable = false)
    @Lob
    String content;

    /** 작성자 아이디 **/
    // N:1 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    /**
     * 댓글 아이디
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    //좋아요 개수

    /** 생성 시간 **/

    private String createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;


    /** 수정 여부 **/
    @Column(nullable = false)
    @Builder.Default
    private boolean isUpdated =false;


    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public void upDateContent(String content, boolean isUpdated){
        this.content = content;
        this.isUpdated=isUpdated;
    }

}
