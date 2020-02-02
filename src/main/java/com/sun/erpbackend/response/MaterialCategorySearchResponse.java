package com.sun.erpbackend.response;

import java.util.ArrayList;
import java.util.List;

class SingleMaterial{
	private int id;
	private String name;
	private int mount;
	private String description;
	private int remain;
	private int distributed;
	private int mclass;
	private int status;
	private String lastransport;
	public SingleMaterial(int id, String name, int mount, String description,
			int remain, int destributes, int mclass, int status, String lasttransport) {
		this.id = id;
		this.name = name;
		this.mount = mount;
		this.description = description;
		this.remain = remain;
		this.distributed = destributes;
		this.mclass = mclass;
		this.status = status;
		this.lastransport = lasttransport;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMount() {
		return mount;
	}
	public void setMount(int mount) {
		this.mount = mount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRemain() {
		return remain;
	}
	public void setRemain(int remain) {
		this.remain = remain;
	}
	public int getDistributed() {
		return distributed;
	}
	public void setDistributed(int distributed) {
		this.distributed = distributed;
	}
	public int getMclass() {
		return mclass;
	}
	public void setMclass(int mclass) {
		this.mclass = mclass;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLastransport() {
		return lastransport;
	}
	public void setLastransport(String lastransport) {
		this.lastransport = lastransport;
	}
}

public class MaterialCategorySearchResponse {
	private int code;
	private List<SingleMaterial> materials;
	private String message;
	private int allLength;
	public MaterialCategorySearchResponse() {
		this.materials = new ArrayList<SingleMaterial>();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<SingleMaterial> getMaterials() {
		return materials;
	}
	public void setMaterials(List<SingleMaterial> materials) {
		this.materials = materials;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void addMaterial(int id, String name, int mount, String description,
			int remain, int destributes, int mclass, int status, String lasttranport) {
		this.getMaterials().add(new SingleMaterial(id, name, mount, description, remain, destributes, mclass, status, lasttranport));
	}
	public int getAllLength() {
		return allLength;
	}
	public void setAllLength(int allLength) {
		this.allLength = allLength;
	}
}
