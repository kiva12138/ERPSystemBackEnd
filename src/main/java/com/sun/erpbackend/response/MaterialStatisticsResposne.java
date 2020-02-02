package com.sun.erpbackend.response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SingleStatistic {
	private Integer id;
	private String name;
	private Integer output;
	private Integer use;
	public SingleStatistic(Integer id, String name, Integer output, Integer use) {
		super();
		this.id = id;
		this.name = name;
		this.output = output;
		this.use = use;
	}
	public SingleStatistic() {
		super();
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
	public Integer getOutput() {
		return output;
	}
	public void setOutput(Integer output) {
		this.output = output;
	}
	public Integer getUse() {
		return use;
	}
	public void setUse(Integer use) {
		this.use = use;
	}
}

public class MaterialStatisticsResposne {
	private int code;
	private String message;
	private Integer allLength;
	private List<SingleStatistic> data;
	public MaterialStatisticsResposne() {
		super();
		this.data = new ArrayList<SingleStatistic>();
		this.message = "";
		this.code = 0;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SingleStatistic> getData() {
		return data;
	}
	public void setData(List<SingleStatistic> data) {
		this.data = data;
	}
	public void addData(Integer id, String name, Integer output, Integer use) {
		this.data.add(new SingleStatistic(id, name, output, use));
	}
	public Integer getAllLength() {
		return allLength;
	}
	public void setAllLength(Integer allLength) {
		this.allLength = allLength;
	}
	public void sortData() {
		this.data.sort(new Comparator<SingleStatistic>() {
			@Override
			public int compare(SingleStatistic o1, SingleStatistic o2) {
				return o2.getUse() - o1.getUse();
			}
		});
	}
	
}
