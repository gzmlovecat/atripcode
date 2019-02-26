package com.trip.mapper;

import com.trip.dto.LogDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper {


    public void insertLog(LogDto logDto);

    public List<LogDto> selectLogByParam(@Param("logType") String logType);

}
