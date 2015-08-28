package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Friend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String userId;
	private String avatar;
	private String nickname;
	public Friend(){}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
