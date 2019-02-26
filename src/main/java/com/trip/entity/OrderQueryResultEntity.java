package com.trip.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.trip.dto.BaseDto;
import com.trip.dto.BookDetailDto;
import com.trip.dto.SettlementDetailDto;

public class OrderQueryResultEntity<T> extends BaseDto{

    private Date tripCreateStartTime    ;
    private Date departureTime           ;
    private String productName            ;
    private Long supplierId            ;
    private String supplierName            ;
    private String salesman              ;
    private String touristPhone          ;
    private String agentName               ;
    private String superAgentName               ;
    private String tripNotifyStatus      ;
    private String contractSignStatus    ;
    private String buyInsuranceStatus    ;
    // add 20180801 bug6 增加出游人姓名 start
    private String tourist;		// 出游人姓名
    // add 20180801 bug6 增加出游人姓名 end
    
    // add 20180802 bug15 查询字段增加付款方式 start
    private String payType;	// 付款方式
    // add 20180802 bug15 查询字段增加付款方式 end
    
    // add 20180801 bug13，bug14 增加订单状态字段 start
    private String orderStatus;		// 订单状态 1:正常 2:取消 3.退款取消
    // add 20180801 bug13，bug14 增加订单状态字段 end
    // add 20180802 bug23 查询/列表字段增加出游类型，付款供应商状态 start
    private Long tripType;	// 出游类型
    private String tripTypeName; // 出游类型名
    private String paySupplierStatus;	// 付款供应商状态
    // add 20180802 bug23 查询/列表字段增加出游类型，付款供应商状态 end
    // add 20180811 新增功能9 查询/列表增加出游人信息 start
    private String touristInfo;	// 出游人信息
    // add 20180811 新增功能9 查询/列表增加出游人信息 end

    private BigDecimal adultPurchasePrice;//成人价
    private BigDecimal childrenPurchasePrice;//儿童价
    private BigDecimal babyPurchasePrice;//婴儿价
    private BigDecimal singleRoomBalance;//单房差
    private BigDecimal plusFee;//附加费
    private BigDecimal visaFee;//签证费
    private BigDecimal insuranceFee;//保险费
    
    // add by 20180812 新增功能20，优化21 start
    private BuyInfoFormulGson buyInfoFinalFormula;
    private List<BookingDetailGson> buyInfoFinal;
    // add by 20180812 新增功能20，优化21 end
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
    private String depositStatusName;	// 定金收款状态名
    private String settlementPriceSaveYn;	// 结算价是否保存 0:未保存 1:保存
    // add 20180814 新增功能 订单产品调整-增加定金收款状态 start
    
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
    // 付款方式
    private String payTypeName;

    // receiptAmount
    private BigDecimal rAReceivable       ;
    private BigDecimal rAActualReceipt        ;
    private BigDecimal rASurplus   ;
    private String rAStatus     ;
    private BigDecimal rADeposit;	// 应收定金

    // agentAmount
    private BigDecimal aAReceivable       ;
    private BigDecimal aAActualReceipt        ;
    private BigDecimal aASurplus   ;
    private String aAStatus     ;

    // superAgentName
    private BigDecimal sAAReceivable       ;
    private BigDecimal sAAActualReceipt        ;
    private BigDecimal sAASurplus           ;
    private String sAAStatus     ;

    // supplierAmount
    private BigDecimal sAReceivable       ;
    private BigDecimal sAActualReceipt        ;
    private BigDecimal sASurplus           ;
    private String sAStatus     ;

    // refundAmount
    private BigDecimal rFAReceivable       ;
    private BigDecimal rFAActualReceipt        ;
    private BigDecimal rFASurplus           ;
    private String rFAStatus     ;
    private String productTypeName;	// 产品类型名

    private Integer version;
    
    private List<BookDetailDto> bookingList;
    
    // 增加结算价列表
    private List<SettlementDetailDto> verifyBill;

    public Date getTripCreateStartTime() {
        return tripCreateStartTime;
    }

