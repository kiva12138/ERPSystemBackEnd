package com.sun.erpbackend.response.materialcurrent;

import java.util.ArrayList;
import java.util.List;

public class MaterialCurrentProduceResponse {
	private int code;
	private String message;
	private int activeMount;
	private int waitingMount;
	private List<MaterialCurrentProduceResponseSingleMaterial> waitingMaterials;
	
	public MaterialCurrentProduceResponse () {
		this.waitingMaterials = new ArrayList<MaterialCurrentProduceResponseSingleMaterial>();
	}
	
	public List<MaterialCurrentProduceResponseSingleMaterial> getWaitingMaterials() {
		return waitingMaterials;
	}


	public void setWaitingMaterials(List<MaterialCurrentProduceResponseSingleMaterial> waitingMaterials) {
		this.waitingMaterials = waitingMaterials;
	}


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
	public int getActiveMount() {
		return activeMount;
	}
	public void setActiveMount(int activeMount) {
		this.activeMount = activeMount;
	}
	public int getWaitingMount() {
		return waitingMount;
	}
	public void setWaitingMount(int waitingMount) {
		this.waitingMount = waitingMount;
	}
}
