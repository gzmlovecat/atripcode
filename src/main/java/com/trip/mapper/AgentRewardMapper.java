package com.trip.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.trip.dto.AgentRewardDto;

public interface AgentRewardMapper {


    public void insertAgentReward(AgentRewardDto agentRewardDto);

    public void updateAgentReward(@Param("param") AgentRewardDto agentRewardDto);

    public List<AgentRewardDto> selectAgentRewardByParam( Map param);

    public BigDecimal selectAgentTotalIncome(@Param("agentId") Long agentId);
    
    public BigDecimal selectAgentDayOfMonthIncome(@Param("agentId") Long agentId);
    
    public BigDecimal selectSaleTotal(@Param("agentId") Long agentId, @Param("dayOfMonth") String dayOfMonth);
    
    public List<AgentRewardDto> selectMyTeam(@Param("agentId") Long agentId);
    
    public List<AgentRewardDto> selectMyTeamImage(@Param("agentId") Long agentId);
    
    public BigDecimal selectExtraRewardAmount(@Param("agentId") Long agentId);
    
    public void updateAgentRewardById(@Param("param") AgentRewardDto agentRewardDto);
    
    /**
     * 更新代理奖励的付款状态
     * @param orderId			订单ID
     * @param rewardType		奖励类型
     * @param payAgentStatus	付款状态
     */
    public void updatePayAgentStatusByOrderId(@Param("param") AgentRewardDto agentRewardDto);
}