    public void setTripCreateStartTime(Date tripCreateStartTime) {
        this.tripCreateStartTime = tripCreateStartTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public BigDecimal getrAReceivable() {
        return rAReceivable;
    }

    public void setrAReceivable(BigDecimal rAReceivable) {
        this.rAReceivable = rAReceivable;
    }

    public BigDecimal getrAActualReceipt() {
        return rAActualReceipt;
    }

    public void setrAActualReceipt(BigDecimal rAActualReceipt) {
        this.rAActualReceipt = rAActualReceipt;
    }

    public BigDecimal getrASurplus() {
        return rASurplus;
    }

    public void setrASurplus(BigDecimal rASurplus) {
        this.rASurplus = rASurplus;
    }

    public String getrAStatus() {
        return rAStatus;
    }

    public void setrAStatus(String rAStatus) {
        this.rAStatus = rAStatus;
    }

    public BigDecimal getaAReceivable() {
        return aAReceivable;
    }

    public void setaAReceivable(BigDecimal aAReceivable) {
        this.aAReceivable = aAReceivable;
    }

    public BigDecimal getaAActualReceipt() {
        return aAActualReceipt;
    }

    public void setaAActualReceipt(BigDecimal aAActualReceipt) {
        this.aAActualReceipt = aAActualReceipt;
    }

    public BigDecimal getaASurplus() {
        return aASurplus;
    }

    public void setaASurplus(BigDecimal aASurplus) {
        this.aASurplus = aASurplus;
    }

    public String getaAStatus() {
        return aAStatus;
    }

    public void setaAStatus(String aAStatus) {
        this.aAStatus = aAStatus;
    }

    public BigDecimal getsAAReceivable() {
        return sAAReceivable;
    }

    public void setsAAReceivable(BigDecimal sAAReceivable) {
        this.sAAReceivable = sAAReceivable;
    }

    public BigDecimal getsAAActualReceipt() {
        return sAAActualReceipt;
    }

    public void setsAAActualReceipt(BigDecimal sAAActualReceipt) {
        this.sAAActualReceipt = sAAActualReceipt;
    }

    public BigDecimal getsAASurplus() {
        return sAASurplus;
    }

    public void setsAASurplus(BigDecimal sAASurplus) {
        this.sAASurplus = sAASurplus;
    }

    public String getsAAStatus() {
        return sAAStatus;
    }

    public void setsAAStatus(String sAAStatus) {
        this.sAAStatus = sAAStatus;
    }

    public BigDecimal getsAReceivable() {
        return sAReceivable;
    }

    public void setsAReceivable(BigDecimal sAReceivable) {
        this.sAReceivable = sAReceivable;
    }

    public BigDecimal getsAActualReceipt() {
        return sAActualReceipt;
    }

    public void setsAActualReceipt(BigDecimal sAActualReceipt) {
        this.sAActualReceipt = sAActualReceipt;
    }

    public BigDecimal getsASurplus() {
        return sASurplus;
    }

    public void setsASurplus(BigDecimal sASurplus) {
        this.sASurplus = sASurplus;
    }

    public String getsAStatus() {
        return sAStatus;
    }

    public void setsAStatus(String sAStatus) {
        this.sAStatus = sAStatus;
    }

    public BigDecimal getrFAReceivable() {
        return rFAReceivable;
    }

    public void setrFAReceivable(BigDecimal rFAReceivable) {
        this.rFAReceivable = rFAReceivable;
    }

    public BigDecimal getrFAActualReceipt() {
        return rFAActualReceipt;
    }

    public void setrFAActualReceipt(BigDecimal rFAActualReceipt) {
        this.rFAActualReceipt = rFAActualReceipt;
    }

    public BigDecimal getrFASurplus() {
        return rFASurplus;
    }

    public void setrFASurplus(BigDecimal rFASurplus) {
        this.rFASurplus = rFASurplus;
    }

    public String getrFAStatus() {
        return rFAStatus;
    }

    public void setrFAStatus(String rFAStatus) {
        this.rFAStatus = rFAStatus;
    }

    public BigDecimal getAdultPurchasePrice() {
        return adultPurchasePrice;
    }

    public void setAdultPurchasePrice(BigDecimal adultPurchasePrice) {
        this.adultPurchasePrice = adultPurchasePrice;
    }

    public BigDecimal getChildrenPurchasePrice() {
        return childrenPurchasePrice;
    }

    public void setChildrenPurchasePrice(BigDecimal childrenPurchasePrice) {
        this.childrenPurchasePrice = childrenPurchasePrice;
    }

    public BigDecimal getBabyPurchasePrice() {
        return babyPurchasePrice;
    }

    public void setBabyPurchasePrice(BigDecimal babyPurchasePrice) {
        this.babyPurchasePrice = babyPurchasePrice;
    }

    public BigDecimal getSingleRoomBalance() {
        return singleRoomBalance;
    }

    public void setSingleRoomBalance(BigDecimal singleRoomBalance) {
        this.singleRoomBalance = singleRoomBalance;
    }

    public BigDecimal getPlusFee() {
        return plusFee;
    }

    public void setPlusFee(BigDecimal plusFee) {
        this.plusFee = plusFee;
    }

    public BigDecimal getVisaFee() {
        return visaFee;
    }

    public void setVisaFee(BigDecimal visaFee) {
        this.visaFee = visaFee;
    }

    public BigDecimal getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<BookDetailDto> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookDetailDto> bookingList) {
        this.bookingList = bookingList;
    }

