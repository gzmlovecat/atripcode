package com.trip.entity;

import com.trip.dto.BaseDto;

import java.math.BigDecimal;

public class OrderQueryEntity<T> extends BaseDto{

    private String orderId               ;
    private String orderCreateStartTime  ;
    private String orderCreateEndTime    ;
    private String tripCreateStartTime   ;
    private String tripCreateEndTime     ;
    private String productName           ;
    private String supplierId            ;
    private String salesman              ;
    private String touristPhone          ;
    private String agentId               ;
    private String tripNotifyStatus      ;
    private String contractSignStatus    ;
    private String buyInsuranceStatus    ;
    private String gatheringStatus       ;
    private String payAgentStatus        ;
    private String paySuperAgentStatus   ;
    private String paySupplierStatus     ;
    private String refundStatus          ;
    private String noPurchasePrice       ;

    private BigDecimal actualReceiptAmount;//实收金额
    private BigDecimal actualPayAgentAmount;//实付代理金额
    private BigDecimal actualPaySuperAgentAmount;//实付上级代理金额
    private BigDecimal actualPaySupplieAmount;//实付供应商金额
    private BigDecimal actualPayUserRefundAmount;//实付用户退款金额

    private BigDecimal needPayUserRefundAmount;//应付用户退款金额
    
    // add 20180801 bug13，bug14 增加订单状态字段 start
    private String orderStatus;		// 订单状态 1:未完成 2:完成
    // add 20180801 bug13，bug14 增加订单状态字段 end
    
    // add 20180802 bug15 查询字段增加出游人姓名和付款方式 start
    private String tourist;	// 出游人姓名
    private String payType;	// 付款方式
    // add 20180802 bug15 查询字段增加出游人姓名和付款方式 end
    // add 20180802 bug23 查询/列表字段增加出游类型 start
    private String tripType;	// 出游类型
    // add 20180802 bug23 查询/列表字段增加出游类型 end
    // add 20180811 新增功能9 查询/列表增加出游人信息 start
    private String touristInfo;	// 出游人信息
    // add 20180811 新增功能9 查询/列表增加出游人信息 end
    // add 20180814 新增功能 订单产品调整-增加定金收款状态 start
    private String depositStatus;	// 定金收款状态 0:无 1:未完成 2:完成
    // add 20180814 新增功能 订单产品调整-增加定金收款状态 start
    private String supplierName;
    private String agentName;
    private String pageNum;
    private String pageSize;
    
    private String purchasePriceStatus; 	// 订单采购价状态 0 不限, 1 未填写 2 已填写

    public OrderQueryEntity(){}

