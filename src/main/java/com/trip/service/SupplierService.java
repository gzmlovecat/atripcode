package com.trip.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.OrderDto;
import com.trip.dto.SupplierDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.SupplierMapper;


@Service
public class SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;




    public ResultEntity addSupplier(String supplierName,Long currentUserId){
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setSupplierName(supplierName);
        supplierDto.setCreatedBy(currentUserId);
        supplierDto.setCreatedAt(new Date());

        try{
        	// modify by 20180810 bug3 重复数据系统 start
        	// 查找是否有重复数据
        	int count = supplierMapper.selectSupplierByCount(supplierDto);
        	if(count >= 1) {
        		return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        	}
            supplierMapper.insertSupplier(supplierDto);
            return new ResultEntity(ResultEnum.SUCCESS,supplierDto);
            // modify by 20180810 bug3 重复数据系统 end
        }
//        catch (DuplicateKeyException dup){
//        	List<String> result = new ArrayList<>();
//            return new ResultEntity(ResultEnum.DUPLICATE_DATA, result);
//        }
        catch (Exception e){
            Logger.error(this,"addSupplier error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity querySupplier(String supplierName, String pageNum1, String pageSize1){

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
            List<SupplierDto> list = supplierMapper.selectSupplierByParam(supplierName);
            PageInfo<SupplierDto> pageList = new PageInfo<SupplierDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
            
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"querySupplier error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }







}
