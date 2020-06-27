package com.sun.erpbackend.response.tree;

import java.util.ArrayList;
import java.util.List;

public class SingleTree {
	private Integer id;
	private String name;
	private String opname;
	private Integer status;
	private String description;
	private List<SingleTreeNeed> need;
	
	public SingleTree() {
		this.need = new ArrayList<SingleTreeNeed>();
		this.id = 0;
		this.name = "";
		this.opname = "";
		this.status = 0;
		this.description = "";
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

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SingleTreeNeed> getNeed() {
		return need;
	}

	public void setNeed(List<SingleTreeNeed> need) {
		this.need = need;
	}

}
