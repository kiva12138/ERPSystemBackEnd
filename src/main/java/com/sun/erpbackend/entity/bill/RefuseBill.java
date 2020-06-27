package com.sun.erpbackend.entity.bill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="refusebill")
public class RefuseBill {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer billid;
	private Integer refusekind;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBillid() {
		return billid;
	}
	public void setBillid(Integer billid) {
		this.billid = billid;
	}
	public Integer getRefusekind() {
		return refusekind;
	}
	public void setRefusekind(Integer refusekind) {
		this.refusekind = refusekind;
	}
	
	
}