    public OrderQueryEntity(String orderId, String orderCreateStartTime, String orderCreateEndTime, String tripCreateStartTime,
                            String tripCreateEndTime, String productName, String supplierId, String salesman,
                            String touristPhone, String agentName, String tripNotifyStatus, String contractSignStatus,
                            String buyInsuranceStatus, String gatheringStatus, String payAgentStatus, String paySuperAgentStatus,
                            String paySupplierStatus, String refundStatus, String noPurchasePrice, 
                            String orderStatus, String tourist, String payType, String tripType, String touristInfo,
                            String depositStatus,String supplierName,String purchasePriceStatus) {
        this.orderId = orderId;
        this.orderCreateStartTime = orderCreateStartTime;
        this.orderCreateEndTime = orderCreateEndTime;
        this.tripCreateStartTime = tripCreateStartTime;
        this.tripCreateEndTime = tripCreateEndTime;
        this.productName = productName;
        this.supplierId = supplierId;
        this.salesman = salesman;
        this.touristPhone = touristPhone;
        this.agentName = agentName;
        this.tripNotifyStatus = tripNotifyStatus;
        this.contractSignStatus = contractSignStatus;
        this.buyInsuranceStatus = buyInsuranceStatus;
        this.gatheringStatus = gatheringStatus;
        this.payAgentStatus = payAgentStatus;
        this.paySuperAgentStatus = paySuperAgentStatus;
        this.paySupplierStatus = paySupplierStatus;
        this.refundStatus = refundStatus;
        this.noPurchasePrice = noPurchasePrice;
        this.orderStatus = orderStatus;
        this.tourist = tourist;
        this.payType = payType;
        this.tripType = tripType;
        this.touristInfo = touristInfo;
        this.depositStatus = depositStatus;
        this.supplierName = supplierName;
        this.purchasePriceStatus = purchasePriceStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCreateStartTime() {
        return orderCreateStartTime;
    }

    public void setOrderCreateStartTime(String orderCreateStartTime) {
        this.orderCreateStartTime = orderCreateStartTime;
    }

    public String getOrderCreateEndTime() {
        return orderCreateEndTime;
    }

    public void setOrderCreateEndTime(String orderCreateEndTime) {
        this.orderCreateEndTime = orderCreateEndTime;
    }

    public String getTripCreateStartTime() {
        return tripCreateStartTime;
    }

    public void setTripCreateStartTime(String tripCreateStartTime) {
        this.tripCreateStartTime = tripCreateStartTime;
    }

    public String getTripCreateEndTime() {
        return tripCreateEndTime;
    }

    public void setTripCreateEndTime(String tripCreateEndTime) {
        this.tripCreateEndTime = tripCreateEndTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getTouristPhone() {
        return touristPhone;
    }

    public void setTouristPhone(String touristPhone) {
        this.touristPhone = touristPhone;
    }

    public String getTripNotifyStatus() {
        return tripNotifyStatus;
    }

    public void setTripNotifyStatus(String tripNotifyStatus) {
        this.tripNotifyStatus = tripNotifyStatus;
    }

    public String getContractSignStatus() {
        return contractSignStatus;
    }

    public void setContractSignStatus(String contractSignStatus) {
        this.contractSignStatus = contractSignStatus;
    }

    public String getBuyInsuranceStatus() {
        return buyInsuranceStatus;
    }

    public void setBuyInsuranceStatus(String buyInsuranceStatus) {
        this.buyInsuranceStatus = buyInsuranceStatus;
    }

    public String getGatheringStatus() {
        return gatheringStatus;
    }

    public void setGatheringStatus(String gatheringStatus) {
        this.gatheringStatus = gatheringStatus;
    }

    public String getPayAgentStatus() {
        return payAgentStatus;
    }

    public void setPayAgentStatus(String payAgentStatus) {
        this.payAgentStatus = payAgentStatus;
    }

    public String getPaySuperAgentStatus() {
        return paySuperAgentStatus;
    }

    public void setPaySuperAgentStatus(String paySuperAgentStatus) {
        this.paySuperAgentStatus = paySuperAgentStatus;
    }

    public String getPaySupplierStatus() {
        return paySupplierStatus;
    }

    public void setPaySupplierStatus(String paySupplierStatus) {
        this.paySupplierStatus = paySupplierStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getNoPurchasePrice() {
        return noPurchasePrice;
    }

    public void setNoPurchasePrice(String noPurchasePrice) {
        this.noPurchasePrice = noPurchasePrice;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getActualReceiptAmount() {
        return actualReceiptAmount;
    }

    public void setActualReceiptAmount(BigDecimal actualReceiptAmount) {
        this.actualReceiptAmount = actualReceiptAmount;
    }

    public BigDecimal getActualPayAgentAmount() {
        return actualPayAgentAmount;
    }

    public void setActualPayAgentAmount(BigDecimal actualPayAgentAmount) {
        this.actualPayAgentAmount = actualPayAgentAmount;
    }

    public BigDecimal getActualPaySuperAgentAmount() {
        return actualPaySuperAgentAmount;
    }

    public void setActualPaySuperAgentAmount(BigDecimal actualPaySuperAgentAmount) {
        this.actualPaySuperAgentAmount = actualPaySuperAgentAmount;
    }

    public BigDecimal getActualPaySupplieAmount() {
        return actualPaySupplieAmount;
    }

    public void setActualPaySupplieAmount(BigDecimal actualPaySupplieAmount) {
        this.actualPaySupplieAmount = actualPaySupplieAmount;
    }

    public BigDecimal getActualPayUserRefundAmount() {
        return actualPayUserRefundAmount;
    }

    public void setActualPayUserRefundAmount(BigDecimal actualPayUserRefundAmount) {
        this.actualPayUserRefundAmount = actualPayUserRefundAmount;
    }

    public BigDecimal getNeedPayUserRefundAmount() {
        return needPayUserRefundAmount;
    }

    public void setNeedPayUserRefundAmount(BigDecimal needPayUserRefundAmount) {
        this.needPayUserRefundAmount = needPayUserRefundAmount;
    }

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTourist() {
		return tourist;
	}

	public void setTourist(String tourist) {
		this.tourist = tourist;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getTouristInfo() {
		return touristInfo;
	}

	public void setTouristInfo(String touristInfo) {
		this.touristInfo = touristInfo;
	}

	public String getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPurchasePriceStatus() {
		return purchasePriceStatus;
	}

	public void setPurchasePriceStatus(String purchasePriceStatus) {
		this.purchasePriceStatus = purchasePriceStatus;
	}
	

}
