package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Sale implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String salesId;
	private String image;
	private String title;
	private float price;
	public Sale(){}
	

	public String getSalesId() {
		return salesId;
	}


	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}


	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	
}
