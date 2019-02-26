package com.trip.controller;


import com.trip.entity.ResultEntity;
import com.trip.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value="/login",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestParam(value="username")String username,
                       @RequestParam(value="password")String password,
                               HttpServletRequest request,HttpServletResponse response) throws Exception{

        ResultEntity resultEntity = accountService.checkUserAndPwd(username,password,false,request,response);

        return resultEntity.toString();
    }

}
