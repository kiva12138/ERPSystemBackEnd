package com.sun.erpbackend.response;

import java.util.List;

import com.sun.erpbackend.entity.material.Transport;

public class TransportResponse {
	private Integer code;
	private String message;
	private List<Transport> data;
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
	public List<Transport> getData() {
		return data;
	}
	public void setData(List<Transport> data) {
		this.data = data;
	}
	
	
}
