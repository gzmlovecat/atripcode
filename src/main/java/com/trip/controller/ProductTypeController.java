package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.LogAopAnnotation;
import com.trip.service.ProductTypeService;
import com.trip.service.TripTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 新增产品类型
     * @param productTypeName
     * @param request
     * @return
     */
    @LogAopAnnotation(logType = LogTypeEnum.PRODUCT_TYPE,operateType = OperateTypeEnum.ADD)
    @RequestMapping(value="/productType/add",method = RequestMethod.POST)
    @ResponseBody
    public String addSupplier(@RequestParam("productTypeName")String productTypeName,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = productTypeService.addProductType(productTypeName,currentUserId);

        return resultEntity.toString();
    }




    @RequestMapping(value="/productType/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAccount(@RequestParam(name="productTypeName",required = false)String productTypeName,
    		   				   @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
    		   				   @RequestParam(value="pageSize", required=false) String pageSize	// 条数
    						){

        ResultEntity resultEntity = productTypeService.selectProductTypeList(productTypeName,pageNum,pageSize);

        return resultEntity.toString();
    }



}
