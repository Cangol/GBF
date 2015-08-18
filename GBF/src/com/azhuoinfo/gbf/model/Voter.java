package com.azhuoinfo.gbf.model;

import java.io.Serializable;

public class Voter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String userId;
	private String avatar;
	private String nickname;
	private String gender;
	private String location;
	private String job;
	private String areaId;
	private Integer pollNum;
	private Integer voteNum;
	private Integer markNum;
	private String followingNum;
	private String followerNum;
	
	private boolean isFollowing;
	private String area;
	public Voter(){}
	

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


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}

	


	public Integer getPollNum() {
		return pollNum;
	}


	public void setPollNum(Integer pollNum) {
		this.pollNum = pollNum;
	}


	public Integer getVoteNum() {
		return voteNum;
	}


	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}


	public Integer getMarkNum() {
		return markNum;
	}


	public void setMarkNum(Integer markNum) {
		this.markNum = markNum;
	}


	public String getFollowingNum() {
		return followingNum;
	}


	public void setFollowingNum(String followingNum) {
		this.followingNum = followingNum;
	}


	public String getFollowerNum() {
		return followerNum;
	}


	public void setFollowerNum(String followerNum) {
		this.followerNum = followerNum;
	}


	public boolean isFollowing() {
		return isFollowing;
	}


	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}

	

}
