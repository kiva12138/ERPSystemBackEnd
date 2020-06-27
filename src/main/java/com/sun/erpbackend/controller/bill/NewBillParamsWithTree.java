package com.sun.erpbackend.controller.bill;

public class NewBillParamsWithTree {
	String name;
	Integer output;
	Integer outputMount;
	Integer estimateTime;
	Integer treeId;
	String description;
	public Integer getTreeId() {
		return treeId;
	}
	public void setTreeId(Integer treeId) {
		this.treeId = treeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOutput() {
		return output;
	}
	public void setOutput(Integer output) {
		this.output = output;
	}
	public Integer getOutputMount() {
		return outputMount;
	}
	public void setOutputMount(Integer outputMount) {
		this.outputMount = outputMount;
	}
	public Integer getEstimateTime() {
		return estimateTime;
	}
	public void setEstimateTime(Integer estimateTime) {
		this.estimateTime = estimateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
