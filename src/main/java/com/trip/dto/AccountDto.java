package com.trip.dto;

import org.springframework.data.annotation.Transient;

public class AccountDto<T> extends BaseDto{

    /**
     * 账户类型
     *1、管理员
     *2、员工-销售
     *3、员工-产品
     *4、员工-财务
     *5、代理
     */
    private String accountType;
    private String username;
    private String employeeName;
    private String agentName;
    private String password;
    private String status;
    private Long employeeId;
    private Long agentId;
    @Transient
    private Long superAgentId;
    @Transient
    private String superAgentName;
    @Transient
    private String payeeAccount;
    @Transient
    private String payeeBank;
    @Transient
    private String payeeName;
    @Transient
    private String payeePhone;
    
    private String JSESSIONID;
    private String openId;
    private String avatarUrl;
    // 手机
    private String telPhone;
    // 昵称
    private String nickName;
    // 姓名
    private String name;
    // 代理类型 旅游达人 vip旅游达人
    private String agentType;
    private String agentTypeName;
    // vip 描述
    private String vipRemark;
    
    private String thirdSessionId;
    // access token
    private String accessToken;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getSuperAgentName() {
        return superAgentName;
    }

    public void setSuperAgentName(String superAgentName) {
        this.superAgentName = superAgentName;
    }

    public Long getSuperAgentId() {
        return superAgentId;
    }

    public void setSuperAgentId(Long superAgentId) {
        this.superAgentId = superAgentId;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getThirdSessionId() {
		return thirdSessionId;
	}

	public void setThirdSessionId(String thirdSessionId) {
		this.thirdSessionId = thirdSessionId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public AccountDto() {}

    public AccountDto(String accountType,String username, String password, String status, Long employeeId, Long agentId) {
        this.accountType = accountType;
        this.username = username;
        this.password = password;
        this.status = status;
        this.employeeId = employeeId;
        this.agentId = agentId;
//        this.openId = openId;
    }
}
