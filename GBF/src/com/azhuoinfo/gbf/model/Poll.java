package com.azhuoinfo.gbf.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Poll implements Parcelable{

	// Fields

	private String id;
	private String areaId;
	private String content;
	private String userId;
	private String location;
	private String image;
	private String publishTime;
	private String beginTime;
	private String endTime;
	private Integer isDelete;
	private Integer isTop;
	private Integer isRecommend;
	private Integer commentNum;
	private Integer joinNum;

	// Constructors
	private List<PollPoint> points;
	private String nickname;
	private String avatar;
	private String myPointId;
	private String area;
	/** default constructor */
	public Poll() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsTop() {
		return this.isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getIsRecommend() {
		return this.isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getCommentNum() {
		return this.commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public List<PollPoint> getPoints() {
		return points;
	}

	public void setPoints(List<PollPoint> points) {
		this.points = points;
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

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}
	
	public String getMyPointId() {
		return myPointId;
	}

	public void setMyPointId(String myPointId) {
		this.myPointId = myPointId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(areaId);
		dest.writeString(content);
		dest.writeString(publishTime);
		dest.writeString(beginTime);
		dest.writeString(endTime);
		dest.writeString(location);
		dest.writeString(image);
		dest.writeInt(isDelete);
		dest.writeInt(isTop);
		dest.writeInt(isRecommend);
		dest.writeInt(commentNum);
		dest.writeInt(joinNum);
	}

	public static final Parcelable.Creator<Poll> CREATOR = new Creator<Poll>() {

		@Override
		public Poll createFromParcel(Parcel source) {
			Poll p = new Poll();
			p.setId(source.readString());
			p.setAreaId(source.readString());
			p.setContent(source.readString());
			p.setPublishTime(source.readString());
			p.setBeginTime(source.readString());
			p.setEndTime(source.readString());
			p.setLocation(source.readString());
			p.setImage(source.readString());
			p.setIsDelete(source.readInt());
			p.setIsTop(source.readInt());
			p.setIsRecommend(source.readInt());
			p.setCommentNum(source.readInt());
			p.setJoinNum(source.readInt());
			return p;
		}

		@Override
		public Poll[] newArray(int size) {
			return new Poll[size];
		}
	};

	public String getPoint(String pointId) {
		if(points!=null&&points.size()>0){
			for(int i=0;i<points.size();i++){
				if(points.get(i).getId().equals(pointId)){
					return points.get(i).getContent();
				}
			}
		}
		return null;
	}	
}