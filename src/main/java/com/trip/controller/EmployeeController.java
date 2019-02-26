package com.trip.controller;


import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.EmployeeService;
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
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 新增员工
     * @param employeeName
     * @param request
     * @return
     */
    @RequestMapping(value="/employee/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.EMPLOYEE,operateType = OperateTypeEnum.ADD)
    public String addEmployee(@RequestParam("employeeName")String employeeName,
                             HttpServletRequest request) throws Exception{

        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = employeeService.addEmployee(employeeName,currentUserId);

        return resultEntity.toString();
    }




    @RequestMapping(value="/employee/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryEmployee(@RequestParam(name="employeeName",required = false)String employeeName,
    							@RequestParam(value="pageNum", required=false) String pageNum,	// 页数
    							@RequestParam(value="pageSize", required=false) String pageSize	// 条数
    						){

        ResultEntity resultEntity = employeeService.queryEmployee(employeeName,null,pageNum,pageSize);

        return resultEntity.toString();
    }



}
