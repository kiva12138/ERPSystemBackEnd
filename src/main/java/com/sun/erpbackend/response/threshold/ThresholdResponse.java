package com.sun.erpbackend.response.threshold;

import java.util.ArrayList;
import java.util.List;

public class ThresholdResponse {
	private Integer code;
	private String message;
	private Integer allLength;
	private List<SingleMaterialThreshold> data;
	
	public ThresholdResponse () {
		this.code = 0;
		this.message = "";
		this.allLength = 0;
		this.data = new ArrayList<SingleMaterialThreshold>();
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
	public List<SingleMaterialThreshold> getData() {
		return data;
	}
	public void setData(List<SingleMaterialThreshold> data) {
		this.data = data;
	}

	public Integer getAllLength() {
		return allLength;
	}

	public void setAllLength(Integer allLength) {
		this.allLength = allLength;
	}
	
	
}
