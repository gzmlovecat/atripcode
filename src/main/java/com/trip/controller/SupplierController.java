package com.trip.controller;


import com.trip.dto.AccountDto;
import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.AccountService;
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
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 新增供应商
     * @param supplierName
     * @param request
     * @return
     */
    @RequestMapping(value="/supplier/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.SUPPLIER,operateType = OperateTypeEnum.ADD)
    public String addSupplier(@RequestParam("supplierName")String supplierName, HttpServletRequest request) {

//        Long currentUserId = UserInfoEntity.getUserId(request);
//    	ResultEntity resultEntity = supplierService.addSupplier(supplierName,currentUserId);
    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        ResultEntity resultEntity = supplierService.addSupplier(supplierName,currentUser.getId());

        return resultEntity.toString();
    }




    @RequestMapping(value="/supplier/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String querySupplier(@RequestParam(name="supplierName",required = false)String supplierName,
    							@RequestParam(value="pageNum", required=false) String pageNum,	// 页数
    							@RequestParam(value="pageSize", required=false) String pageSize	// 条数
    							){

        ResultEntity resultEntity = supplierService.querySupplier(supplierName, pageNum, pageSize);

        return resultEntity.toString();
    }



}
