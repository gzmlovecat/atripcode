package com.trip.entity;

public enum LogTypeEnum {

    SUPPLIER("supplier","供应商"),
    AGENT("agent","代理"),
    EMPLOYEE("employee","员工"),
    ACCOUNT("account","账户"),
    ADDRESS("address","地址"),
    TRIP_TYPE("tripType","出游类型"),
    PRODUCT_TYPE("productType","产品类型"),
    ARTICLE_TYPE("article","产品类型"),
    ORDER_TYPE("order","产品类型"),
    ORDER_REWARD("orderReward","代理奖励"),
    ;

    LogTypeEnum(String code, String text) {
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
