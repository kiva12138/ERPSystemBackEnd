package com.sun.erpbackend.controller.bill;

public class StopByStationParam {
	Integer id;String reason; String[] haveUsed; int haveOutput;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	public String[] getHaveUsed() {
		return haveUsed;
	}

	public void setHaveUsed(String[] haveUsed) {
		this.haveUsed = haveUsed;
	}

	public int getHaveOutput() {
		return haveOutput;
	}

	public void setHaveOutput(int haveOutput) {
		this.haveOutput = haveOutput;
	}
	
}
