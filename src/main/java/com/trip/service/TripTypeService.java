package com.trip.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.AddressDto;
import com.trip.dto.TripTypeDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.TripTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TripTypeService {

    @Autowired
    private TripTypeMapper tripTypeMapper;


    public ResultEntity addTripType(String tripTypeName,Long currentUserId){
        TripTypeDto tripTypeDto = new TripTypeDto();
        tripTypeDto.setTripTypeName(tripTypeName);
        tripTypeDto.setCreatedBy(currentUserId);
        tripTypeDto.setCreatedAt(new Date());
        try{
            tripTypeMapper.insertTripType(tripTypeDto);
            return new ResultEntity(ResultEnum.SUCCESS,tripTypeDto);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }catch (Exception e){
            Logger.error(this,"addTripType error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity selectTripTypeList(String tripTypeName, String pageNum1, String pageSize1){

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
            List<TripTypeDto> list = tripTypeMapper.selectTripTypeList(tripTypeName);
            PageInfo<TripTypeDto> pageList = new PageInfo<TripTypeDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
        	
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"selectTripTypeList error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }







}
