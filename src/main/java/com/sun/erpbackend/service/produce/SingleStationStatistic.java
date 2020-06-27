package com.sun.erpbackend.service.produce;

import java.util.HashSet;
import java.util.Set;

public class SingleStationStatistic {
	private Set<Integer> billIds;
	public SingleStationStatistic () {
		this.billIds = new HashSet<Integer>();
	}
	public Set<Integer> getBillIds() {
		return billIds;
	}
	public void setBillIds(Set<Integer> billIds) {
		this.billIds = billIds;
	}
}
