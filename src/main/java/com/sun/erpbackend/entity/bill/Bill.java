package com.sun.erpbackend.entity.bill;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bill")
public class Bill {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(name="opkind")
	private Integer outputKind;
	@Column(name="estimatetime")
	private Integer estimateTime;
	private Integer status;
	private String description;
	@Column(name="stationid")
	private Integer stationId;
	@Column(name="refusereason")
	private String refuseReason;
	@Column(name="stoppedtime")
	private Date stoppedTime;
	@Column(name="stoppedreason")
	private String stoppedReson;
	@Column(name="completetime")
	private Date completeTime;
	@Column(name="distributetime")
	private Date distributeTime;
	@Column(name="createdtime")
	private Date createdTime;
	@Column(name="usebrief")
	private String useBrief;
	@Column(name="outbrief")
	private String outBrief;
	public String getUseBrief() {
		return useBrief;
	}
	public void setUseBrief(String useBrief) {
		this.useBrief = useBrief;
	}
	public String getOutBrief() {
		return outBrief;
	}
	public void setOutBrief(String outBrief) {
		this.outBrief = outBrief;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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
	public Integer getOutputKind() {
		return outputKind;
	}
	public void setOutputKind(Integer outputKind) {
		this.outputKind = outputKind;
	}
	public Integer getEstimateTime() {
		return estimateTime;
	}
	public void setEstimateTime(Integer estimateTime) {
		this.estimateTime = estimateTime;
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
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public Date getStoppedTime() {
		return stoppedTime;
	}
	public void setStoppedTime(Date stoppedTime) {
		this.stoppedTime = stoppedTime;
	}
	public String getStoppedReson() {
		return stoppedReson;
	}
	public void setStoppedReson(String stoppedReson) {
		this.stoppedReson = stoppedReson;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Date getDistributeTime() {
		return distributeTime;
	}
	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}
	
	
}
