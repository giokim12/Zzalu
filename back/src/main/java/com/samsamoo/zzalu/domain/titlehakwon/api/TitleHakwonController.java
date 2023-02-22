package com.samsamoo.zzalu.domain.titlehakwon.api;


import com.samsamoo.zzalu.domain.titlehakwon.dto.CommentResponse;
import com.samsamoo.zzalu.domain.titlehakwon.dto.ReplyCommentResponse;
import com.samsamoo.zzalu.domain.titlehakwon.dto.TitleHakwonDetailResponse;
import com.samsamoo.zzalu.domain.titlehakwon.dto.TitleHakwonResponse;
import com.samsamoo.zzalu.domain.titlehakwon.enums.TitleHakwonState;
import com.samsamoo.zzalu.domain.titlehakwon.repository.TitleHackwonRepository;
import com.samsamoo.zzalu.domain.titlehakwon.entity.TitleHakwon;
import com.samsamoo.zzalu.domain.titlehakwon.service.CommentService;
import com.samsamoo.zzalu.domain.titlehakwon.service.TitleHakwonService;
import com.samsamoo.zzalu.infra.amazonS3.upLoader.S3Uploader;
import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/title-hakwon")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TitleHakwonController {

    private String DIRNAME ="TitleHakwon";
    private final S3Uploader s3Uploader;
    private  final TitleHackwonRepository titleHackwonDao;
    private final CommentService commentService;
    private final TitleHakwonService titleHakwonService;

    private final JwtTokenProvider jwtTokenProvider;



    //data로 넘어오는 MultipartFile을 S3Uploader로 전달
    @PostMapping("/uploadToAmazon")
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {

        String url =  s3Uploader.upload(multipartFile, DIRNAME);
        if(url==""){

            return "r";
        }else{
            //타이틀 학원을 등록한다.
            TitleHakwon titleHakwon = new TitleHakwon();
            titleHakwon.setZzalUrl(url);
            titleHakwon.setState(TitleHakwonState.NOT_OPEN);
            System.out.println("생성");
            System.out.println(TitleHakwonState.NOT_OPEN+"@@@@");
            System.out.println(TitleHakwonState.NOT_OPEN+"@@@@");
            titleHackwonDao.save(titleHakwon);

            return url;
        }


    }

    //제목 학원 등록하기


    @PostMapping("/upload")
    public  ResponseEntity<String>   uploadTitleHakwon(@RequestParam ("zzalUrl") String url) throws IOException{

        ResponseEntity response = null;


        if(url==""){
            return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
        }else{
            //타이틀 학원을 등록한다.
            TitleHakwon titleHakwon = new TitleHakwon();
            titleHakwon.setZzalUrl(url);
            titleHakwon.setState(TitleHakwonState.NOT_OPEN);
            titleHackwonDao.save(titleHakwon);

            return new ResponseEntity<String>("sucess", HttpStatus.OK);

        }

    }

    /**
     * 오늘의 제목학원 정보 얻어오기
     */

    @GetMapping(value = "/{openDate}")
    public ResponseEntity<TitleHakwonDetailResponse> getTitlehakwonInfo(@PathVariable String openDate){

        return new ResponseEntity<>(titleHakwonService.getTitleHakwonInfo(openDate), HttpStatus.OK);
    }




    /**
     * [GET]
     * 댓글 가져오기
     * Cursor 기반 페이징
     */

    @GetMapping("/{titleHakwonId}/comments")
    public  ResponseEntity<List<CommentResponse>> getRecentCommentList (@RequestHeader( required = false, value = "Authorization") String bearerToken ,@PathVariable Long titleHakwonId, @RequestParam Long lastCid , @RequestParam int limit, @RequestParam String sort){



        String token = jwtTokenProvider.getToken(bearerToken);

        List<CommentResponse> commentResponseList;
        if(sort.equals("LATEST")){
            commentResponseList = commentService.getRecentCommentList(titleHakwonId,lastCid,limit, token);

            for(CommentResponse c : commentResponseList){
                System.out.println(c.getContent());
            }

            return new ResponseEntity<>(commentResponseList,HttpStatus.OK);
        }

        else if (sort.equals("CHRONOLOGICAL")) {
            commentResponseList = commentService.getPastCommentList(titleHakwonId,lastCid,limit, token);
            return new ResponseEntity<>(commentResponseList,HttpStatus.OK);
        }

        else {
            return null;
        }


    }

    /**
     * [GET]
     * 대댓글  최신순 가져오기
     * Cursor 기반 페이징
     */

    @GetMapping(value = "/comments/{parentId}/reply")
    public  ResponseEntity<List<ReplyCommentResponse>> getReplyCommentList (@PathVariable Long parentId, @RequestParam Long lastCid , @RequestParam int limit, @RequestParam String sort){



        //status code =200
        List<ReplyCommentResponse> replyCommentResponseList;
        if(sort.equals("LATEST")){
            replyCommentResponseList = commentService.getRecentReplyCommentList( lastCid ,  parentId ,   limit );
            return new ResponseEntity<>(replyCommentResponseList,HttpStatus.OK);
        }else if (sort.equals("CHRONOLOGICAL")){
            replyCommentResponseList = commentService.getPastReplyCommentList( lastCid ,  parentId ,   limit );
            return new ResponseEntity<>(replyCommentResponseList,HttpStatus.OK);
        }else {
            return null;
        }

    }



    /**
     * [GET]
     * 상위 좋아요 50개 댓글
     */
    @GetMapping("/{titleHakwonId}/comments/best")
    public  ResponseEntity<List<CommentResponse>> getBest50CommentList (@RequestHeader( required = false, value = "Authorization") String bearerToken,@PathVariable Long titleHakwonId, @RequestParam int limit ,@RequestParam String sort){
        String token = jwtTokenProvider.getToken(bearerToken);
        //code 200
        if(sort.equals("POPULAR")){
            List<CommentResponse> commentResponseList = commentService.getBestLikesCommentList(titleHakwonId, token,limit);
            return new ResponseEntity<>(commentResponseList,HttpStatus.OK);
        }
        return null;

    }

    /**
     * [GET]
     역대 제목학원 리스트 조회하기
     */

    @GetMapping()
    public ResponseEntity<List<TitleHakwonResponse>> getTitleHakwonList(){

        List<TitleHakwonResponse> titleHakwonResponseList = titleHakwonService.getTitleHakwonList();
        return new ResponseEntity<>(titleHakwonResponseList,HttpStatus.OK);
    }

}
