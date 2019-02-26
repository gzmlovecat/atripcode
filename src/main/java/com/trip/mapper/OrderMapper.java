package com.trip.mapper;

import com.trip.dto.OrderDto;
import com.trip.entity.OrderQueryEntity;
import com.trip.entity.OrderQueryResultEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {


    public void insertOrder(OrderDto param);

    public List<OrderDto> selectOrderList(@Param("param") OrderQueryEntity orderQueryEntity);
    public OrderDto selectOneOrder(@Param("id") Long id);

    public int updateOrder(@Param("param") OrderQueryResultEntity orderQueryEntity);
    
}
