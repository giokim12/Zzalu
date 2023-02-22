package com.samsamoo.zzalu.domain.titlehakwon.dto;

import com.samsamoo.zzalu.domain.titlehakwon.entity.TitleHakwon;
import com.samsamoo.zzalu.domain.titlehakwon.enums.TitleHakwonState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Getter
public class TitleHakwonResponse {

    /** 제목학원 open 날짜 **/

    private Long titleHakwonId;
    private String openDate;

    private String zzalUrl;

    private TitleHakwonState state;


    public TitleHakwonResponse(TitleHakwon titleHakwon) {
        this.titleHakwonId = titleHakwon.getId();
        this.openDate = titleHakwon.getOpenDate();
        this.zzalUrl = titleHakwon.getZzalUrl();
        this.state = titleHakwon.getState();

    }

    public  static List<TitleHakwonResponse> convertToDtoList (List<TitleHakwon> titleHakwonList){
        List<TitleHakwonResponse> titleHakwonResponseList = new ArrayList<>();

        for(TitleHakwon titleHakwon:titleHakwonList){
            titleHakwonResponseList.add(new TitleHakwonResponse(titleHakwon));
        }
        return titleHakwonResponseList;
    }
}
