package com.trip.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDto<T> extends BaseDto{

    private String productName;
    private Long departure;
    private String departureName;
    private String destination;
    private String destinationName;
    private Long productType;
    private String productTypeName;
    private Long tripType;
    private String tripTypeName;
    private Long supplier;
    private String supplierName;
    private Date departureTime;
    private Date returnTime;
    private String tourist;
    private String touristPhone;
    private Long agentId;
    private String agentName;
    private Long superAgent;
    private String superAgentName;
    private BigDecimal superAgentReturnRatio;
    private String payType;
    private Integer version;
    
    //状态信息
    private String tripNotifyStatus   ;
    private String contractSignStatus ;
    private String buyInsuranceStatus ;
    private String gatheringStatus    ;
    private String payAgentStatus     ;
    private String paySuperAgentStatus;
    private String paySupplierStatus  ;
    private String refundStatus       ;
    private String noPurchasePrice    ;

    private String salesman    ;

    private BigDecimal adultPurchasePrice;//成人价
    private BigDecimal childrenPurchasePrice;//儿童价
    private BigDecimal babyPurchasePrice;//婴儿价
    private BigDecimal singleRoomBalance;//单房差
    private BigDecimal plusFee;//附加费
    private BigDecimal visaFee;//签证费
    private BigDecimal insuranceFee;//保险费
    
    // add 20180801 bug13，bug14 增加订单状态字段 start
    private String orderStatus;		// 订单状态 1:未完成 2:完成
    // add 20180801 bug13，bug14 增加订单状态字段 end
    // add 20180811 新增功能9 查询/列表增加出游人信息 start
    private String touristInfo; // 出游人信息
    // add 20180811 新增功能9 查询/列表增加出游人信息 end
    // add 20180812 新增功能25 增加财务结算单价 start
    private BigDecimal adultSettlementPrice;				// 成人结算单价
    private BigDecimal childrenSettlementPrice;				// 儿童结算单价
    private BigDecimal babySettlementPrice;					// 婴儿结算单价
    private BigDecimal singleRoomBalanceSettlementPrice;	// 单房差结算单价
    private BigDecimal plusFeeSettlementPrice;				// 附加费结算单价
    private BigDecimal visaFeeSettlementPrice;				// 签证费结算单价
    private BigDecimal insuranceFeeSettlementPrice;			// 保险费结算单价
    // add 20180812 新增功能25 增加财务结算单价 end
    // add 20180814 新增功能 订单产品调整-增加定金收款状态 start
    private BigDecimal depositAmount;	// 定金金额
    private String depositStatus;	// 定金收款状态 0:无 1:未完成 2:完成
    private String settlementPriceSaveYn;	// 结算价是否保存 0:未保存 1:保存
    // add 20180814 新增功能 订单产品调整-增加定金收款状态 start

    /**
     * 付款信息
     */
    private BigDecimal actualReceiptAmount;//实收金额
    private BigDecimal actualPayAgentAmount;//实付代理金额
    private BigDecimal actualPaySuperAgentAmount;//实付上级代理金额
    private BigDecimal actualPaySupplieAmount;//实付供应商金额
    private BigDecimal actualPayUserRefundAmount;//实付用户退款金额

    private BigDecimal needPayUserRefundAmount;//应付用户退款金额
    private BigDecimal originSalesTotal;//初始用户应付款
    private BigDecimal finalSalesTotal;//最终用户应付款
    
    // 各个状态名
    // 出团通知状态名
    private String tripNotifyStatusName;
    // 合同签约状态名
    private String contractSignStatusName;
    // 保险购买状态名
    private String buyInsuranceStatusName;
    // 收款状态名
    private String gatheringStatusName;
    // 付款代理状态名
    private String payAgentStatusName;
    // 付款上级代理状态名
    private String paySuperAgentStatusName;
    // 付款供应商状态名
    private String paySupplierStatusName;
    // 付款用户退款/赔偿状态名
    private String refundStatusName;
    // 订单状态名
    private String orderStatusName;
    // 定金收款状态名
    private String depositStatusName;
    // 付款方式
    private String payTypeName;
    // 上级代理额外返佣系数
    private BigDecimal superAgentExtraReturnRatio;

    private List<BookDetailDto> bookingList;
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getDeparture() {
        return departure;
    }

    public void setDeparture(Long departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Long getTripType() {
        return tripType;
    }

    public void setTripType(Long tripType) {
        this.tripType = tripType;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getTourist() {
        return tourist;
    }

    public void setTourist(String tourist) {
        this.tourist = tourist;
    }

    public String getTouristPhone() {
        return touristPhone;
    }

    public void setTouristPhone(String touristPhone) {
        this.touristPhone = touristPhone;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getSuperAgent() {
        return superAgent;
    }

    public void setSuperAgent(Long superAgent) {
        this.superAgent = superAgent;
    }

    public BigDecimal getSuperAgentReturnRatio() {
        return  superAgentReturnRatio==null?BigDecimal.ZERO:superAgentReturnRatio;
    }

    public void setSuperAgentReturnRatio(BigDecimal superAgentReturnRatio) {
        this.superAgentReturnRatio = superAgentReturnRatio;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public List<BookDetailDto> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookDetailDto> bookingList) {
        this.bookingList = bookingList;
    }

    public String getDepartureName() {
        return departureName;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getTripTypeName() {
        return tripTypeName;
    }

    public void setTripTypeName(String tripTypeName) {
        this.tripTypeName = tripTypeName;
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

    public String getSuperAgentName() {
        return superAgentName;
    }

    public void setSuperAgentName(String superAgentName) {
        this.superAgentName = superAgentName;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public BigDecimal getAdultPurchasePrice() {
        return adultPurchasePrice==null?BigDecimal.ZERO:adultPurchasePrice;
    }

    public void setAdultPurchasePrice(BigDecimal adultPurchasePrice) {
        this.adultPurchasePrice = adultPurchasePrice;
    }

    public BigDecimal getChildrenPurchasePrice() {
        return  childrenPurchasePrice==null?BigDecimal.ZERO:childrenPurchasePrice;
    }

    public void setChildrenPurchasePrice(BigDecimal childrenPurchasePrice) {
        this.childrenPurchasePrice = childrenPurchasePrice;
    }

    public BigDecimal getBabyPurchasePrice() {
        return  babyPurchasePrice==null?BigDecimal.ZERO:babyPurchasePrice;
    }

    public void setBabyPurchasePrice(BigDecimal babyPurchasePrice) {
        this.babyPurchasePrice = babyPurchasePrice;
    }

    public BigDecimal getSingleRoomBalance() {
        return  singleRoomBalance==null?BigDecimal.ZERO:singleRoomBalance;
    }

    public void setSingleRoomBalance(BigDecimal singleRoomBalance) {
        this.singleRoomBalance = singleRoomBalance;
    }

    public BigDecimal getPlusFee() {
        return  plusFee==null?BigDecimal.ZERO:plusFee;
    }

    public void setPlusFee(BigDecimal plusFee) {
        this.plusFee = plusFee;
    }

    public BigDecimal getVisaFee() {
        return  visaFee==null?BigDecimal.ZERO:visaFee;
    }

    public void setVisaFee(BigDecimal visaFee) {
        this.visaFee = visaFee;
    }

    public BigDecimal getInsuranceFee() {
        return  insuranceFee==null?BigDecimal.ZERO:insuranceFee;
    }

    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public BigDecimal getActualReceiptAmount() {
        return  actualReceiptAmount==null?BigDecimal.ZERO:actualReceiptAmount;
    }

    public void setActualReceiptAmount(BigDecimal actualReceiptAmount) {
        this.actualReceiptAmount = actualReceiptAmount;
    }

    public BigDecimal getActualPayAgentAmount() {
        return  actualPayAgentAmount==null?BigDecimal.ZERO:actualPayAgentAmount;
    }

    public void setActualPayAgentAmount(BigDecimal actualPayAgentAmount) {
        this.actualPayAgentAmount = actualPayAgentAmount;
    }

    public BigDecimal getActualPaySuperAgentAmount() {
        return  actualPaySuperAgentAmount==null?BigDecimal.ZERO:actualPaySuperAgentAmount;
    }

    public void setActualPaySuperAgentAmount(BigDecimal actualPaySuperAgentAmount) {
        this.actualPaySuperAgentAmount = actualPaySuperAgentAmount;
    }

    public BigDecimal getActualPaySupplieAmount() {
        return  actualPaySupplieAmount==null?BigDecimal.ZERO:actualPaySupplieAmount;
    }

    public void setActualPaySupplieAmount(BigDecimal actualPaySupplieAmount) {
        this.actualPaySupplieAmount = actualPaySupplieAmount;
    }

    public BigDecimal getActualPayUserRefundAmount() {
        return  actualPayUserRefundAmount==null?BigDecimal.ZERO:actualPayUserRefundAmount;
    }

    public void setActualPayUserRefundAmount(BigDecimal actualPayUserRefundAmount) {
        this.actualPayUserRefundAmount = actualPayUserRefundAmount;
    }

    public BigDecimal getNeedPayUserRefundAmount() {
        return needPayUserRefundAmount==null?BigDecimal.ZERO:needPayUserRefundAmount;
    }

    public void setNeedPayUserRefundAmount(BigDecimal needPayUserRefundAmount) {
        this.needPayUserRefundAmount = needPayUserRefundAmount;
    }

    public BigDecimal getOriginSalesTotal() {
        return originSalesTotal==null?BigDecimal.ZERO:originSalesTotal;
    }

    public void setOriginSalesTotal(BigDecimal originSalesTotal) {
        this.originSalesTotal = originSalesTotal;
    }

    public BigDecimal getFinalSalesTotal() {
        return finalSalesTotal==null?BigDecimal.ZERO:finalSalesTotal;
    }

    public void setFinalSalesTotal(BigDecimal finalSalesTotal) {
        this.finalSalesTotal = finalSalesTotal;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTouristInfo() {
		return touristInfo;
	}

	public void setTouristInfo(String touristInfo) {
		this.touristInfo = touristInfo;
	}

	public BigDecimal getAdultSettlementPrice() {
		return adultSettlementPrice==null?BigDecimal.ZERO:adultSettlementPrice;
	}

	public void setAdultSettlementPrice(BigDecimal adultSettlementPrice) {
		this.adultSettlementPrice = adultSettlementPrice;
	}

	public BigDecimal getChildrenSettlementPrice() {
		return childrenSettlementPrice==null?BigDecimal.ZERO:childrenSettlementPrice;
	}

	public void setChildrenSettlementPrice(BigDecimal childrenSettlementPrice) {
		this.childrenSettlementPrice = childrenSettlementPrice;
	}

	public BigDecimal getBabySettlementPrice() {
		return babySettlementPrice==null?BigDecimal.ZERO:babySettlementPrice;
	}

	public void setBabySettlementPrice(BigDecimal babySettlementPrice) {
		this.babySettlementPrice = babySettlementPrice;
	}

	public BigDecimal getSingleRoomBalanceSettlementPrice() {
		return singleRoomBalanceSettlementPrice==null?BigDecimal.ZERO:singleRoomBalanceSettlementPrice;
	}

	public void setSingleRoomBalanceSettlementPrice(BigDecimal singleRoomBalanceSettlementPrice) {
		this.singleRoomBalanceSettlementPrice = singleRoomBalanceSettlementPrice;
	}

	public BigDecimal getPlusFeeSettlementPrice() {
		return plusFeeSettlementPrice==null?BigDecimal.ZERO:plusFeeSettlementPrice;
	}

	public void setPlusFeeSettlementPrice(BigDecimal plusFeeSettlementPrice) {
		this.plusFeeSettlementPrice = plusFeeSettlementPrice;
	}

	public BigDecimal getVisaFeeSettlementPrice() {
		return visaFeeSettlementPrice==null?BigDecimal.ZERO:visaFeeSettlementPrice;
	}

	public void setVisaFeeSettlementPrice(BigDecimal visaFeeSettlementPrice) {
		this.visaFeeSettlementPrice = visaFeeSettlementPrice;
	}

	public BigDecimal getInsuranceFeeSettlementPrice() {
		return insuranceFeeSettlementPrice==null?BigDecimal.ZERO:insuranceFeeSettlementPrice;
	}

	public void setInsuranceFeeSettlementPrice(BigDecimal insuranceFeeSettlementPrice) {
		this.insuranceFeeSettlementPrice = insuranceFeeSettlementPrice;
	}

	public String getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount==null?BigDecimal.ZERO:depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getDepositStatusName() {
		return depositStatusName;
	}

	public void setDepositStatusName(String depositStatusName) {
		this.depositStatusName = depositStatusName;
	}

	public String getSettlementPriceSaveYn() {
		return settlementPriceSaveYn;
	}

	public void setSettlementPriceSaveYn(String settlementPriceSaveYn) {
		this.settlementPriceSaveYn = settlementPriceSaveYn;
	}

	public String getTripNotifyStatusName() {
		return tripNotifyStatusName;
	}

	public void setTripNotifyStatusName(String tripNotifyStatusName) {
		this.tripNotifyStatusName = tripNotifyStatusName;
	}

	public String getContractSignStatusName() {
		return contractSignStatusName;
	}

	public void setContractSignStatusName(String contractSignStatusName) {
		this.contractSignStatusName = contractSignStatusName;
	}

	public String getBuyInsuranceStatusName() {
		return buyInsuranceStatusName;
	}

	public void setBuyInsuranceStatusName(String buyInsuranceStatusName) {
		this.buyInsuranceStatusName = buyInsuranceStatusName;
	}

	public String getGatheringStatusName() {
		return gatheringStatusName;
	}

	public void setGatheringStatusName(String gatheringStatusName) {
		this.gatheringStatusName = gatheringStatusName;
	}

	public String getPayAgentStatusName() {
		return payAgentStatusName;
	}

	public void setPayAgentStatusName(String payAgentStatusName) {
		this.payAgentStatusName = payAgentStatusName;
	}

	public String getPaySuperAgentStatusName() {
		return paySuperAgentStatusName;
	}

	public void setPaySuperAgentStatusName(String paySuperAgentStatusName) {
		this.paySuperAgentStatusName = paySuperAgentStatusName;
	}

	public String getPaySupplierStatusName() {
		return paySupplierStatusName;
	}

	public void setPaySupplierStatusName(String paySupplierStatusName) {
		this.paySupplierStatusName = paySupplierStatusName;
	}

	public String getRefundStatusName() {
		return refundStatusName;
	}

	public void setRefundStatusName(String refundStatusName) {
		this.refundStatusName = refundStatusName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public BigDecimal getSuperAgentExtraReturnRatio() {
		return superAgentExtraReturnRatio;
	}

	public void setSuperAgentExtraReturnRatio(BigDecimal superAgentExtraReturnRatio) {
		this.superAgentExtraReturnRatio = superAgentExtraReturnRatio;
	}

}
