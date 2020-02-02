package com.sun.erpbackend.response;

import java.util.List;

import com.sun.erpbackend.entity.station.Station;

public class StationFindResponse {
	private int code;
	private String message;
	private List<Station> data;
	private int allLength;
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
	public List<Station> getData() {
		return data;
	}
	public void setData(List<Station> data) {
		this.data = data;
	}
	public int getAllLength() {
		return allLength;
	}
	public void setAllLength(int allLength) {
		this.allLength = allLength;
	}
	
	
}
