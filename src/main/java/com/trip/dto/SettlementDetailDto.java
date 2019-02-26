package com.trip.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.Transient;

public class SettlementDetailDto<T> extends BaseDto {
    private Long orderId;
    private String standard;//规格
    private BigDecimal settlementPrice;//结算（单价）
    
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
    
}
