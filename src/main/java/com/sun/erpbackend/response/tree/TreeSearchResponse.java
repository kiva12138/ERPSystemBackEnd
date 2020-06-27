package com.sun.erpbackend.response.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeSearchResponse {
	private Integer code;
	private String message;
	private int allLength;
	private List<SingleTree> data;
	
	public TreeSearchResponse () {
		this.data = new ArrayList<SingleTree>();
		this.code = 0;
		this.message = "";
		this.allLength = 0;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getAllLength() {
		return allLength;
	}

	public void setAllLength(int allLength) {
		this.allLength = allLength;
	}

	public List<SingleTree> getData() {
		return data;
	}

	public void setData(List<SingleTree> data) {
		this.data = data;
	}
	
}
