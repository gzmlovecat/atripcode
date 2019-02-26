package com.trip.entity;

import com.trip.dto.BaseDto;

public class AgentRegisterGson extends BaseDto {

    private String agentName;
    private Long superAgent;
    private String superAgentName;
    private String subAgent;
    private String status;
    private String payeeAccount;
    private String payeeBank;
    private String payeeName;
    private String payeePhone;

    private String username;
    private String password;
    // 用js_code取得的openId、sessionKey，然后加密，返回的thirdSessionId
    private String thirdSessionId;
    private String jsCode;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getThirdSessionId() {
		return thirdSessionId;
	}

	public void setThirdSessionId(String thirdSessionId) {
		this.thirdSessionId = thirdSessionId;
	}

	public String getJsCode() {
		return jsCode;
	}

	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}
}
