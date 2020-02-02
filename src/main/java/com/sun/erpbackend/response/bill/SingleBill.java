package com.sun.erpbackend.response.bill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleBill {
	private Integer id;
	private String name;
	private Integer estimateTime;
	private String description;
	private Integer status;
	private String output;
	private Integer outputMount;
	private Integer outputKind;
	private Integer haveoutputMount;
	private Integer station;
	private String refuseReason;
	private String stoppedReason;
	private Date acceptedTime;
	private Date stoppedTime;
	private Date completeTime;
	private List<SingleBillMaterial> materials;
	private List<SingleBillMaterial> haveused;
	
	public SingleBill() {
		this.id=0;
		this.name="";
		this.output="";
		this.outputMount=0;
		this.outputKind=0;
		this.estimateTime=0;
		this.description = "";
		this.status=0;
		this.materials = new ArrayList<SingleBillMaterial>();
		this.haveused = new ArrayList<SingleBillMaterial>();
		this.haveoutputMount = 0;
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
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public Integer getOutputMount() {
		return outputMount;
	}
	public void setOutputMount(Integer outputMount) {
		this.outputMount = outputMount;
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
	public List<SingleBillMaterial> getMaterials() {
		return materials;
	}
	public void setMaterials(List<SingleBillMaterial> materials) {
		this.materials = materials;
	}
	public List<SingleBillMaterial> getHaveused() {
		return haveused;
	}
	public void setHaveused(List<SingleBillMaterial> haveused) {
		this.haveused = haveused;
	}
	public Integer getHaveoutputMount() {
		return haveoutputMount;
	}
	public void setHaveoutputMount(Integer haveoutputMount) {
		this.haveoutputMount = haveoutputMount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getStation() {
		return station;
	}

	public void setStation(Integer station) {
		this.station = station;
	}

	public Date getAcceptedTime() {
		return acceptedTime;
	}

	public void setAcceptedTime(Date acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	public String getStoppedReason() {
		return stoppedReason;
	}

	public void setStoppedReason(String stoppedReason) {
		this.stoppedReason = stoppedReason;
	}

	public Date getStoppedTime() {
		return stoppedTime;
	}

	public void setStoppedTime(Date stoppedTime) {
		this.stoppedTime = stoppedTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
}
