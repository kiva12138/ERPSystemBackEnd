package com.sun.erpbackend.entity.tree;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tree")
public class Tree {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer targetmid;
	private String targetname;
	private Integer opmount;
	private Integer status;
	private String name;
	private String description;
	private String needbrief;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTargetmid() {
		return targetmid;
	}
	public void setTargetmid(Integer targetmid) {
		this.targetmid = targetmid;
	}
	public String getTargetname() {
		return targetname;
	}
	public void setTargetname(String targetname) {
		this.targetname = targetname;
	}
	public Integer getOpmount() {
		return opmount;
	}
	public void setOpmount(Integer opmount) {
		this.opmount = opmount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNeedbrief() {
		return needbrief;
	}
	public void setNeedbrief(String needbrief) {
		this.needbrief = needbrief;
	}
	
	
}
