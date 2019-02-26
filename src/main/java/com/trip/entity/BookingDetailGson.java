package com.trip.entity;

import com.trip.dto.OrderDto;

import java.math.BigDecimal;

public class BookingDetailGson {


    private String type;
    private BigDecimal adultPurchasePrice;//成人价
    private BigDecimal childrenPurchasePrice;//儿童价
    private BigDecimal babyPurchasePrice;//婴儿价
    private BigDecimal singleRoomBalance;//单房差
    private BigDecimal plusFee;//附加费
    private BigDecimal visaFee;//签证费
    private BigDecimal insuranceFee;//保险费

    private BigDecimal linesProductTotal;//产品线总金额
    private BigDecimal totalPrice;//产品线总金额

    // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
    private BigDecimal adultSalePrice;  				// 成人销价
    private Integer adultBuyAmount;						// 成人购买数量 
    private BigDecimal childrenSalePrice;				// 儿童单价
    private Integer childrenBuyAmount;					// 儿童购买数量
    private BigDecimal babySalePrice;					// 婴儿单价
    private Integer babyBuyAmount;						// 婴儿购买数量
    private BigDecimal singleRoomBalanceSalePrice;		// 单房差单价
    private Integer singleRoomBalanceBuyAmount;			// 单房差购买数量
    private BigDecimal plusFeeSalePrice;				// 附加费单价
    private Integer plusFeeBuyAmount;					// 附加费购买数量
    private BigDecimal visaFeeSalePrice;				// 签证费单价
    private Integer visaFeeBuyAmount;					// 签证购买数量
    private BigDecimal insuranceFeeSalePrice;			// 保险费单价
    private Integer insuranceFeeBuyAmount;				// 保险费购买数量
    // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end
    // add by 20180814 增加结算单价*数量 以及结算节 start
    /*
    private BigDecimal adultSettlementPrice;				// 成人结算单价
    private BigDecimal childrenSettlementPrice;				// 儿童结算单价
    private BigDecimal babySettlementPrice;					// 婴儿结算单价
    private BigDecimal singleRoomBalanceSettlementPrice;	// 单房差结算单价
    private BigDecimal plusFeeSettlementPrice;				// 附加费结算单价
    private BigDecimal visaFeeSettlementPrice;				// 签证费结算单价
    private BigDecimal insuranceFeeSettlementPrice;			// 保险费结算单价
    
    private BigDecimal adultSettlementAmount;			// 成人结算价
    private BigDecimal childrenSettlementAmount;		// 儿童结算价
    private BigDecimal babySettlementAmount;			// 婴儿结算价
    private BigDecimal singleRoomBalanceSettlementAmount;	// 单房差结算价
    private BigDecimal plusFeeSettlementAmount;			// 附加费结算价
    private BigDecimal visaFeeSettlementAmount;			// 签证费结算价
    private BigDecimal insuranceFeeSettlementAmount;	// 保险费结算价
    */
    // add by 20180814 增加结算单价*数量 以及结算节 end


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public BigDecimal getLinesProductTotal() {
        return linesProductTotal;
    }

