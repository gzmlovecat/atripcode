package com.trip.entity;

public enum OperateTypeEnum {

    ADD("add","新增"),
    UPDATE("update","修改"),
    DELETE("delete","删除"),


    ;

    OperateTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
