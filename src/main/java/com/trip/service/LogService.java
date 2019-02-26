package com.trip.service;


import com.trip.dto.AccountDto;
import com.trip.dto.LogDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.AccountMapper;
import com.trip.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    public ResultEntity queryLog(String logType){
        try{
            List<LogDto> list = logMapper.selectLogByParam(logType);
            return new ResultEntity(ResultEnum.SUCCESS,list);
        }catch (Exception e){
            Logger.error(this,"queryLog error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }







}
