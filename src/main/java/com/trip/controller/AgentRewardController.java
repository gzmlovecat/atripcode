package com.trip.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.dto.AccountDto;
import com.trip.dto.AgentRewardDto;
import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.AgentRewardService;
import com.trip.service.LogAopAnnotation;


@Controller
public class AgentRewardController {

    @Autowired
    private AgentRewardService agentRewardService;
    
    private final String[] AGENT_REWARD_TITLE = {"联系人姓名", "电话", "业务员", "上级代理", "出发时间", "产品名称", "供应商", 
    		"成人销售价", "成人人数", "儿童销售价", "儿童人数", "婴儿销售价", "婴儿人数", "附加费销售价", "附加费人数", "签证销售价", "签证人数", 
    		"", "", "", "", ""};



    @RequestMapping(value="/agentReward/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAgent(@RequestParam(name="agentName",required = false)String agentName,
    						 @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
    						 @RequestParam(value="pageSize", required=false) String pageSize	// 条数
    						){

        ResultEntity resultEntity = agentRewardService.queryAgentReward(agentName,pageNum,pageSize);

        return resultEntity.toString();
    }
    
    /**
     * 新增代理奖励
     * @param agentRewardDto
     * @param request
     * @return
     */
    @RequestMapping(value="/agentReward/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ORDER_REWARD,operateType = OperateTypeEnum.ADD)
    public String addAgentReward(@RequestBody AgentRewardDto agentRewardDto,HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        ResultEntity resultEntity = agentRewardService.addAgentReward(agentRewardDto,currentUser);

        return resultEntity.toString();
    }
    
    /**
     * 更新代理奖励
     * @param agentRewardDto
     * @param request
     * @return
     */
    @RequestMapping(value="/agentReward/update",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ORDER_REWARD,operateType = OperateTypeEnum.UPDATE)
    public String updateAgentReward(@RequestBody AgentRewardDto agentRewardDto,HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        ResultEntity resultEntity = agentRewardService.updateAgentReward(agentRewardDto,currentUser);

        return resultEntity.toString();
    }



}
