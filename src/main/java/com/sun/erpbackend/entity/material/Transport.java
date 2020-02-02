package com.sun.erpbackend.entity.material;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transport")
public class Transport {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date time;
	private Integer mid;
	private Integer mount;
	private Integer way;
	private String description;
	
	public Transport() {
	}
	
	public Transport( Date time, Integer mid, Integer mount, Integer way, String description) {
		super();
		this.time = time;
		this.mid = mid;
		this.mount = mount;
		this.way = way;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public Integer getMount() {
		return mount;
	}
	public void setMount(Integer mount) {
		this.mount = mount;
	}
	public Integer getWay() {
		return way;
	}
	public void setWay(Integer way) {
		this.way = way;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
