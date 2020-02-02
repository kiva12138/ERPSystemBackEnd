package com.sun.erpbackend.response.statisticbystation;

import java.util.ArrayList;
import java.util.List;

public class SingleStationData {
	private Integer id;
	private String name;
	private Integer status;
	private List<SingleStationMaterial> use;
	private List<SingleStationMaterial> out;
	
	public SingleStationData() {
		super();
		this.id = 0;
		this.name = "";
		this.status = 0;
		this.use = new ArrayList<SingleStationMaterial>();
		this.out = new ArrayList<SingleStationMaterial>();
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
	public List<SingleStationMaterial> getUse() {
		return use;
	}
	public void setUse(List<SingleStationMaterial> use) {
		this.use = use;
	}
	public List<SingleStationMaterial> getOut() {
		return out;
	}
	public void setOut(List<SingleStationMaterial> out) {
		this.out = out;
	}
	
}