	public String getTourist() {
		return tourist;
	}

	public void setTourist(String tourist) {
		this.tourist = tourist;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getTripType() {
		return tripType;
	}

	public void setTripType(Long tripType) {
		this.tripType = tripType;
	}

	public String getPaySupplierStatus() {
		return paySupplierStatus;
	}

	public void setPaySupplierStatus(String paySupplierStatus) {
		this.paySupplierStatus = paySupplierStatus;
	}

	public String getTripTypeName() {
		return tripTypeName;
	}

	public void setTripTypeName(String tripTypeName) {
		this.tripTypeName = tripTypeName;
	}

	public String getTouristInfo() {
		return touristInfo;
	}

	public void setTouristInfo(String touristInfo) {
		this.touristInfo = touristInfo;
	}

	public BuyInfoFormulGson getBuyInfoFinalFormula() {
		return buyInfoFinalFormula;
	}

	public void setBuyInfoFinalFormula(BuyInfoFormulGson buyInfoFinalFormula) {
		this.buyInfoFinalFormula = buyInfoFinalFormula;
	}

	public List<BookingDetailGson> getBuyInfoFinal() {
		return buyInfoFinal;
	}

	public void setBuyInfoFinal(List<BookingDetailGson> buyInfoFinal) {
		this.buyInfoFinal = buyInfoFinal;
	}

	public BigDecimal getAdultSettlementPrice() {
		return adultSettlementPrice;
	}

	public void setAdultSettlementPrice(BigDecimal adultSettlementPrice) {
		this.adultSettlementPrice = adultSettlementPrice;
	}

	public BigDecimal getChildrenSettlementPrice() {
		return childrenSettlementPrice;
	}

	public void setChildrenSettlementPrice(BigDecimal childrenSettlementPrice) {
		this.childrenSettlementPrice = childrenSettlementPrice;
	}

	public BigDecimal getBabySettlementPrice() {
		return babySettlementPrice;
	}

	public void setBabySettlementPrice(BigDecimal babySettlementPrice) {
		this.babySettlementPrice = babySettlementPrice;
	}

	public BigDecimal getSingleRoomBalanceSettlementPrice() {
		return singleRoomBalanceSettlementPrice;
	}

	public void setSingleRoomBalanceSettlementPrice(BigDecimal singleRoomBalanceSettlementPrice) {
		this.singleRoomBalanceSettlementPrice = singleRoomBalanceSettlementPrice;
	}

	public BigDecimal getPlusFeeSettlementPrice() {
		return plusFeeSettlementPrice;
	}

	public void setPlusFeeSettlementPrice(BigDecimal plusFeeSettlementPrice) {
		this.plusFeeSettlementPrice = plusFeeSettlementPrice;
	}

	public BigDecimal getVisaFeeSettlementPrice() {
		return visaFeeSettlementPrice;
	}

	public void setVisaFeeSettlementPrice(BigDecimal visaFeeSettlementPrice) {
		this.visaFeeSettlementPrice = visaFeeSettlementPrice;
	}

	public BigDecimal getInsuranceFeeSettlementPrice() {
		return insuranceFeeSettlementPrice;
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
		return depositAmount;
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

	public BigDecimal getrADeposit() {
		return rADeposit;
	}

	public void setrADeposit(BigDecimal rADeposit) {
		this.rADeposit = rADeposit;
	}

	public String getSettlementPriceSaveYn() {
		return settlementPriceSaveYn;
	}

	public void setSettlementPriceSaveYn(String settlementPriceSaveYn) {
		this.settlementPriceSaveYn = settlementPriceSaveYn;
	}

	public List<SettlementDetailDto> getVerifyBill() {
		return verifyBill;
	}

	public void setVerifyBill(List<SettlementDetailDto> verifyBill) {
		this.verifyBill = verifyBill;
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

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
}

