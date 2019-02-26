package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.AddressService;
import com.trip.service.EmployeeService;
import com.trip.service.LogAopAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 新增地址
     * @param addressDetail
     * @param addressType
     * @param request
     * @return
     */
    @RequestMapping(value="/address/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ADDRESS,operateType = OperateTypeEnum.ADD)
    public String addSupplier(@RequestParam(value="addressType",required=false)String addressType, 
                              @RequestParam(value="addressDetail",required=true)String addressDetail,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = addressService.addAddress(addressType,addressDetail,
                currentUserId);

        return resultEntity.toString();
    }




    @RequestMapping(value="/address/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAccount(@RequestParam(name="addressDetail",required = false)String addressDetail,
                               @RequestParam(name="addressType",required = false)String addressType,
                               @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
   							   @RequestParam(value="pageSize", required=false) String pageSize	// 条数                   
    						){

        ResultEntity resultEntity = addressService.queryAddressList(addressType,addressDetail,pageNum,pageSize);

        return resultEntity.toString();
    }



}
