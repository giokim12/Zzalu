package com.samsamoo.zzalu.domain.tempGif.enums;

public enum RequestType {

    /** 생성 요철 (CREATE) **/
    /** 업데이트 요청 (UPDATE) **/
    CREATE("C"),UPDATE("U");


    private String code;

    private RequestType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


}
