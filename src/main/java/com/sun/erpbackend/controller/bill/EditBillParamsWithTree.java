package com.sun.erpbackend.controller.bill;


public class EditBillParamsWithTree {
	Integer id; String name; int output; int outputMount;
	int estimateTime; int treeId; public int getTreeId() {
		return treeId;
	}



	public void setTreeId(int treeId) {
		this.treeId = treeId;
	}



	String description;
	
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getOutput() {
		return output;
	}



	public void setOutput(int output) {
		this.output = output;
	}



	public int getOutputMount() {
		return outputMount;
	}



	public void setOutputMount(int outputMount) {
		this.outputMount = outputMount;
	}



	public int getEstimateTime() {
		return estimateTime;
	}



	public void setEstimateTime(int estimateTime) {
		this.estimateTime = estimateTime;
	}


	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String toString() {
		return String.valueOf(id) + name + String.valueOf(output) + String.valueOf(outputMount)
			+ String.valueOf(estimateTime) + String.valueOf(treeId) + description;
	}
}
