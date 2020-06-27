package com.sun.erpbackend.controller.tree;

public class NewTreeParams {
	String name;
	Integer targetmid;
	String description;
	String[] needs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTargetmid() {
		return targetmid;
	}
	public void setTargetmid(Integer targetmid) {
		this.targetmid = targetmid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getNeeds() {
		return needs;
	}
	public void setNeeds(String[] needs) {
		this.needs = needs;
	}	
}
