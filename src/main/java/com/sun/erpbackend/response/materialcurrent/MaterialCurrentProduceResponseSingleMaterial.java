package com.sun.erpbackend.response.materialcurrent;

import java.util.ArrayList;
import java.util.List;

public class MaterialCurrentProduceResponseSingleMaterial {
	private int id;
	private int mount;
	private String name;
	private List<Integer> billids;
	public MaterialCurrentProduceResponseSingleMaterial () {
		this.billids = new ArrayList<Integer>();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMount() {
		return mount;
	}
	public void setMount(int mount) {
		this.mount = mount;
	}
	public List<Integer> getBillids() {
		return billids;
	}
	public void setBillids(List<Integer> billids) {
		this.billids = billids;
	}
	
}
