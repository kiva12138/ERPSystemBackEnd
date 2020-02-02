package com.sun.erpbackend.response.materialcurrent;

import java.util.ArrayList;
import java.util.List;

public class SingleStationCurrentData {
	private Integer id;
	private String name;
	private Integer status;
	private Integer process;
	private List<SingleMaterialCurrentUse> uses;
	
	
	public SingleStationCurrentData() {
		super();
		this.id = 0;
		this.name = "";
		this.status = 0;
		this.process = 0;
		this.uses = new ArrayList<SingleMaterialCurrentUse>();
	}
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
	}
	public List<SingleMaterialCurrentUse> getUses() {
		return uses;
	}
	public void setUses(List<SingleMaterialCurrentUse> uses) {
		this.uses = uses;
	}
	
}
