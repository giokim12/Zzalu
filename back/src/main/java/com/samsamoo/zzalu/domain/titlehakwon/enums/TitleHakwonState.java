package com.samsamoo.zzalu.domain.titlehakwon.enums;


public enum TitleHakwonState {

    /** 진행 완료 (Done) **/
    /** 진행 중 (Proceed) **/
    /** 오픈 되지 않음 (Not Open) **/
   DONE("D"),PROCEED("P"),NOT_OPEN("N");

    private String code;

    private TitleHakwonState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
