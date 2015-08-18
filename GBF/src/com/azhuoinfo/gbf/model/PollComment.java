package com.azhuoinfo.gbf.model;


public class PollComment  {

	private String id;
	private String voteId;
	private String pointId;
	private String content;
	private String userId;
	private String timestramp;
	private Integer isDelete;
	private Integer prasiedNum;
	
	private boolean isPrasied;
	private String nickname;
	private String avatar;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVoteId() {
		return voteId;
	}
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTimestramp() {
		return timestramp;
	}
	public void setTimestramp(String timestramp) {
		this.timestramp = timestramp;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getPrasiedNum() {
		return prasiedNum;
	}
	public void setPrasiedNum(Integer prasiedNum) {
		this.prasiedNum = prasiedNum;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean getIsPrasied() {
		return isPrasied;
	}
	public void setIsPrasied(boolean isPrasied) {
		this.isPrasied = isPrasied;
	}
	
}