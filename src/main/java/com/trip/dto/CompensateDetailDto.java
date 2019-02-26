package com.trip.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.Transient;

public class CompensateDetailDto<T> extends BaseDto {
    private Long orderId;	// 订单ID
    private BigDecimal compensationPrice;//赔偿金额
    private String remark;//赔偿备注
    private Long version;
    private Long compensateId;	// 赔偿单ID
    @Transient
    private Long oldVersion;
    
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getCompensationPrice() {
		return compensationPrice;
	}
	public void setCompensationPrice(BigDecimal compensationPrice) {
		this.compensationPrice = compensationPrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Long getOldVersion() {
		return oldVersion;
	}
	public void setOldVersion(Long oldVersion) {
		this.oldVersion = oldVersion;
	}
    
}
