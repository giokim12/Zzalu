package com.samsamoo.zzalu.domain.titlehakwon.api;

import com.samsamoo.zzalu.domain.titlehakwon.dto.*;
import com.samsamoo.zzalu.domain.titlehakwon.repository.RedisCommentRepository;
import com.samsamoo.zzalu.domain.titlehakwon.service.CommentService;
import com.samsamoo.zzalu.global.advice.BadRequestException;
import com.samsamoo.zzalu.global.advice.ConflictException;
import com.samsamoo.zzalu.global.advice.NotFoundException;
import com.samsamoo.zzalu.infra.redis.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final RedisPublisher redisPublisher;
    private final CommentService commentService;
    private final RedisCommentRepository redisCommentRepository;


    /**
     * [POST]
     * 댓글 저장하기
     */

    @PostMapping()
    public ResponseEntity<CommentResponse> addComent(@RequestHeader(value = "Authorization") String bearerToken ,@RequestBody CommentRequest requestComent){


        if(requestComent.getContent().isBlank()){
          throw  new BadRequestException("[Error] 댓글의 내용을 입력해주세요.");
        }

        String token = bearerToken.substring(7);

        CommentResponse cr = commentService.addComment(token ,requestComent);
        redisPublisher.publishTitleHakwon(redisCommentRepository.getTopic("comments"),cr);

        return ResponseEntity.status(HttpStatus.CREATED).body(cr);

    }

    /**
     * [POST]
     * 대댓글 저장하기
     */

    @PostMapping(value = "/reply")
    public ResponseEntity<ReplyCommentResponse> addReplyComent(@RequestHeader(value = "Authorization") String bearerToken,@RequestBody ReplyCommentRequest replyCommentRequest){

        if(replyCommentRequest.getContent().isBlank()){
            throw  new BadRequestException("[Error] 대댓글의 내용을 입력해주세요.");
        }

        String token = bearerToken.substring(7);
        ReplyCommentResponse rp =  commentService.addReplyComment(token ,replyCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rp);

    }


    /**
     * [UPDATE]
     * 댓글 수정
     */
    @PatchMapping("/comment")
    public ResponseEntity<String> updateComment (@RequestBody UpdateCommentRequest commentRequest){
        commentService.updateComment(commentRequest);
        return new ResponseEntity<>("댓글 변경완료",HttpStatus.OK);
    }


    /**
     * [UPDATE]
     * 대댓글  수정
     */

    @PatchMapping(value = "/reply-comment")
    public  ResponseEntity<String> updateReplyComent (@RequestBody UpdateCommentRequest commentRequest){
        commentService.updateReplyComment(commentRequest);
        return new ResponseEntity<>("대댓글 변경완료",HttpStatus.OK);
    }


    /**
     * [DELETE]
     * 댓글 삭제
     */


    @DeleteMapping("/{commentId}")
    public  ResponseEntity deleteComment(@RequestHeader(required = false, value = "Authorization") String bearerToken ,@PathVariable Long commentId){



        String token = bearerToken.substring(7);
        commentService.deleteComment(commentId ,token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }

    /**
     * [DELETE]
     * 대댓글  삭제
     */

    @DeleteMapping(value = "/reply/{replyCommentId}")
    public  ResponseEntity deleteReplyComment(@RequestHeader(value = "Authorization") String bearerToken ,@PathVariable Long replyCommentId){
        String token = bearerToken.substring(7);
       commentService.deleteReplyComment(replyCommentId,token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    /**
     * [POST]
     * 댓글 좋아요 하기
     */

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<String> clickCommentLikes (@RequestHeader(value = "Authorization") String bearerToken ,@PathVariable Long commentId){
        String token = bearerToken.substring(7);

        //좋아요를 할껀데 기존에 누른 기록이 있었으면 예외처리
        if(commentService.existCommentLikeWithToken(commentId,token)){
            throw new ConflictException("[ERROR] 이미 좋아요를 눌렀습니다.");
        }else{

           LikeResponse likeResponse = commentService.clickCommentLikes(commentId,token);
            redisPublisher.pubLikes(redisCommentRepository.getTopic("likes"),likeResponse);

            return new ResponseEntity<>("좋아요 완료 ",HttpStatus.CREATED);
        }
    }


    /**
     * [DELETE]
     * 댓글 좋아요 취소하기
     */
    @DeleteMapping("{commentId}/likes")
    public ResponseEntity<String> cancelCommentLikes (@RequestHeader(value = "Authorization") String bearerToken ,@PathVariable Long commentId){

        String token = bearerToken.substring(7);

        if(!commentService.existCommentLikeWithToken(commentId,token)){
            throw  new NotFoundException("[Error] 좋아요를 누른 기록이 없습니다.");
        }else{

            LikeResponse likeResponse = commentService.cancelCommentLikes(commentId,token);
            redisPublisher.pubLikes(redisCommentRepository.getTopic("likes"),likeResponse);

            return new ResponseEntity<>("좋아요 취소 완료 ",HttpStatus.OK);
        }
    }


}
