package com.sun.erpbackend.response.record;

import java.util.ArrayList;
import java.util.List;

import com.sun.erpbackend.entity.material.MaterialProduce;

public class MaterialProduceResponse {
	private Integer code;
	private String message;
	private Integer allLength;
	private List<MaterialProduce> records;
	public MaterialProduceResponse() {
		this.records = new ArrayList<MaterialProduce>();
	}
	
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
	public Integer getAllLength() {
		return allLength;
	}
	public void setAllLength(Integer allLength) {
		this.allLength = allLength;
	}
	public List<MaterialProduce> getRecords() {
		return records;
	}
	public void setRecords(List<MaterialProduce> records) {
		this.records = records;
	}
	
}
