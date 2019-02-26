package com.trip.entity;

import java.math.BigDecimal;

public class BuyInfoFormulGson {

    private BigDecimal salesTotal;//销售总金额
    private BigDecimal agentTotal;//代理返佣总金额
    private BigDecimal purchaseTotal;//采购总金额
    private BigDecimal upAgentRefundTotal;//上级代理返佣总金额
    private BigDecimal settlementTotal;	// 结算总金额
    private BigDecimal compensateTotal;	// 赔偿单总金额

    public BigDecimal getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

    public BigDecimal getAgentTotal() {
        return agentTotal;
    }

    public void setAgentTotal(BigDecimal agentTotal) {
        this.agentTotal = agentTotal;
    }

    public BigDecimal getPurchaseTotal() {
        return purchaseTotal;
    }

    public void setPurchaseTotal(BigDecimal purchaseTotal) {
        this.purchaseTotal = purchaseTotal;
    }

    public BigDecimal getUpAgentRefundTotal() {
        return upAgentRefundTotal;
    }

    public void setUpAgentRefundTotal(BigDecimal upAgentRefundTotal) {
        this.upAgentRefundTotal = upAgentRefundTotal;
    }

	public BigDecimal getSettlementTotal() {
		return settlementTotal;
	}

	public void setSettlementTotal(BigDecimal settlementTotal) {
		this.settlementTotal = settlementTotal;
	}

	public BigDecimal getCompensateTotal() {
		return compensateTotal;
	}

	public void setCompensateTotal(BigDecimal compensateTotal) {
		this.compensateTotal = compensateTotal;
	}
}
