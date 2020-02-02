package com.sun.erpbackend.response.bill;

import java.util.ArrayList;
import java.util.List;

public class BillSearchResponse {
	private Integer code;
	private String message;
	private int allLength;
	private List<SingleBill> data;
	
	public BillSearchResponse() {
		this.code = 0;
		this.message = "";
		this.data = new ArrayList<SingleBill>();
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SingleBill> getData() {
		return data;
	}
	public void setData(List<SingleBill> data) {
		this.data = data;
	}

	public int getAllLength() {
		return allLength;
	}

	public void setAllLength(int allLength) {
		this.allLength = allLength;
	}
}
