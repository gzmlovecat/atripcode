package com.trip.filter;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.trip.dto.AccountDto;
import com.trip.entity.CookieUtils;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.entity.UserInfoEntity;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
//        registration.addInitParameter("name", "alue");//添加默认参数
        registration.setName("MyFilter");
        registration.setOrder(1);//设置优先级
        return registration;
    }

    public class LoginFilter implements Filter {

        private final String[] ignoreLoginUrls = {
                "/atrip/login",
                "/atrip/miniProgram/login",	
                "/atrip/miniProgram/register",
                "/atrip/miniProgram/authorizedLogin",
                "/atrip/miniProgram/authorizedLoginOrRegister"
//                ,"/atrip/order/query"
//                ,"/atrip/supplier/add"
//                ,"/atrip/order/export"
//                ,"/atrip/order/update"
//                ,"/atrip/miniProgram/agent/inviterQRCode"
        };


        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            response.setCharacterEncoding("utf-8");

            //跨域
            httpResponse.setHeader("Access-Control-Allow-Origin",httpRequest.getHeader("origin"));
            System.out.println("############### : " + httpRequest.getHeader("origin"));
//            System.out.println("############### : " + httpRequest.getHeader("origin"));
//            System.out.println("############### : " + httpResponse.getHeader("Origin"));
//            System.out.println("############### : " + httpRequest.getHeader("Origin"));
//            httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"http://atrip.club");
//            httpResponse.setHeader("Access-Control-Allow-Origin", "http://www.atrip.club");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
            httpResponse.setHeader("Access-Control-Allow-Headers", "authorization,x-requested-with,Origin,Content-Type,Accept");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

            //打印请求Url
            System.out.println("LoginFilter,url :" + httpRequest.getRequestURI());

            if( !Arrays.asList(ignoreLoginUrls).contains(httpRequest.getRequestURI()) 
            		&& !httpRequest.getRequestURI().contains("/atrip/image")
            		&& !httpRequest.getRequestURI().contains("/atrip/agent")
            		&& !httpRequest.getRequestURI().contains("/atrip/qr")) {

                AccountDto currentUser = UserInfoEntity.getUserInfo(httpRequest);
                if( currentUser == null ){
                    httpResponse.setContentType("application/json");
                    httpResponse.getWriter().write( new ResultEntity(ResultEnum.NOT_LOGIN).toString());
                    return;
                }
            }

            filterChain.doFilter(request, response);
        }

        @Override
        public void init(javax.servlet.FilterConfig filterConfig) throws ServletException{

        }

        @Override
        public void destroy() {
        }


    }
}
