package com.sun.erpbackend.controller.tree;

public class EditTreeParams {
	String name;
	Integer id;
	Integer targetmid;
	String description;
	Integer status;
	String[] needs;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String[] getNeeds() {
		return needs;
	}
	public void setNeeds(String[] needs) {
		this.needs = needs;
	}
	
}
