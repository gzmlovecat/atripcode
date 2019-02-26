package com.trip.entity;

public enum ResultEnum {

    SUCCESS("00","成功"),
    LOSE_PARAM("01","缺少参数"),
    SYS_ERROR("99","系统异常"),
    NOT_LOGIN("98","未登录"),
    DUPLICATE_DATA("97","重复数据"),
    DATA_CHANGED("96","数据发生变化"),
    OPENID_TLE_NOT_FOUND("95","OPEN_ID和输入的手机号不匹配"),
    STATUS_INVALID("94","状态无效"),
    NO_TELPHONE("93","未填写手机"),
    NO_NAME("92","未填写姓名"),


    USERNAME_PWD_NOT_MATCH("11","用户名和密码不匹配或无效账号"),
    USERNAME_EXISTS("12","该用户名已存在"),
    USER_NOT_EXISTS("13","该用户不存在"),
    NOT_AGENT("14","非代理用户"),

    ORDER_NO_BOOK_DETAIL("89","无订购信息"),
    AGENT_NO_TEAM("88","无团队信息"),
    
    WX_USERINFO_ERROR("79","微信用户解密异常"),
    WX_SESSIONKEY_OPENID("78","微信获取SessionKey和OpenId失败"),
	WX_SIGNATURE_ERROR("77","签名认证失败"),
	WX_OPENID_ERROR("76","OPENID不一致"),
	WX_JSCODE_ERROR("75","JSCODE获取OPENID失败");


    ;

    ResultEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    private String resultCode;
    private String resultMsg;


    @Override
    public String toString(){
        return "{resultCode:"+this.resultCode+",resultMsg:"+this.resultMsg+"}";
    }


    public static void main(String args[]){
        System.out.println(ResultEnum.SUCCESS);
    }



}
