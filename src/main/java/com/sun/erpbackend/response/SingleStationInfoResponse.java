package com.sun.erpbackend.response;

import com.sun.erpbackend.entity.station.Station;

public class SingleStationInfoResponse {
	private int code;
	private String message;
	private Station station;
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
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	
	
}
