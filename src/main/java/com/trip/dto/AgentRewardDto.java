package com.trip.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AgentRewardDto extends BaseDto{

    private Long agentId;
    private String agentName;
    private Long subAgentId;
    private String subAgentName;
    private Long orderId;
    private String rewardReason;
    private String payAgentStatus;
    private String paySuperAgentStatus;
    private BigDecimal rewardAmount;
    
    private String username;
    private String avatarUrl;
    
    private String agentType;
    private String agentTypeName;
    private String nickName;
    
    private String rewardType; 				// 收益类型：1:订单 2：下级返佣 3:推荐奖励
    private String rewardTypeName;			// 收益类型名
    private String extraType;				// 额外收益类型：1:vip 2:创享
    private String estraTypeName;			// 额外收益类型名
    private BigDecimal rewardExtraAmount;	// 额外收益值
    private String pageAgentStatusName;		// 付款代理状态名
    private String orderYn;					// 是否是订单导入状态 1:是 0:否
    private String useYn;					// 是否使用 0:否 1:是


    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRewardReason() {
        return rewardReason;
    }

    public void setRewardReason(String rewardReason) {
        this.rewardReason = rewardReason;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getSubAgentId() {
        return subAgentId;
    }

    public void setSubAgentId(Long subAgentId) {
        this.subAgentId = subAgentId;
    }

    public String getSubAgentName() {
        return subAgentName;
    }

    public void setSubAgentName(String subAgentName) {
        this.subAgentName = subAgentName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getAgentTypeName() {
		return agentTypeName;
	}

	public void setAgentTypeName(String agentTypeName) {
		this.agentTypeName = agentTypeName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getRewardTypeName() {
		return rewardTypeName;
	}

	public void setRewardTypeName(String rewardTypeName) {
		this.rewardTypeName = rewardTypeName;
	}

	public String getExtraType() {
		return extraType;
	}

	public void setExtraType(String extraType) {
		this.extraType = extraType;
	}

	public String getEstraTypeName() {
		return estraTypeName;
	}

	public void setEstraTypeName(String estraTypeName) {
		this.estraTypeName = estraTypeName;
	}

	public BigDecimal getRewardExtraAmount() {
		return rewardExtraAmount;
	}

	public void setRewardExtraAmount(BigDecimal rewardExtraAmount) {
		this.rewardExtraAmount = rewardExtraAmount;
	}

	public String getPageAgentStatusName() {
		return pageAgentStatusName;
	}

	public void setPageAgentStatusName(String pageAgentStatusName) {
		this.pageAgentStatusName = pageAgentStatusName;
	}

	public String getOrderYn() {
		return orderYn;
	}

	public void setOrderYn(String orderYn) {
		this.orderYn = orderYn;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
}
