package com.trip.entity;

import com.trip.dto.BookDetailDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author Vincent.Pei
 * @Description:
 * @date 2018/7/8下午7:03
 */
public class AgentIncomeGson{

    private String day;
    private String tourist;
    private Long orderId;
    private String productName;
    private String subAgent;
    private BigDecimal orderIncome;
    private String touristPhone;
    private Date tripCreateStartTime;
    private String buyInsuranceStatus;
    private String contractSignStatus;
    private String tripNotifyStatus;

    private String payAgentStatus     ;//代理返佣状态
    private String paySuperAgentStatus;//上级代理返佣状态

    private BigDecimal needPayUserRefundAmount;//应退款金额
    private BigDecimal actualPayUserRefundAmount;//实际退款金额
    private Long id;	// 收益流水号
    private String rewardType;	//收益类型 1:订单 2:下级返佣 3：推荐奖励
    private BigDecimal rewardAmount;	// 代理收益
    private BigDecimal extraRewardAmount;	// 代理额外收益
    private String extraType;	// 代理额外收益类型


    private List<BookDetailDto> bookingList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTourist() {
        return tourist;
    }

    public void setTourist(String tourist) {
        this.tourist = tourist;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubAgent() {
        return subAgent;
    }

    public void setSubAgent(String subAgent) {
        this.subAgent = subAgent;
    }

    public BigDecimal getOrderIncome() {
        return orderIncome;
    }

    public void setOrderIncome(BigDecimal orderIncome) {
        this.orderIncome = orderIncome;
    }

    public String getTouristPhone() {
        return touristPhone;
    }

    public void setTouristPhone(String touristPhone) {
        this.touristPhone = touristPhone;
    }

    public Date getTripCreateStartTime() {
        return tripCreateStartTime;
    }

    public void setTripCreateStartTime(Date tripCreateStartTime) {
        this.tripCreateStartTime = tripCreateStartTime;
    }

    public String getBuyInsuranceStatus() {
        return buyInsuranceStatus;
    }

    public void setBuyInsuranceStatus(String buyInsuranceStatus) {
        this.buyInsuranceStatus = buyInsuranceStatus;
    }

    public String getContractSignStatus() {
        return contractSignStatus;
    }

    public void setContractSignStatus(String contractSignStatus) {
        this.contractSignStatus = contractSignStatus;
    }

    public String getTripNotifyStatus() {
        return tripNotifyStatus;
    }

    public void setTripNotifyStatus(String tripNotifyStatus) {
        this.tripNotifyStatus = tripNotifyStatus;
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

    public BigDecimal getNeedPayUserRefundAmount() {
        return needPayUserRefundAmount;
    }

    public void setNeedPayUserRefundAmount(BigDecimal needPayUserRefundAmount) {
        this.needPayUserRefundAmount = needPayUserRefundAmount;
    }

    public BigDecimal getActualPayUserRefundAmount() {
        return actualPayUserRefundAmount;
    }

    public void setActualPayUserRefundAmount(BigDecimal actualPayUserRefundAmount) {
        this.actualPayUserRefundAmount = actualPayUserRefundAmount;
    }

    public List<BookDetailDto> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookDetailDto> bookingList) {
        this.bookingList = bookingList;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public BigDecimal getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(BigDecimal rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public BigDecimal getExtraRewardAmount() {
		return extraRewardAmount;
	}

	public void setExtraRewardAmount(BigDecimal extraRewardAmount) {
		this.extraRewardAmount = extraRewardAmount;
	}

	public String getExtraType() {
		return extraType;
	}

	public void setExtraType(String extraType) {
		this.extraType = extraType;
	}
}
