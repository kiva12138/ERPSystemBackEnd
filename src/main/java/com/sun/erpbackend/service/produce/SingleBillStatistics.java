package com.sun.erpbackend.service.produce;

import java.util.HashSet;
import java.util.Set;

public class SingleBillStatistics {
	private int status;
	private Set<Integer> billIds;
	public SingleBillStatistics () {
		this.status = 0;
		this.billIds = new HashSet<Integer>();
	}
	
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public Set<Integer> getBillIds() {
		return billIds;
	}
	public void setBillIds(Set<Integer> billIds) {
		this.billIds = billIds;
	}
}
