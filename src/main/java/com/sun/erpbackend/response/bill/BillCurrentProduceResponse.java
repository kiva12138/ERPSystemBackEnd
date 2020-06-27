package com.sun.erpbackend.response.bill;

import java.util.ArrayList;
import java.util.List;

import com.sun.erpbackend.entity.bill.Bill;

public class BillCurrentProduceResponse {
	private int code;
	private String message;
	private int activeMount;
	private int waitingMount;
	private List<Bill> bills;
	
	public BillCurrentProduceResponse () {
		this.bills = new ArrayList<Bill>();
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

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	
	
}
