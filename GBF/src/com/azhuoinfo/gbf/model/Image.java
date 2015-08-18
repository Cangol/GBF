package com.azhuoinfo.gbf.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable{
	private int id;
	private String name;
	private String url;
	private String thumb;
	private String timestamp;
	private int status;
	public Image(){}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(thumb);
		dest.writeString(url);
		dest.writeString(timestamp);
		dest.writeInt(status);
	}
	public Image(Parcel in) {
		id = in.readInt();
		name = in.readString();
		thumb = in.readString();
		url = in.readString();
		timestamp = in.readString();
		status = in.readInt();
	}
	public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {

		@Override
		public Image createFromParcel(Parcel in) {
			return new Image(in);
		}

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
	};
	
	
}
