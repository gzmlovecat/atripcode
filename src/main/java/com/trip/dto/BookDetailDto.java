package com.trip.dto;

import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

public class BookDetailDto<T> extends BaseDto{

    private Long orderId;
    private String standard;//规格
    private BigDecimal salePrice;//售价（单价）
    private BigDecimal agentReturnAmount;//代理返佣单价
    private Integer buyAmount;//购买数量
    private Long version;
    private BigDecimal agentExtraReturnAmount;//代理额外返佣单价
    private String extraType;	// 额外收益类型
//    private String extraTypeName;// 额外收益类型名
    @Transient
    private Long oldVersion;

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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getAgentReturnAmount() {
        return agentReturnAmount;
    }

    public void setAgentReturnAmount(BigDecimal agentReturnAmount) {
        this.agentReturnAmount = agentReturnAmount;
    }

    public Integer getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(Integer buyAmount) {
        this.buyAmount = buyAmount;
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

	public BigDecimal getAgentExtraReturnAmount() {
		return agentExtraReturnAmount;
	}

	public void setAgentExtraReturnAmount(BigDecimal agentExtraReturnAmount) {
		this.agentExtraReturnAmount = agentExtraReturnAmount;
	}

	public String getExtraType() {
		return extraType;
	}

	public void setExtraType(String extraType) {
		this.extraType = extraType;
	}

//	public String getExtraTypeName() {
//		return extraTypeName;
//	}
//
//	public void setExtraTypeName(String extraTypeName) {
//		this.extraTypeName = extraTypeName;
//	}
}
