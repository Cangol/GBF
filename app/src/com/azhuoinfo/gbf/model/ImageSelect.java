package com.azhuoinfo.gbf.model;

import java.util.ArrayList;
import java.util.List;

public class ImageSelect {
	private String url;
	private boolean isSelected=false;
	
	public static ArrayList<ImageSelect> listOf(List<String> strs){
		ArrayList<ImageSelect> list=new ArrayList<ImageSelect>();
		if(strs!=null)for(int i=0;i<strs.size();i++){
			list.add(new ImageSelect(strs.get(i)));
		}
		return list;
	}
	public static ArrayList<String> toList(List<ImageSelect> images){
		ArrayList<String> list=new ArrayList<String>();
		if(images!=null)for(int i=0;i<images.size();i++){
			list.add(images.get(i).getUrl());
		}
		return list;
	}
	public ImageSelect(){
	}
	
	public ImageSelect(String url) {
		super();
		this.url = url;
	}

	public ImageSelect(String url, boolean isSelected) {
		super();
		this.url = url;
		this.isSelected = isSelected;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public boolean equals(Object o) {
		ImageSelect i=(ImageSelect)o;
		return url.equals(i.getUrl());
	}
	@Override
	public String toString() {
		return  url;
	}
	
}
