package com.sun.erpbackend.service.produce;

import java.util.HashSet;
import java.util.Set;

public class SingleMaterialStatistic {
	private int mount;
	private Set<Integer> billIds;
	public SingleMaterialStatistic () {
		this.mount = 0;
		this.billIds = new HashSet<Integer>();
	}
	
	public int getMount() {
		return mount;
	}
	public void setMount(int mount) {
		this.mount = mount;
	}
	public Set<Integer> getBillIds() {
		return billIds;
	}
	public void setBillIds(Set<Integer> billIds) {
		this.billIds = billIds;
	}
	
	
}
