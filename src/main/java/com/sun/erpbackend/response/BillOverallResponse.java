package com.sun.erpbackend.response;

public class BillOverallResponse {
	private Integer code;
	private String message;
	
	private Integer all;
    private Integer finished;
    private Integer producing;
    private Integer stopping;
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
	public Integer getFinished() {
		return finished;
	}
	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	public Integer getProducing() {
		return producing;
	}
	public void setProducing(Integer producing) {
		this.producing = producing;
	}
	public Integer getStopping() {
		return stopping;
	}
	public void setStopping(Integer stopping) {
		this.stopping = stopping;
	}
}
