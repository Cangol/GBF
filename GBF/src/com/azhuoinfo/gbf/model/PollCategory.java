package com.azhuoinfo.gbf.model;

public class PollCategory{

	// Fields

	private String areaId;
	private String parentCategoryId;
	private String name;
	private Integer voteNum;

	// Constructors

	/** default constructor */
	public PollCategory() {
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getParentAreaId() {
		return parentCategoryId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentCategoryId = parentAreaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}

	
}