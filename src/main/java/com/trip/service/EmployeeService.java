package com.trip.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.AgentDto;
import com.trip.dto.EmployeeDto;
import com.trip.dto.SupplierDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.EmployeeMapper;
import com.trip.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;




    public ResultEntity addEmployee(String employeeName,Long currentUserId){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeName(employeeName);
        employeeDto.setCreatedBy(currentUserId);
        employeeDto.setCreatedAt(new Date());

        try{
            employeeMapper.insertEmployee(employeeDto);
            return new ResultEntity(ResultEnum.SUCCESS,employeeDto);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }catch (Exception e){
            Logger.error(this,"addEmployee error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity queryEmployee(String employeeName,Long id,String pageNum1,String pageSize1){

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
            List<EmployeeDto> list = employeeMapper.selectEmployeeList(employeeName,id);
            PageInfo<EmployeeDto> pageList = new PageInfo<EmployeeDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
        	
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"queryEmployee error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }







}
