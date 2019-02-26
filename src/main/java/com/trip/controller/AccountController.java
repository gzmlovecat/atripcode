package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.AccountService;
import com.trip.service.LogAopAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 新增账户
     * @param accountType
     * @param username
     * @param password
     * @param agentId
     * @param employeeId
     * @param request
     * @return
     */
    @RequestMapping(value="/account/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ACCOUNT,operateType = OperateTypeEnum.ADD)
    public String addAccount(@RequestParam(name = "agentId",required=false)Long agentId,
            				 @RequestParam(name = "employeeId",required=false)Long employeeId,
            				 @RequestParam(name = "accountType",required=true)String accountType,
                             @RequestParam(name = "username",required=true)String username,
                             @RequestParam(name = "password",required=true)String password,
                             @RequestParam(name = "status",required=true)String status,
                             
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = accountService.addAccount(accountType,username,password,status,employeeId,agentId,
                currentUserId,null,null);

        return resultEntity.toString();
    }


    /**
     * 修改账户信息
     * @param id
     * @param username
     * @param password
     * @param status
     * @param request
     * @return
     */
    @RequestMapping(value="/account/update",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ACCOUNT,operateType = OperateTypeEnum.UPDATE)
    public String updateAccount(
                             @RequestParam("id")Long id,
                             @RequestParam(name="username",required = false)String username,
                             @RequestParam(name="password",required = false)String password,
                             @RequestParam(name="status",required = false)String status,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = accountService.updateAccount(id,username,password,status,currentUserId);

        return resultEntity.toString();
    }


    @RequestMapping(value="/account/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAccount(@RequestParam(name="username",required = false)String username,
                               @RequestParam(name="employeeName",required = false)String employeeName,
                               @RequestParam(name="agentName",required = false)String agentName,
                               @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
   							   @RequestParam(value="pageSize", required=false) String pageSize	// 条数
   							   ){

        ResultEntity resultEntity = accountService.queryAccount(username,employeeName,agentName,pageNum,pageSize);

        return resultEntity.toString();
    }



}
