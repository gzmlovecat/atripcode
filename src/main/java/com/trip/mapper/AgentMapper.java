package com.trip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.trip.dto.AgentDto;
import com.trip.dto.AgentRewardDto;

public interface AgentMapper {


    public void insertAgent(AgentDto agentDto);
    public void updateAgent(@Param("param") AgentDto agentDto);


    public List<AgentDto> selectAgentByParam(@Param("agentName") String agentName,
                                             @Param("id") Long id,
                                             @Param("superAgent") Long superAgent,
                                             @Param("status") String status);

    public AgentDto selectOneAgent(@Param("id") Long id);

    public AgentDto selectSuperAgentByParam(@Param("id") Long id);

    public String selectSubAgentNameByParam(@Param("id") Long id);

    public int updateAgentByParam(@Param("agentName")String agentNem, @Param("id")Long id);
    
    public Map selectNickImageUrlByAgentId(@Param("agentId")Long agentId);
}
