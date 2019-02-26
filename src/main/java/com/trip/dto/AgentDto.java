package com.trip.dto;

public class AgentDto extends BaseDto{

    private String agentName;
    private Long superAgent;
    private String superAgentName;
    private String subAgent;
    private String status;
    private String payeeAccount;
    private String payeeBank;
    private String payeeName;
    private String payeePhone;
    private String agentType;
    private String agentTypeName;
    private String vipRemark;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getSuperAgent() {
        return superAgent;
    }

    public void setSuperAgent(Long superAgent) {
        this.superAgent = superAgent;
    }

    public String getSubAgent() {
        return subAgent;
    }

    public void setSubAgent(String subAgent) {
        this.subAgent = subAgent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuperAgentName() {
        return superAgentName;
    }

    public void setSuperAgentName(String superAgentName) {
        this.superAgentName = superAgentName;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getPayeeBank() {
        return payeeBank;
    }

    public void setPayeeBank(String payeeBank) {
        this.payeeBank = payeeBank;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeePhone() {
        return payeePhone;
    }

    public void setPayeePhone(String payeePhone) {
        this.payeePhone = payeePhone;
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

	public String getVipRemark() {
		return vipRemark;
	}

	public void setVipRemark(String vipRemark) {
		this.vipRemark = vipRemark;
	}

}
