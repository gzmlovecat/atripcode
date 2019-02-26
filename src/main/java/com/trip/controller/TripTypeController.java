package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.EmployeeService;
import com.trip.service.LogAopAnnotation;
import com.trip.service.TripTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class TripTypeController {

    @Autowired
    private TripTypeService tripTypeService;

    /**
     * 新增出游类型
     * @param tripTypeName
     * @param request
     * @return
     */
    @RequestMapping(value="/tripType/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.TRIP_TYPE,operateType = OperateTypeEnum.ADD)
    public String addTripType(@RequestParam("tripTypeName")String tripTypeName,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = tripTypeService.addTripType(tripTypeName,currentUserId);

        return resultEntity.toString();
    }




    @RequestMapping(value="/tripType/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryAccount(@RequestParam(name="tripTypeName",required = false)String tripTypeName,
    						   @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
    						   @RequestParam(value="pageSize", required=false) String pageSize	// 条数 
    						){

        ResultEntity resultEntity = tripTypeService.selectTripTypeList(tripTypeName,pageNum,pageSize);

        return resultEntity.toString();
    }



}
