package com.trip.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.AccountDto;
import com.trip.dto.AddressDto;
import com.trip.dto.EmployeeDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.AddressMapper;
import com.trip.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;




    public ResultEntity addAddress(String addressType,String addressDetail,Long currentUserId){
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressType(addressType);
        addressDto.setAddressDetail(addressDetail);
        addressDto.setCreatedBy(currentUserId);
        addressDto.setCreatedAt(new Date());

        try{
            addressMapper.insertAddress(addressDto);
            return new ResultEntity(ResultEnum.SUCCESS,addressDto);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }catch (Exception e){
            Logger.error(this,"addAddress error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity queryAddressList(String addressType,String addressDetail,String pageNum1,String pageSize1){

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
            List<AddressDto> list = addressMapper.selectAddressList(addressType,addressDetail);
            PageInfo<AddressDto> pageList = new PageInfo<AddressDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
        	
//            List<EmployeeDto> list = addressMapper.selectAddressList(addressType,addressDetail);
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"queryAddressList error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }







}
