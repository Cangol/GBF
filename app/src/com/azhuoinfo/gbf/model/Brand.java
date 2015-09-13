package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Brand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String id;
	private String name;
	private String logo;
	private String desc;
	public Brand(){}
	
	public Brand(String id, String name, String logo, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.desc = desc;
	}
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
