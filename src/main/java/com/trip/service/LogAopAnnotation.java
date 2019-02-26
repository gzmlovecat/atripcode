package com.trip.service;

import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAopAnnotation {
    /**
     * 1、	供应商
     * 2、	代理
     * 3、	员工
     * 4、	账号
     * 5、	出发地/目的地
     * 6、	出游类型
     * 7、	产品类型

     * @return
     */
    LogTypeEnum logType();

    /**
     * 1、新增
     * 2、修改
     * 3、删除
     * @return
     */
    OperateTypeEnum operateType();

}
