package com.sun.erpbackend.response;

public class StationOveralResponse {
	private Integer code;
	private String message;
	private Integer all;
	private Integer producing;
	private Integer stopped;
	private Integer maintain;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getAll() {
		return all;
	}
	public void setAll(Integer all) {
		this.all = all;
	}
	public Integer getProducing() {
		return producing;
	}
	public void setProducing(Integer producing) {
		this.producing = producing;
	}
	public Integer getStopped() {
		return stopped;
	}
	public void setStopped(Integer stopped) {
		this.stopped = stopped;
	}
	public Integer getMaintain() {
		return maintain;
	}
	public void setMaintain(Integer maintain) {
		this.maintain = maintain;
	}
	
	
}
