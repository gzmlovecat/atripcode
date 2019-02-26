package com.trip.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.MySessionContext;
import com.trip.dto.AccountDto;
import com.trip.dto.AgentDto;
import com.trip.dto.EmployeeDto;
import com.trip.dto.TraceDto;
import com.trip.entity.AccountGson;
import com.trip.entity.AesUtil;
import com.trip.entity.EncryptedDataGson;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.entity.UserInfoEntity;
import com.trip.entity.WxUtil;
import com.trip.mapper.AccountMapper;
import com.trip.mapper.AgentMapper;
import com.trip.mapper.PayMapper;

import net.sf.json.JSONObject;


@Service
public class PayService {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private AgentMapper agentMapper;
    
    
    public ResultEntity addOutTrace(String orderNo, String agentId, String payStatus){
    	TraceDto traceDto = new TraceDto();
    	traceDto.setOrderNo(orderNo);
    	traceDto.setAgentId(agentId);
    	traceDto.setPayStatus(payStatus);
		
		try{

		  payMapper.insertOutTrace(traceDto);
		  return new ResultEntity(ResultEnum.SUCCESS, traceDto);
		}catch (Exception e){
		  Logger.error(this,"addOutTrace error:",e);
		  return new ResultEntity(ResultEnum.SYS_ERROR);
		}
			
	}
    
    public ResultEntity queryOutTrace(String agentName, String orderDate,
    		String payStatus, String pageNum1, String pageSize1){

        try{
        	int pageNum = 1;
        	int pageSize = 30;
            if(pageNum1 != null && !"".equals(pageNum1)) {
            	pageNum = Integer.parseInt(pageNum1);
            }
            if(pageSize1 != null && !"".equals(pageSize1)) {
            	pageSize = Integer.parseInt(pageSize1);
            }
            
            PageHelper.startPage(pageNum, pageSize);
            List<TraceDto> list = payMapper.selectTraceByParam(agentName,orderDate,payStatus);
            PageInfo<TraceDto> pageList = new PageInfo<TraceDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
            
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"queryOutTrace error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }
    
    public ResultEntity updateOutTrace(String payStatus, String orderNo) {
    	
        try{
           
        	payMapper.updateTraceByParam(payStatus, orderNo);
           
            return new ResultEntity(ResultEnum.SUCCESS,null);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }
        catch (Exception e){
        	System.out.println("updateAgentPayee error:" + e.getMessage());
            Logger.error(this,"updateAgentPayee error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }
    
}
