package com.sun.erpbackend.entity.bill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billuse")
public class BillUse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="billid")
	private Integer billId;
	@Column(name="usemid")
	private Integer useMaterialId;
	private Integer mount;
	@Column(name="haveused")
	private Integer haveUsed;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public Integer getUseMaterialId() {
		return useMaterialId;
	}
	public void setUseMaterialId(Integer useMaterialId) {
		this.useMaterialId = useMaterialId;
	}
	public Integer getMount() {
		return mount;
	}
	public void setMount(Integer mount) {
		this.mount = mount;
	}
	public Integer getHaveUsed() {
		return haveUsed;
	}
	public void setHaveUsed(Integer haveUsed) {
		this.haveUsed = haveUsed;
	}
	
	
}
