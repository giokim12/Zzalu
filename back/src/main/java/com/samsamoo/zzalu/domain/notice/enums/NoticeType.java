package com.samsamoo.zzalu.domain.notice.enums;

public enum NoticeType {
    /** 생성 요철 (CREATE) **/
    /** 업데이트 요청 (UPDATE) **/
    ROLE("R"),SYSTEM("S");
    private String code;
    private NoticeType(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
