package com.trip.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.AddressDto;
import com.trip.dto.ArticleDto;
import com.trip.dto.EmployeeDto;
import com.trip.dto.ProductTypeDto;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.AddressMapper;
import com.trip.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public ResultEntity addArticle(String content,String publishTime,Long currentUserId){
        ArticleDto articleDto = new ArticleDto();
        articleDto.setContent(content);
        articleDto.setPublishTime(publishTime);
        articleDto.setCreatedBy(currentUserId);
        articleDto.setCreatedAt(new Date());

        try{
            articleMapper.insertArticle(articleDto);
            return new ResultEntity(ResultEnum.SUCCESS,articleDto);
        }catch (Exception e){
            Logger.error(this,"addArticle error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }

    public ResultEntity updateArticle(String content,Long currentUserId,Long id,String publishTime,
                                      boolean soldout,boolean isDelete){
        ArticleDto articleDto = new ArticleDto();
        articleDto.setContent(content);
        articleDto.setPublishTime(publishTime);
        articleDto.setSoldout(soldout);
        articleDto.setDelete(isDelete);
        articleDto.setUpdatedBy(currentUserId);
        articleDto.setUpdatedAt(new Date());
        articleDto.setId(id);
        try{
            articleMapper.updateArticle(articleDto);
            return new ResultEntity(ResultEnum.SUCCESS,articleDto);
        }catch (Exception e){
            Logger.error(this,"updateArticle error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }

    public ResultEntity selectArticleByParam(String content,String publishTime,String pageNum1,String pageSize1){

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
            List<ArticleDto> list = articleMapper.selectArticleByParam(content,publishTime);
            PageInfo<ArticleDto> pageList = new PageInfo<ArticleDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
        	
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"selectArticleByParam error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }



}
