package com.samsamoo.zzalu.domain.titlehakwon.entity;


import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    /** 댓글 고유 아이디 **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    Long id;


    /** 댓글 내용 **/
    @Column(nullable = false)
    @Lob
    String content;

    /** 작성자 아이디 **/
    // N:1 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    /** 제목학원 고유 아이디 **/
    //N:1 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TITLEHAKWON_ID")
    private TitleHakwon titleHakwon;


    /** 대댓글 목록 **/
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)  //자식도 삭제
    @Builder.Default
    private List<ReplyComment> replyCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    @Builder.Default
    private List<CommentLike> commentLikeList = new ArrayList<>();

    /** 좋아요 개수  **/
    @ColumnDefault("0")
    int likeNum;

    /** 생성 시간 **/

    private String createdDate;

    /** 마지막 수정 시간 **/
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

    public  void plusLikeNum (){
        this.likeNum+=1;
    }

    public  void minusLikeNum (){
        this.likeNum-=1;
    }




}
