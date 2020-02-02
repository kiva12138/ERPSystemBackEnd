package com.sun.erpbackend.entity.bill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billoutput")
public class BillOutput {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="billid")
	private Integer billId;
	
	@Column(name="opmid")
	private Integer outputMaterialId;
	
	@Column(name="mount")
	private Integer mount;
	
	@Column(name="haveop")
	private Integer haveOutput;
	
	public BillOutput() {
		super();
	}
	
	public BillOutput(Integer billId, Integer outputMaterialId, Integer mount, Integer haveOutput) {
		super();
		this.billId = billId;
		this.outputMaterialId = outputMaterialId;
		this.mount = mount;
		this.haveOutput = haveOutput;
	}

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

	public Integer getOutputMaterialId() {
		return outputMaterialId;
	}

	public void setOutputMaterialId(Integer outputMaterialId) {
		this.outputMaterialId = outputMaterialId;
	}

	public Integer getMount() {
		return mount;
	}

	public void setMount(Integer mount) {
		this.mount = mount;
	}

	public Integer getHaveOutput() {
		return haveOutput;
	}

	public void setHaveOutput(Integer haveOutput) {
		this.haveOutput = haveOutput;
	}
	
	
}
