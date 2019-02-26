package com.trip.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.trip.dto.TraceDto;

public interface PayMapper {

    public void insertOutTrace(TraceDto traceDto);
    
    public List<TraceDto> selectTraceByParam(@Param("agentName") String agentName,
            @Param("orderDate") String orderDate,
            @Param("payStatus") String payStatus);
    
    public int updateTraceByParam(@Param("payStatus")String payStatus, @Param("orderNo")String orderNo);
}
