package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Favorite implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String goodId;
	private String image;
	private String title;
	private float price;
	public Favorite(){}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
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
