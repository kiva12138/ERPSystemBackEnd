package com.sun.erpbackend.response.produce;

import java.util.ArrayList;
import java.util.List;

import com.sun.erpbackend.response.bill.SingleBill;

public class StoppedProduceResponse {
	private int code;
	private String message;
	private int stoppedMount;
	private List<SingleBill> bills;
	public StoppedProduceResponse () {
		this.bills = new ArrayList<SingleBill>();
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
	public int getStoppedMount() {
		return stoppedMount;
	}
	public void setStoppedMount(int stoppedMount) {
		this.stoppedMount = stoppedMount;
	}
	public List<SingleBill> getBills() {
		return bills;
	}
	public void setBills(List<SingleBill> bills) {
		this.bills = bills;
	}
	
}
