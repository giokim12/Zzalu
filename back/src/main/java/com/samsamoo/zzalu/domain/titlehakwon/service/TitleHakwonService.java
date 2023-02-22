package com.samsamoo.zzalu.domain.titlehakwon.service;

import com.samsamoo.zzalu.domain.award.entity.AwardRecord;
import com.samsamoo.zzalu.domain.award.repository.AwardRecordRepository;
import com.samsamoo.zzalu.domain.titlehakwon.dto.TitleHakwonDetailResponse;
import com.samsamoo.zzalu.domain.titlehakwon.dto.TitleHakwonResponse;
import com.samsamoo.zzalu.domain.titlehakwon.entity.Comment;
import com.samsamoo.zzalu.domain.titlehakwon.entity.TitleHakwon;
import com.samsamoo.zzalu.domain.titlehakwon.enums.TitleHakwonState;
import com.samsamoo.zzalu.domain.titlehakwon.exception.TitleHakwonException;
import com.samsamoo.zzalu.domain.titlehakwon.repository.CommentRepository;
import com.samsamoo.zzalu.domain.titlehakwon.repository.TitleHackwonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TitleHakwonService {
    private final TitleHackwonRepository titleHackwonRepository;
    private final CommentRepository commentRepository;
    private final AwardRecordRepository awardRecordRepository;


    /**
     * 제목학원 정보 조회
     */

    public TitleHakwonDetailResponse getTitleHakwonInfo(String openDate){

        TitleHakwon titleHakwon = titleHackwonRepository.findTitleHakwonByOpenDate(openDate).orElseThrow(()-> new TitleHakwonException());


       return new TitleHakwonDetailResponse(titleHakwon);

    }

    /**
     * 매일 새벽 0시 1분에 제목학원 오픈
     */

     @Scheduled(cron = "0 1 0 * * *")
    public void openTitleHakwon(){

        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = current.format(formatter);

        TitleHakwon titleHakwon = titleHackwonRepository.findTitleHakwonByOpenDate(date).orElseThrow(()-> new TitleHakwonException());

        if(titleHakwon.getState().getCode().equals("N")) {
            titleHakwon.setState(TitleHakwonState.PROCEED);
            titleHackwonRepository.save(titleHakwon);

        }else{
            return;
        }
    }


    /**
     * 매일 저녁 11 시  58분 제목학원 닫기 , 좋아요 1/2/3/등 유저에게 수상 기록 저장
     */

    @Scheduled(cron = "0 58 23 * * *")
    public  void setRank () {

        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = current.format(formatter);

        TitleHakwon titleHakwon = titleHackwonRepository.findTitleHakwonByOpenDate(date).orElseThrow(()-> new TitleHakwonException());

        //상위 3개의 댓글 가져오기
        List<Comment> comments = commentRepository.findTop3ByTitleHakwonIdAndLikeNumGreaterThanOrderByLikeNumDesc(titleHakwon.getId(), 0);

        for (int i = 0; i < comments.size(); i++) {

            AwardRecord awardRecord = AwardRecord.builder()
                    .member(comments.get(i).getMember())
                    .titleHakwon(comments.get(i).getTitleHakwon())
                    .rank(i + 1)
                    .build();
            awardRecordRepository.save(awardRecord);

        }

        //수상 기록 저장 후 오늘의 제목학원 종료하기
        titleHakwon.setState(TitleHakwonState.DONE);
        titleHackwonRepository.save(titleHakwon);

    }

    /**
     * 역대 제목학원 리스트 조회하기
     */

    public List<TitleHakwonResponse> getTitleHakwonList(){
        List<TitleHakwon> titleHakwonList = titleHackwonRepository.findTitleHakwonByState(TitleHakwonState.DONE);
        return TitleHakwonResponse.convertToDtoList(titleHakwonList);
    }

}
