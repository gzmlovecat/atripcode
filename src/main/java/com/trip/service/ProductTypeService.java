package com.trip.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.ProductTypeDto;
import com.trip.dto.TripTypeDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.ProductTypeMapper;
import com.trip.mapper.TripTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;


    public ResultEntity addProductType(String productTypeName,Long currentUserId){
        ProductTypeDto productTypeDto = new ProductTypeDto();
        productTypeDto.setProductTypeName(productTypeName);
        productTypeDto.setCreatedBy(currentUserId);
        productTypeDto.setCreatedAt(new Date());
        try{
            productTypeMapper.insertProductType(productTypeDto);
            return new ResultEntity(ResultEnum.SUCCESS,productTypeDto);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }catch (Exception e){
            Logger.error(this,"addProductType error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity selectProductTypeList(String productTypeName, String pageNum1, String pageSize1){

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
            List<ProductTypeDto> list = productTypeMapper.selectProductTypeList(productTypeName);
            PageInfo<ProductTypeDto> pageList = new PageInfo<ProductTypeDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
            
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"selectProductTypeList error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }







}
