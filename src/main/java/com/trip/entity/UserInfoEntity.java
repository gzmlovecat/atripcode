package com.trip.entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.trip.MySessionContext;
import com.trip.dto.AccountDto;

public class UserInfoEntity {

    public static AccountDto getUserInfo(HttpServletRequest request){

        AccountDto accountDto = null;
        HttpSession session = null;

        if ( request.getHeader("JSESSIONID")!=null ){
            String JESSIONID = request.getHeader("JSESSIONID").toString();
            session = MySessionContext.getSession(JESSIONID);

//        }
//
//        //如果cookie中有信息，从cookie中取，否则，
//        String userGon = "";
//
//        try{
//            userGon = URLDecoder.decode(CookieUtils.getCookieValue(request,"wx_ck_atrip"),"utf8") ;
//        }catch (Exception e){
//            Logger.error(UserInfoEntity.class,"getUserInfo decode error :",e);
//        }
//        if(StringUtils.isNotBlank(userGon)){
//            try{
//                accountDto = new Gson().fromJson(userGon,AccountDto.class);
//            }catch (Exception e){
//                Logger.error(UserInfoEntity.class,"parse user from cookie error : ",e);
//            }
        }else{
            session = request.getSession();
        }

        if( session == null ){
            return null;
        }

        accountDto = (AccountDto)session.getAttribute("userInfo");
        return accountDto;
    }


    public static Long getUserId(HttpServletRequest request) throws Exception{
        AccountDto accountDto = UserInfoEntity.getUserInfo(request);
        if( accountDto == null ){
            return null;
        }
        return accountDto.getId();
    }
    
}
