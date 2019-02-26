package com.trip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.trip.dto.CompensateDetailDto;

public interface CompensateDetailMapper {
	public List<Map> selectCompensateHistory(@Param("orderId") Long orderId);
	
	public void insertCompensateDetail(CompensateDetailDto param);
	
	/**
	 * 取得订单ID的赔偿金额合计
	 */
	public Map selectCompensateDetailPriceByParam(@Param("orderId") Long orderId);
}
