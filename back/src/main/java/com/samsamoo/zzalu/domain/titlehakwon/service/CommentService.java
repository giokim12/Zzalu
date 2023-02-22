package com.samsamoo.zzalu.domain.titlehakwon.service;


import com.samsamoo.zzalu.domain.titlehakwon.dto.*;
import com.samsamoo.zzalu.domain.titlehakwon.entity.Comment;
import com.samsamoo.zzalu.domain.titlehakwon.entity.CommentLike;
import com.samsamoo.zzalu.domain.titlehakwon.entity.ReplyComment;
import com.samsamoo.zzalu.domain.titlehakwon.entity.TitleHakwon;
import com.samsamoo.zzalu.domain.titlehakwon.enums.TitleHakwonState;
import com.samsamoo.zzalu.domain.titlehakwon.exception.CommentNotFoundException;
import com.samsamoo.zzalu.domain.titlehakwon.exception.TitleHakwonException;
import com.samsamoo.zzalu.domain.titlehakwon.repository.CommentLikeRepository;
import com.samsamoo.zzalu.domain.titlehakwon.repository.CommentRepository;
import com.samsamoo.zzalu.domain.titlehakwon.repository.ReplyCommentRepository;
import com.samsamoo.zzalu.domain.titlehakwon.repository.TitleHackwonRepository;
import com.samsamoo.zzalu.global.advice.BadRequestException;
import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;
    private final MemberRepository memberRepository;
    private final TitleHackwonRepository titleHackwonRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 댓글 작성
     */

    public CommentResponse addComment (String token ,CommentRequest requestComment){

        Member member = jwtTokenProvider.getMember(token);
        //없는 제목학원에 댓글을 게시하려 한 경우 예외처리
        TitleHakwon titleHakwon = titleHackwonRepository.findTitleHakwonById(requestComment.getTitleHakwonId()).orElseThrow(()-> new TitleHakwonException());
        //이미 끝났거나 열리지 않은 제목학원에 대하여 댓글을 게시하려 한 경우 예외처리
        if(titleHakwon.getState()== TitleHakwonState.DONE || titleHakwon.getState() ==TitleHakwonState.NOT_OPEN){
            throw new BadRequestException("[ERROR] 기한이 끝나거나 열리지 않은 제목학원에는 댓글을 달 수 없습니다. 제목학원의 회차를 확인해 주세요");
        }

        Comment comment = Comment.builder()
                .member(member)
                .titleHakwon(titleHakwon)
                .content(requestComment.getContent())
                .replyCommentList(new ArrayList<>())
                .build();

        commentRepository.save(comment);

        return new CommentResponse(comment);


    }

    /**
     * 대댓글 작성
     */
    public ReplyCommentResponse addReplyComment (String token ,ReplyCommentRequest replyCommentRequest){



        Member member = jwtTokenProvider.getMember(token);
        Comment comment =  commentRepository.findById(replyCommentRequest.getParentCommentId()).orElseThrow(()->new CommentNotFoundException("[ERROR] 대댓글을 달 댓글이 존재하지 않습니다."));

        ReplyComment replyComment = ReplyComment.builder()
                .member(member)
                .content(replyCommentRequest.getContent())
                .parentComment(comment)
                .build();

      replyCommentRepository.save(replyComment);

        return new ReplyCommentResponse(replyComment);
    }

    /**
     * 댓글 최신순 조회하기
     * 무한 스크롤 / 커서 기반 페이지 네이션
     */

    public List<CommentResponse> getRecentCommentList (Long titleHakwonId ,  Long lastCommentId , int limit  ,String token ){


        Page<Comment> comments = fetchRecentCommentPages(lastCommentId ,titleHakwonId,limit);

        if(token== null || !jwtTokenProvider.validateToken(token)){
            return getCommentList(comments.getContent(),null);
        }else{
            String username = jwtTokenProvider.getUserNameWithToken(token);
               if(memberRepository.findByUsername(username).isPresent()){
                   return getCommentList(comments.getContent(),username);
               }
            return getCommentList(comments.getContent(),null);

            }

        }

    private Page<Comment> fetchRecentCommentPages(Long lastCommentId, Long titleHakwonId ,int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return commentRepository.findByIdLessThanAndTitleHakwonIdOrderByIdDesc(lastCommentId,titleHakwonId , pageRequest);
    }

    /**
     * 댓글 과거순 조회하기
     */
    public List<CommentResponse> getPastCommentList (Long titleHakwonId ,  Long lastCommentId , int limit  ,String token){


        Page<Comment> comments = fetchPastCommentPages(lastCommentId ,titleHakwonId,limit);

        if(token== null || !jwtTokenProvider.validateToken(token)){
            return getCommentList(comments.getContent(),null);
        }else{
            String username = jwtTokenProvider.getUserNameWithToken(token);
            if(memberRepository.findByUsername(username).isPresent()){
                return getCommentList(comments.getContent(),username);
            }
            return getCommentList(comments.getContent(),null);

        }

    }


    private Page<Comment> fetchPastCommentPages(Long lastCommentId, Long titleHakwonId ,int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return commentRepository.findByIdGreaterThanAndTitleHakwonId(lastCommentId,titleHakwonId , pageRequest);
    }


    public List<CommentResponse> getCommentList (List<Comment>commentList ,String username){

        if(username==null){
            return CommentResponse.convertCommentToDtoList(commentList);
        }else{
            //사용자가 로그인이 되어있는 경우 좋아요를 눌렀던 기록을 불러온다.
            List<CommentResponse> commentResponseList = new ArrayList<>();

            for(Comment comment : commentList){
                CommentResponse  commentResponse = new CommentResponse(comment);

                //좋아요 누른 기록이 존재한다면
                if(existCommentLikeWithUserName(comment.getId(),username)){
                    //좋아요 누른 기록으로 보낸다.
                    commentResponse.updateIsPressed();
                }

                commentResponseList.add(commentResponse);
            }

            return commentResponseList;
        }
    }

    /**
     * 대댓글 최신순 가져오기
     * 커서 기반 페이지 네이션
     */

    public List<ReplyCommentResponse> getRecentReplyCommentList (Long lastCommentId , Long parentId , int size){

        Page<ReplyComment> replyComments = fetchRecentReplyCommentPages(lastCommentId,parentId,size);

        return ReplyCommentResponse.convertReplyCommentToDtoList(replyComments.getContent());
    }

    public Page<ReplyComment> fetchRecentReplyCommentPages (Long lastReplyCommentId,Long parentCommentId, int size){
        PageRequest pageRequest = PageRequest.of(0, size);
        return  replyCommentRepository.findByIdLessThanAndParentCommentIdOrderByIdDesc(lastReplyCommentId,parentCommentId,pageRequest);
    }
    /**
     * 대댓글 과거순 가져오기
     * 커서 기반 페이지 네이션
     */

    public List<ReplyCommentResponse> getPastReplyCommentList (Long lastCommentId , Long parentId , int size ){

        Page<ReplyComment> replyComments = fetchPastReplyCommentPages(lastCommentId,parentId,size);

        return ReplyCommentResponse.convertReplyCommentToDtoList(replyComments.getContent());
    }


    public Page<ReplyComment> fetchPastReplyCommentPages (Long lastReplyCommentId,Long parentCommentId, int size){
        PageRequest pageRequest = PageRequest.of(0, size);
        return  replyCommentRepository.findByIdGreaterThanAndParentCommentId(lastReplyCommentId,parentCommentId,pageRequest);
    }

    /**
     * 댓글 수정
     */
    public void updateComment (UpdateCommentRequest commentRequest){

       Optional<Comment> comment = commentRepository.findById(commentRequest.getCommentId());

        if(comment!=null){
            if(!StringUtils.isEmpty(commentRequest.getContent())) {
                comment.get().upDateContent(commentRequest.getContent(), true);
            }
            commentRepository.save(comment.get());
        }

    }


    /**
     * 대댓글 수정
     */

  public void updateReplyComment (UpdateCommentRequest ur){

      ReplyComment replyComment = replyCommentRepository.findById(ur.getCommentId()).orElseThrow(()->new CommentNotFoundException("[ERROR] 삭제하려는 대댓글이 존재하지 않습니다."));

      if(replyComment!=null){
          if(!StringUtils.isEmpty(ur.getContent())){
              replyComment.upDateContent(ur.getContent(),true);
          }
          replyCommentRepository.save(replyComment);
      }
  }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void  deleteComment (Long commentId ,String token){
        Member member = jwtTokenProvider.getMember(token);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());

        if(!member.getUsername().equals(comment.getMember().getUsername())){
            throw new BadRequestException("[ERROR] 삭제하려는 회원정보와 댓글 작성자가 일치하지 않습니다.");
        }
         commentRepository.deleteById(commentId);


    }

    /**
     * 대댓글 삭제
     */
    @Transactional
    public void  deleteReplyComment(Long replyCommentId , String token){

        Member member = jwtTokenProvider.getMember(token);
        ReplyComment replyComment =  replyCommentRepository.findById(replyCommentId).orElseThrow(()-> new CommentNotFoundException("[ERROR] 삭제하려는 대댓글이 존재하지 않습니다."));

        if(!member.getUsername().equals(replyComment.getMember().getUsername())){
            throw new BadRequestException("[ERROR] 삭제하려는 회원정보와 대댓글 작성자가 일치하지 않습니다.");
        }

        replyCommentRepository.deleteById(replyCommentId);

    }

    /**
     * 댓글에 좋아요 누르기
     */
    public LikeResponse clickCommentLikes(Long commentId , String token){


        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        Member member = jwtTokenProvider.getMember(token);

        CommentLike commentLike = CommentLike.builder()
                .member(member)
                .comment(comment)
                .build();

        commentLikeRepository.save(commentLike);
        comment.plusLikeNum();
        commentRepository.save(comment);

        return new LikeResponse(commentId,comment.getLikeNum());

    }

    /**
     * 댓글 좋아요 취소하기
     */
    @Transactional
    public LikeResponse cancelCommentLikes(Long commentId , String token){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        Member member = jwtTokenProvider.getMember(token);

        commentLikeRepository.deleteByComment_IdAndMemberUsername(commentId,member.getUsername());

        comment.minusLikeNum();
        commentRepository.save(comment);

        return new LikeResponse(commentId,comment.getLikeNum());

    }

    /**
     * 댓글 좋아요 기록이 존재 확인
     */

    public  boolean existCommentLikeWithUserName(Long commentId ,String username ){

        return commentLikeRepository.existsByComment_IdAndMemberUsername(commentId, username);
    }


    public  boolean existCommentLikeWithToken(Long commentId ,String token ){

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        Member member = jwtTokenProvider.getMember(token);

        return commentLikeRepository.existsByComment_IdAndMemberUsername(commentId, member.getUsername());
    }


    /**
     * 상위 50개 댓글 가져오기
     */

    public List<CommentResponse> getBestLikesCommentList ( Long titleHakwonId ,String token ,int limit){

        Page<Comment> comments = fetchBestCommentPages(titleHakwonId,limit);


        if(token== null || !jwtTokenProvider.validateToken(token)){

            return getCommentList(comments.getContent(),null);

        }else {
            String username = jwtTokenProvider.getUserNameWithToken(token);
            if (memberRepository.findByUsername(username).isPresent()) {
                return getCommentList(comments.getContent(), username);
            } else {
                return getCommentList(comments.getContent(), null);
            }

        }
    }


    private Page<Comment> fetchBestCommentPages(Long titleHakwonId ,int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return commentRepository.findByTitleHakwonIdAndLikeNumGreaterThanOrderByLikeNumDesc(titleHakwonId ,0, pageRequest);
    }


}
