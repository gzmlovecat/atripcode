package com.trip.controller;


import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.AccountService;
import com.trip.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping(value="/log/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAccount(@RequestParam(name="logType",required = true)String logType){

        ResultEntity resultEntity = logService.queryLog(logType);

        return resultEntity.toString();
    }



}
