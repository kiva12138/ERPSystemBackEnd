package com.sun.erpbackend.entity.material;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="material")
public class Material {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="allmount")
	private int allMount;
	
	@Column(name="leftmount")
	private int leftMount;
	
	@Column(name="distributedmount")
	private int distributedMount;
	
	@Column(name="kind")
	private int kind;
	
	@Column(name="status")
	private int status;
	
	@Column(name="description")
	private String description;
	
	@Column(name="lasttransport")
	private Date lastTransport;
	
	@Column(name="warnthreshold")
	private int warnThresHold;
	
	@Column(name="dangerthreshold")
	private int dangerThresHold;
	
	public Material() {
		this.name = "";
		this.allMount = 0;
		this.leftMount = 0;
		this.distributedMount = 0;
		this.kind = 0;
		this.status = 0;
		this.description = "";
		this.lastTransport = new Date();
		this.warnThresHold = 0;
		this.dangerThresHold = 0;
	}
	
	public Material(String name, int allMount, int leftMount, int distributedMount, int kind, int status,
			String description, Date lastTransport, int warnThresHold, int dangerThresHold) {
		super();
		this.name = name;
		this.allMount = allMount;
		this.leftMount = leftMount;
		this.distributedMount = distributedMount;
		this.kind = kind;
		this.status = status;
		this.description = description;
		this.lastTransport = lastTransport;
		this.warnThresHold = warnThresHold;
		this.dangerThresHold = dangerThresHold;
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

	public int getAllMount() {
		return allMount;
	}

	public void setAllMount(int allMount) {
		this.allMount = allMount;
	}

	public int getLeftMount() {
		return leftMount;
	}

	public void setLeftMount(int leftMount) {
		if(this.leftMount >= this.warnThresHold) {
			this.status = 1;
		}else if (this.leftMount >= this.dangerThresHold) {
			this.status = 3;
		}else{
			this.status = 2;
		}
		this.leftMount = leftMount;
	}

	public int getDistributedMount() {
		return distributedMount;
	}

	public void setDistributedMount(int distributedMount) {
		this.distributedMount = distributedMount;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastTransport() {
		return lastTransport;
	}

	public void setLastTransport(Date lastTransport) {
		this.lastTransport = lastTransport;
	}

	public int getWarnThresHold() {
		return warnThresHold;
	}

	public void setWarnThresHold(int warnThresHold) {
		if(this.leftMount >= this.warnThresHold) {
			this.status = 1;
		}else if (this.leftMount >= this.dangerThresHold) {
			this.status = 3;
		}else{
			this.status = 2;
		}
		this.warnThresHold = warnThresHold;
	}

	public int getDangerThredHold() {
		return dangerThresHold;
	}

	public void setDangerThredHold(int dangerThredHold) {
		if(this.leftMount >= this.warnThresHold) {
			this.status = 1;
		}else if (this.leftMount >= this.dangerThresHold) {
			this.status = 3;
		}else{
			this.status = 2;
		}
		this.dangerThresHold = dangerThredHold;
	}
	
}
