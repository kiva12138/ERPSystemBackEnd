package com.sun.erpbackend.response.statisticbystation;

import java.util.ArrayList;
import java.util.List;

public class StationProducingResponseSingle {
	private int id;
	private String name;
	private int status;
	private List<Integer> billIds;
	
	public StationProducingResponseSingle () {
		this.billIds = new ArrayList<Integer>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Integer> getBillIds() {
		return billIds;
	}
	public void setBillIds(List<Integer> billIds) {
		this.billIds = billIds;
	}
	
	
	
}
