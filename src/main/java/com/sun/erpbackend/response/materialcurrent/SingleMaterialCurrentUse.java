package com.sun.erpbackend.response.materialcurrent;

public class SingleMaterialCurrentUse {
	private Integer id;
	private String name;
	private Integer mount;
	public SingleMaterialCurrentUse () {
		this.id = 0;
		this.name = "";
		this.mount = 0;
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
	public Integer getMount() {
		return mount;
	}
	public void setMount(Integer mount) {
		this.mount = mount;
	}
	
}
