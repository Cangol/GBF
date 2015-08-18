package com.azhuoinfo.gbf.model;



public class PollPoint {

	// Fields

	private String id;
	private String voteId;
	private String content;
	private String userId;
	private String timestramp;
	private Integer isDelete;
	private Integer agreeNum;

	public PollPoint() {
		
	}

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

	public Integer getAgreeNum() {
		return agreeNum;
	}

	public void setAgreeNum(Integer agreeNum) {
		this.agreeNum = agreeNum;
	}

	
}