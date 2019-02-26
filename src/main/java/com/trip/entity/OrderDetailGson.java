package com.trip.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.trip.dto.BookDetailDto;
import com.trip.dto.OrderDto;

public class OrderDetailGson {

    private OrderDto order;
    private Long orderId;
    private String salesman;
    private Date orderCreateStartTime;
    private List<BookingDetailGson> buyInfoInit;
    private List<BookingDetailGson> buyInfoFinal;
    private List<BookDetailDto> bookingList;
    private BuyInfoFormulGson buyInfoInitFormula;
    private BuyInfoFormulGson buyInfoFinalFormula;
    private Map mixStatus;
    private Map financeReceipt;
    private Map financePaidAgent;
    private Map financePaidSuperAgent;
    private Map financePaidSupplier;
    private Map financePaidRefund;
    
    private BigDecimal depositAmount;	// 定金金额
    
    // 财务实收
    private BigDecimal actualReceiptAmount;
    // 财务状态
    private String gatheringStatus;
    // 代理实付
    private BigDecimal actualPayAgentAmount;
    // 代理状态
    private String payAgentStatus;
    // 上级代理实付
    private BigDecimal actualPaySuperAgentAmount;
    // 上级代理状态
    private String paySuperAgentStatus;
    // 供应商实付
    private BigDecimal actualPaySupplieAmount;
    // 供应商状态
    private String paySupplierStatus;
    // 退款/赔偿实付
    private BigDecimal actualPayUserRefundAmount;
    // 退款/赔偿状态
    private String refundStatus;
    
    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public Date getOrderCreateStartTime() {
        return orderCreateStartTime;
    }

    public void setOrderCreateStartTime(Date orderCreateStartTime) {
        this.orderCreateStartTime = orderCreateStartTime;
    }

    public List<BookDetailDto> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookDetailDto> bookingList) {
        this.bookingList = bookingList;
    }

    public List<BookingDetailGson> getBuyInfoInit() {
        return buyInfoInit;
    }

    public void setBuyInfoInit(List<BookingDetailGson> buyInfoInit) {
        this.buyInfoInit = buyInfoInit;
    }

    public List<BookingDetailGson> getBuyInfoFinal() {
        return buyInfoFinal;
    }

    public void setBuyInfoFinal(List<BookingDetailGson> buyInfoFinal) {
        this.buyInfoFinal = buyInfoFinal;
    }

    public BuyInfoFormulGson getBuyInfoInitFormula() {
        return buyInfoInitFormula;
    }

    public void setBuyInfoInitFormula(BuyInfoFormulGson buyInfoInitFormula) {
        this.buyInfoInitFormula = buyInfoInitFormula;
    }

    public BuyInfoFormulGson getBuyInfoFinalFormula() {
        return buyInfoFinalFormula;
    }

    public void setBuyInfoFinalFormula(BuyInfoFormulGson buyInfoFinalFormula) {
        this.buyInfoFinalFormula = buyInfoFinalFormula;
    }

    public Map getMixStatus() {
        return mixStatus;
    }

    public void setMixStatus(Map mixStatus) {
        this.mixStatus = mixStatus;
    }

    public Map getFinanceReceipt() {
        return financeReceipt;
    }

    public void setFinanceReceipt(Map financeReceipt) {
        this.financeReceipt = financeReceipt;
    }

    public Map getFinancePaidAgent() {
        return financePaidAgent;
    }

    public void setFinancePaidAgent(Map financePaidAgent) {
        this.financePaidAgent = financePaidAgent;
    }

    public Map getFinancePaidSuperAgent() {
        return financePaidSuperAgent;
    }

    public void setFinancePaidSuperAgent(Map financePaidSuperAgent) {
        this.financePaidSuperAgent = financePaidSuperAgent;
    }

    public Map getFinancePaidSupplier() {
        return financePaidSupplier;
    }

    public void setFinancePaidSupplier(Map financePaidSupplier) {
        this.financePaidSupplier = financePaidSupplier;
    }

    public Map getFinancePaidRefund() {
        return financePaidRefund;
    }

    public void setFinancePaidRefund(Map financePaidRefund) {
        this.financePaidRefund = financePaidRefund;
    }

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public BigDecimal getActualReceiptAmount() {
		return actualReceiptAmount;
	}

	public void setActualReceiptAmount(BigDecimal actualReceiptAmount) {
		this.actualReceiptAmount = actualReceiptAmount;
	}

	public String getGatheringStatus() {
		return gatheringStatus;
	}

	public void setGatheringStatus(String gatheringStatus) {
		this.gatheringStatus = gatheringStatus;
	}

	public BigDecimal getActualPayAgentAmount() {
		return actualPayAgentAmount;
	}

	public void setActualPayAgentAmount(BigDecimal actualPayAgentAmount) {
		this.actualPayAgentAmount = actualPayAgentAmount;
	}

	public String getPayAgentStatus() {
		return payAgentStatus;
	}

	public void setPayAgentStatus(String payAgentStatus) {
		this.payAgentStatus = payAgentStatus;
	}

	public BigDecimal getActualPaySuperAgentAmount() {
		return actualPaySuperAgentAmount;
	}

	public void setActualPaySuperAgentAmount(BigDecimal actualPaySuperAgentAmount) {
		this.actualPaySuperAgentAmount = actualPaySuperAgentAmount;
	}

	public String getPaySuperAgentStatus() {
		return paySuperAgentStatus;
	}

	public void setPaySuperAgentStatus(String paySuperAgentStatus) {
		this.paySuperAgentStatus = paySuperAgentStatus;
	}

	public BigDecimal getActualPaySupplieAmount() {
		return actualPaySupplieAmount;
	}

	public void setActualPaySupplieAmount(BigDecimal actualPaySupplieAmount) {
		this.actualPaySupplieAmount = actualPaySupplieAmount;
	}

	public String getPaySupplierStatus() {
		return paySupplierStatus;
	}

	public void setPaySupplierStatus(String paySupplierStatus) {
		this.paySupplierStatus = paySupplierStatus;
	}

	public BigDecimal getActualPayUserRefundAmount() {
		return actualPayUserRefundAmount;
	}

	public void setActualPayUserRefundAmount(BigDecimal actualPayUserRefundAmount) {
		this.actualPayUserRefundAmount = actualPayUserRefundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

}


