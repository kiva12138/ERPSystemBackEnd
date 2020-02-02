package com.sun.erpbackend.response.materialcurrent;

import java.util.ArrayList;
import java.util.List;

public class MaterialCurrentResponse {
	private Integer code;
	private String message;
	private Integer allLength;
	private List<SingleStationCurrentData> data;
	
	public MaterialCurrentResponse () {
		this.code = 0;
		this.message = "";
		this.data = new ArrayList<SingleStationCurrentData>();
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
	public List<SingleStationCurrentData> getData() {
		return data;
	}
	public void setData(List<SingleStationCurrentData> data) {
		this.data = data;
	}

	public Integer getAllLength() {
		return allLength;
	}

	public void setAllLength(Integer allLength) {
		this.allLength = allLength;
	}
	
}
