package com.samsamoo.zzalu.domain.award.dto;


import com.samsamoo.zzalu.domain.award.entity.AwardRecord;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AwardResponse {

    int rank;
    Long titleHakwonId;
    String openDate;

    public AwardResponse(AwardRecord awardRecord) {
        this.rank = awardRecord.getRank();
        this.titleHakwonId = awardRecord.getTitleHakwon().getId();
        this.openDate = awardRecord.getTitleHakwon().getOpenDate();
    }

    public static List<AwardResponse> converToDtoList(List<AwardRecord> awardRecordList){

        List<AwardResponse> awardResponseList = new ArrayList<>();
        for(AwardRecord awardRecord :awardRecordList){
            awardResponseList.add( new AwardResponse(awardRecord));
        }

        return awardResponseList;


    }
}
