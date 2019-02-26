package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.AgentService;
import com.trip.service.LogAopAnnotation;
import com.trip.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AgentController {

    @Autowired
    private AgentService agentService;

    /**
     * 新增代理(或修改)
     * @param agentName
     * @param request
     * @return
     */
    @RequestMapping(value="/agent/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.AGENT,operateType = OperateTypeEnum.ADD)
    public String addAgent(@RequestParam("agentName")String agentName,
                              @RequestParam(name="id",required = false)Long id,
                              @RequestParam(name="superAgent",required = false)Long superAgent,
                              @RequestParam(name="payeeAccount",required = false)String payeeAccount,
                              @RequestParam(name="payeeBank",required = false)String payeeBank,
                              @RequestParam(name="payeeName",required = false)String payeeName,
                              @RequestParam(name="payeePhone",required = false)String payeePhone,
                              @RequestParam(name="status",required = false)String status,
                              @RequestParam(name="agentType",required = false)String agentType,
                              @RequestParam(name="vipRemark",required = false)String vipRemark,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = agentService.addAgent(id,agentName,superAgent,currentUserId,
                payeeAccount,payeeBank,payeeName,payeePhone,status,agentType,vipRemark);

        return resultEntity.toString();
    }

    @RequestMapping(value="/agent/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAgent(@RequestParam(name="agentName",required = false)String agentName,
    						 @RequestParam(name="status",required=false)String status,
    						 @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
 							 @RequestParam(value="pageSize", required=false) String pageSize	// 条数
 							 ){

        ResultEntity resultEntity = agentService.queryAgent(agentName,status, pageNum, pageSize);

        return resultEntity.toString();
    }



}

