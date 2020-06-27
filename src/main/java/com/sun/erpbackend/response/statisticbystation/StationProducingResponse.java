package com.sun.erpbackend.response.statisticbystation;

import java.util.ArrayList;
import java.util.List;

public class StationProducingResponse {
	private int code;
	private String message;
	private int activeMount;
	private int producingMount;
	private List<StationProducingResponseSingle> stations;
	
	public int getActiveMount() {
		return activeMount;
	}

	public void setActiveMount(int activeMount) {
		this.activeMount = activeMount;
	}

	public int getProducingMount() {
		return producingMount;
	}

	public void setProducingMount(int producingMount) {
		this.producingMount = producingMount;
	}
	
	public StationProducingResponse () {
		this.stations = new ArrayList<StationProducingResponseSingle>();
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

	public List<StationProducingResponseSingle> getStations() {
		return stations;
	}

	public void setStations(List<StationProducingResponseSingle> stations) {
		this.stations = stations;
	}
	
}
