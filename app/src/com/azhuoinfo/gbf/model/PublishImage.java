package com.azhuoinfo.gbf.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PublishImage implements Parcelable{
	private String name;
	private String url;
	public PublishImage(){}
	
	public PublishImage(String name, String url) {
		super();
		this.name = name;
		this.url = url;
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
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(url);
	}
	public PublishImage(Parcel in) {
		name = in.readString();
		url = in.readString();
	}
	public static final Parcelable.Creator<PublishImage> CREATOR = new Parcelable.Creator<PublishImage>() {

		@Override
		public PublishImage createFromParcel(Parcel in) {
			return new PublishImage(in);
		}

		@Override
		public PublishImage[] newArray(int size) {
			return new PublishImage[size];
		}
	};
	
	
}
