package com.sun.erpbackend.response;

public class BillAllDataResponse {
	private Integer code;
	private String Message;
	private Integer all;
	private Integer created;
	private Integer waiting;
	private Integer refused;
	private Integer producing;
	private Integer overtime;
	private Integer stopped;
	private Integer complete;
	private Integer over;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Integer getAll() {
		return all;
	}
	public void setAll(Integer all) {
		this.all = all;
	}
	public Integer getCreated() {
		return created;
	}
	public void setCreated(Integer created) {
		this.created = created;
	}
	public Integer getWaiting() {
		return waiting;
	}
	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}
	public Integer getRefused() {
		return refused;
	}
	public void setRefused(Integer refused) {
		this.refused = refused;
	}
	public Integer getProducing() {
		return producing;
	}
	public void setProducing(Integer producing) {
		this.producing = producing;
	}
	public Integer getOvertime() {
		return overtime;
	}
	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}
	public Integer getStopped() {
		return stopped;
	}
	public void setStopped(Integer stopped) {
		this.stopped = stopped;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getOver() {
		return over;
	}
	public void setOver(Integer over) {
		this.over = over;
	}
	
	
}
