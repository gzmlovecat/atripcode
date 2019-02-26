package com.trip.service;


import com.trip.dto.LogDto;
import com.trip.entity.LogTypeEnum;
import com.trip.entity.Logger;
import com.trip.entity.UserInfoEntity;
import com.trip.mapper.LogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class LogAopService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 定义一个Pointcut,凡是加了LogAopAnnotation的都切入
     */
    @Pointcut("@annotation(com.trip.service.LogAopAnnotation)")
    public void addAdvice(){}

    @Before(value = "addAdvice()")//&& args(logAopAnnotation);, LogAopAnnotation logAopAnnotation
    public void interceptor(JoinPoint joinPoint){

        //返回的结果
        Object result = null;
        try{
            //获取当前登录用户信息
            Object[] args = joinPoint.getArgs();

            LogAopAnnotation logAopAnnotation = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(LogAopAnnotation.class);
            HttpServletRequest request = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    request = (HttpServletRequest) args[i];
                }
            }
            Long currentUserId = UserInfoEntity.getUserId(request);

            //组装日志信息并保存
            String logType = logAopAnnotation.logType().getCode();
            String operateType = logAopAnnotation.operateType().getCode();
//            if( LogTypeEnum.AGENT.equals(logType) && ){
//
//            }
            LogDto logDto = new LogDto();
            logDto.setLogType(logType);
            logDto.setOperateType(operateType);
            logDto.setCreatedBy(currentUserId);
            logDto.setCreatedAt(new Date());
            logMapper.insertLog(logDto);

        }catch (Throwable e){
            Logger.error(this,"LogAopService interceptor",e);
        }

    }

}