    public void setLinesProductTotal(BigDecimal linesProductTotal) {
        this.linesProductTotal = linesProductTotal;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

	public BigDecimal getAdultSalePrice() {
		return adultSalePrice;
	}

	public void setAdultSalePrice(BigDecimal adultSalePrice) {
		this.adultSalePrice = adultSalePrice;
	}

	public Integer getAdultBuyAmount() {
		return adultBuyAmount;
	}

	public void setAdultBuyAmount(Integer adultBuyAmount) {
		this.adultBuyAmount = adultBuyAmount;
	}

	public BigDecimal getChildrenSalePrice() {
		return childrenSalePrice;
	}

	public void setChildrenSalePrice(BigDecimal childrenSalePrice) {
		this.childrenSalePrice = childrenSalePrice;
	}

	public Integer getChildrenBuyAmount() {
		return childrenBuyAmount;
	}

	public void setChildrenBuyAmount(Integer childrenBuyAmount) {
		this.childrenBuyAmount = childrenBuyAmount;
	}

	public BigDecimal getBabySalePrice() {
		return babySalePrice;
	}

	public void setBabySalePrice(BigDecimal babySalePrice) {
		this.babySalePrice = babySalePrice;
	}

	public Integer getBabyBuyAmount() {
		return babyBuyAmount;
	}

	public void setBabyBuyAmount(Integer babyBuyAmount) {
		this.babyBuyAmount = babyBuyAmount;
	}


	public BigDecimal getSingleRoomBalanceSalePrice() {
		return singleRoomBalanceSalePrice;
	}

	public void setSingleRoomBalanceSalePrice(BigDecimal singleRoomBalanceSalePrice) {
		this.singleRoomBalanceSalePrice = singleRoomBalanceSalePrice;
	}

	public Integer getSingleRoomBalanceBuyAmount() {
		return singleRoomBalanceBuyAmount;
	}

	public void setSingleRoomBalanceBuyAmount(Integer singleRoomBalanceBuyAmount) {
		this.singleRoomBalanceBuyAmount = singleRoomBalanceBuyAmount;
	}

	public BigDecimal getPlusFeeSalePrice() {
		return plusFeeSalePrice;
	}

	public void setPlusFeeSalePrice(BigDecimal plusFeeSalePrice) {
		this.plusFeeSalePrice = plusFeeSalePrice;
	}

	public Integer getPlusFeeBuyAmount() {
		return plusFeeBuyAmount;
	}

	public void setPlusFeeBuyAmount(Integer plusFeeBuyAmount) {
		this.plusFeeBuyAmount = plusFeeBuyAmount;
	}

	public BigDecimal getVisaFeeSalePrice() {
		return visaFeeSalePrice;
	}

	public void setVisaFeeSalePrice(BigDecimal visaFeeSalePrice) {
		this.visaFeeSalePrice = visaFeeSalePrice;
	}

	public Integer getVisaFeeBuyAmount() {
		return visaFeeBuyAmount;
	}

	public void setVisaFeeBuyAmount(Integer visaFeeBuyAmount) {
		this.visaFeeBuyAmount = visaFeeBuyAmount;
	}

	public BigDecimal getInsuranceFeeSalePrice() {
		return insuranceFeeSalePrice;
	}

	public void setInsuranceFeeSalePrice(BigDecimal insuranceFeeSalePrice) {
		this.insuranceFeeSalePrice = insuranceFeeSalePrice;
	}

	public Integer getInsuranceFeeBuyAmount() {
		return insuranceFeeBuyAmount;
	}

	public void setInsuranceFeeBuyAmount(Integer insuranceFeeBuyAmount) {
		this.insuranceFeeBuyAmount = insuranceFeeBuyAmount;
	}

	/*
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

	public BigDecimal getAdultSettlementAmount() {
		return adultSettlementAmount;
	}

	public void setAdultSettlementAmount(BigDecimal adultSettlementAmount) {
		this.adultSettlementAmount = adultSettlementAmount;
	}

	public BigDecimal getChildrenSettlementAmount() {
		return childrenSettlementAmount;
	}

	public void setChildrenSettlementAmount(BigDecimal childrenSettlementAmount) {
		this.childrenSettlementAmount = childrenSettlementAmount;
	}


	public BigDecimal getBabySettlementAmount() {
		return babySettlementAmount;
	}

	public void setBabySettlementAmount(BigDecimal babySettlementAmount) {
		this.babySettlementAmount = babySettlementAmount;
	}


	public BigDecimal getSingleRoomBalanceSettlementAmount() {
		return singleRoomBalanceSettlementAmount;
	}

	public void setSingleRoomBalanceSettlementAmount(BigDecimal singleRoomBalanceSettlementAmount) {
		this.singleRoomBalanceSettlementAmount = singleRoomBalanceSettlementAmount;
	}

	public BigDecimal getPlusFeeSettlementAmount() {
		return plusFeeSettlementAmount;
	}

	public void setPlusFeeSettlementAmount(BigDecimal plusFeeSettlementAmount) {
		this.plusFeeSettlementAmount = plusFeeSettlementAmount;
	}

	public BigDecimal getVisaFeeSettlementAmount() {
		return visaFeeSettlementAmount;
	}

	public void setVisaFeeSettlementAmount(BigDecimal visaFeeSettlementAmount) {
		this.visaFeeSettlementAmount = visaFeeSettlementAmount;
	}

	public BigDecimal getInsuranceFeeSettlementAmount() {
		return insuranceFeeSettlementAmount;
	}

	public void setInsuranceFeeSettlementAmount(BigDecimal insuranceFeeSettlementAmount) {
		this.insuranceFeeSettlementAmount = insuranceFeeSettlementAmount;
	}
	*/
}
