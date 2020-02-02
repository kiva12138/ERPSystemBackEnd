package com.sun.erpbackend.response.statisticbystation;

import java.util.ArrayList;
import java.util.List;

public class MaterialStatisticsByStation {
	private int code;
	private String message;
	private List<SingleStationData> data;
	
	public MaterialStatisticsByStation () {
		this.code = 0;
		this.message = "";
		this.data = new ArrayList<SingleStationData>();
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
	public List<SingleStationData> getData() {
		return data;
	}
	public void setData(List<SingleStationData> data) {
		this.data = data;
	}
	
	
}
