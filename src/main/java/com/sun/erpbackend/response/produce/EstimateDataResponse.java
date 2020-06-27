package com.sun.erpbackend.response.produce;

public class EstimateDataResponse {
	private int code;
	private String message;
	private float successRate;
	private int currentMaterial;
	private int currentBill;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public float getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(float successRate) {
		this.successRate = successRate;
	}
	public int getCurrentMaterial() {
		return currentMaterial;
	}
	public void setCurrentMaterial(int currentMaterial) {
		this.currentMaterial = currentMaterial;
	}
	public int getCurrentBill() {
		return currentBill;
	}
	public void setCurrentBill(int currentBill) {
		this.currentBill = currentBill;
	}
	
	
}
