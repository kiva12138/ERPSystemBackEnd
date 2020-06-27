package com.sun.erpbackend.response.produce;

import java.util.ArrayList;
import java.util.List;

import com.sun.erpbackend.response.bill.SingleBill;

public class RefusedProduce {
	private int code;
	private String message;
	private int refusedMount;
	private List<SingleBill> bills;
	
	public RefusedProduce () {
		this.bills = new ArrayList<SingleBill>();
	}
	
	public int getRefusedMount() {
		return refusedMount;
	}

	public void setRefusedMount(int refusedMount) {
		this.refusedMount = refusedMount;
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

	public List<SingleBill> getBills() {
		return bills;
	}
	public void setBills(List<SingleBill> bills) {
		this.bills = bills;
	}
	
	
}
