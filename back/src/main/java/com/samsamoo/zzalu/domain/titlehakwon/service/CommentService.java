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
     * λκΈ μμ±
     */

    public CommentResponse addComment (String token ,CommentRequest requestComment){

        Member member = jwtTokenProvider.getMember(token);
        //μλ μ λͺ©νμμ λκΈμ κ²μνλ € ν κ²½μ° μμΈμ²λ¦¬
        TitleHakwon titleHakwon = titleHackwonRepository.findTitleHakwonById(requestComment.getTitleHakwonId()).orElseThrow(()-> new TitleHakwonException());
        //μ΄λ―Έ λλ¬κ±°λ μ΄λ¦¬μ§ μμ μ λͺ©νμμ λνμ¬ λκΈμ κ²μνλ € ν κ²½μ° μμΈμ²λ¦¬
        if(titleHakwon.getState()== TitleHakwonState.DONE || titleHakwon.getState() ==TitleHakwonState.NOT_OPEN){
            throw new BadRequestException("[ERROR] κΈ°νμ΄ λλκ±°λ μ΄λ¦¬μ§ μμ μ λͺ©νμμλ λκΈμ λ¬ μ μμ΅λλ€. μ λͺ©νμμ νμ°¨λ₯Ό νμΈν΄ μ£ΌμΈμ");
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
     * λλκΈ μμ±
     */
    public ReplyCommentResponse addReplyComment (String token ,ReplyCommentRequest replyCommentRequest){



        Member member = jwtTokenProvider.getMember(token);
        Comment comment =  commentRepository.findById(replyCommentRequest.getParentCommentId()).orElseThrow(()->new CommentNotFoundException("[ERROR] λλκΈμ λ¬ λκΈμ΄ μ‘΄μ¬νμ§ μμ΅λλ€."));

        ReplyComment replyComment = ReplyComment.builder()
                .member(member)
                .content(replyCommentRequest.getContent())
                .parentComment(comment)
                .build();

      replyCommentRepository.save(replyComment);

        return new ReplyCommentResponse(replyComment);
    }

    /**
     * λκΈ μ΅μ μ μ‘°ννκΈ°
     * λ¬΄ν μ€ν¬λ‘€ / μ»€μ κΈ°λ° νμ΄μ§ λ€μ΄μ
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
     * λκΈ κ³Όκ±°μ μ‘°ννκΈ°
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
            //μ¬μ©μκ° λ‘κ·ΈμΈμ΄ λμ΄μλ κ²½μ° μ’μμλ₯Ό λλ λ κΈ°λ‘μ λΆλ¬μ¨λ€.
            List<CommentResponse> commentResponseList = new ArrayList<>();

            for(Comment comment : commentList){
                CommentResponse  commentResponse = new CommentResponse(comment);

                //μ’μμ λλ₯Έ κΈ°λ‘μ΄ μ‘΄μ¬νλ€λ©΄
                if(existCommentLikeWithUserName(comment.getId(),username)){
                    //μ’μμ λλ₯Έ κΈ°λ‘μΌλ‘ λ³΄λΈλ€.
                    commentResponse.updateIsPressed();
                }

                commentResponseList.add(commentResponse);
            }

            return commentResponseList;
        }
    }

    /**
     * λλκΈ μ΅μ μ κ°μ Έμ€κΈ°
     * μ»€μ κΈ°λ° νμ΄μ§ λ€μ΄μ
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
     * λλκΈ κ³Όκ±°μ κ°μ Έμ€κΈ°
     * μ»€μ κΈ°λ° νμ΄μ§ λ€μ΄μ
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
     * λκΈ μμ 
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
     * λλκΈ μμ 
     */

  public void updateReplyComment (UpdateCommentRequest ur){

      ReplyComment replyComment = replyCommentRepository.findById(ur.getCommentId()).orElseThrow(()->new CommentNotFoundException("[ERROR] μ­μ νλ €λ λλκΈμ΄ μ‘΄μ¬νμ§ μμ΅λλ€."));

      if(replyComment!=null){
          if(!StringUtils.isEmpty(ur.getContent())){
              replyComment.upDateContent(ur.getContent(),true);
          }
          replyCommentRepository.save(replyComment);
      }
  }

    /**
     * λκΈ μ­μ 
     */
    @Transactional
    public void  deleteComment (Long commentId ,String token){
        Member member = jwtTokenProvider.getMember(token);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());

        if(!member.getUsername().equals(comment.getMember().getUsername())){
            throw new BadRequestException("[ERROR] μ­μ νλ €λ νμμ λ³΄μ λκΈ μμ±μκ° μΌμΉνμ§ μμ΅λλ€.");
        }
         commentRepository.deleteById(commentId);


    }

    /**
     * λλκΈ μ­μ 
     */
    @Transactional
    public void  deleteReplyComment(Long replyCommentId , String token){

        Member member = jwtTokenProvider.getMember(token);
        ReplyComment replyComment =  replyCommentRepository.findById(replyCommentId).orElseThrow(()-> new CommentNotFoundException("[ERROR] μ­μ νλ €λ λλκΈμ΄ μ‘΄μ¬νμ§ μμ΅λλ€."));

        if(!member.getUsername().equals(replyComment.getMember().getUsername())){
            throw new BadRequestException("[ERROR] μ­μ νλ €λ νμμ λ³΄μ λλκΈ μμ±μκ° μΌμΉνμ§ μμ΅λλ€.");
        }

        replyCommentRepository.deleteById(replyCommentId);

    }

    /**
     * λκΈμ μ’μμ λλ₯΄κΈ°
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
     * λκΈ μ’μμ μ·¨μνκΈ°
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
     * λκΈ μ’μμ κΈ°λ‘μ΄ μ‘΄μ¬ νμΈ
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
     * μμ 50κ° λκΈ κ°μ Έμ€κΈ°
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
