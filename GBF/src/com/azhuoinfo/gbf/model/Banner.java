package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Banner implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String id;
	private String name;
	private String url;
	private String image;
	public Banner(){}
	public Banner(String id, String name, String url, String image) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.image = image;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
