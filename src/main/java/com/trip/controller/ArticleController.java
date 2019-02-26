package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.ArticleService;
import com.trip.service.LogAopAnnotation;
import com.trip.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 发布文章
     * @param content
     * @param request
     * @return
     */
    @RequestMapping(value="/article/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ARTICLE_TYPE,operateType = OperateTypeEnum.ADD)
    public String addArticle(@RequestParam("content")String content,
                             @RequestParam("publishTime")String publishTime,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = articleService.addArticle(content,publishTime,currentUserId);

        return resultEntity.toString();
    }


    /**
     * 更新文章
     * @param content
     * @param request
     * @return
     */
    @RequestMapping(value="/article/update",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ARTICLE_TYPE,operateType = OperateTypeEnum.UPDATE)
    public String updateArticle(@RequestParam("id")Long id,
                                @RequestParam(name="content",required = false)String content,
                                @RequestParam(name="publishTime",required = false)String publishTime,
                                @RequestParam(name="soldout",required = false)boolean soldout,
                                @RequestParam(name="isDelete",required = false)boolean isDelete,
                              HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = articleService.updateArticle(content,currentUserId,id,publishTime,soldout,isDelete);

        return resultEntity.toString();
    }


    @RequestMapping(value="/article/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryArticle(@RequestParam(name="content",required = false)String content,
			   				   @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
			   				   @RequestParam(value="pageSize", required=false) String pageSize	// 条数
			   				   ){

        ResultEntity resultEntity = articleService.selectArticleByParam(content,null,pageNum,pageSize);

        return resultEntity.toString();
    }


}
