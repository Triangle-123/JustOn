package com.ssafy.mvc.model.dto;

public class MusicGroup {
	private String playlistName;
	private String userId;
	private String groupDesc;
	private String createDate;
	public MusicGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MusicGroup(String playlistName, String userId, String groupDesc, String createDate) {
		super();
		this.playlistName = playlistName;
		this.userId = userId;
		this.groupDesc = groupDesc;
		this.createDate = createDate;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
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
		return "MusicGroup [playlistName=" + playlistName + ", userId=" + userId + ", groupDesc=" + groupDesc
				+ ", createDate=" + createDate + "]";
	}
	
	
}
