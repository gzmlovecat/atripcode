package com.trip.entity;

public class EncryptedDataGson {
	private String encryptedData;
	private String iv;
	private String jsCode;
	private String rawData;
	private String signature;
	private Long superAgent;
	private String telPhone;
	private String superAgentName;
	private String name;
	
	public String getEncryptedData() {
		return encryptedData;
	}
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getJsCode() {
		return jsCode;
	}
	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Long getSuperAgent() {
		return superAgent;
	}
	public void setSuperAgent(Long superAgent) {
		this.superAgent = superAgent;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getSuperAgentName() {
		return superAgentName;
	}
	public void setSuperAgentName(String superAgentName) {
		this.superAgentName = superAgentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
