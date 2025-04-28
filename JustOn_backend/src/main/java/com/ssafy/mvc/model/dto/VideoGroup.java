package com.ssafy.mvc.model.dto;

public class VideoGroup {
	private String categoryName;
	private String userId;
	private String groupDesc;
	private String createDate;
	public VideoGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoGroup(String categoryName, String userId, String groupDesc, String createDate) {
		super();
		this.categoryName = categoryName;
		this.userId = userId;
		this.groupDesc = groupDesc;
		this.createDate = createDate;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "VideoGroup [categoryName=" + categoryName + ", userId=" + userId + ", groupDesc=" + groupDesc
				+ ", createDate=" + createDate + "]";
	}
	
	
}
