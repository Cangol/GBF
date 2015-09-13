package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Tag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String id;
	private String name;
	private String categroy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategroy() {
		return categroy;
	}
	public void setCategroy(String categroy) {
		this.categroy = categroy;
	}
	
	
	
}
