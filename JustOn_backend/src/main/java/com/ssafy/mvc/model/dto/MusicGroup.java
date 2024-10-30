package com.ssafy.mvc.model.dto;

public class MusicGroup {
	private int playlistNo;
	private String userId;
	private String playlistName;
	private String groupDesc;
	public MusicGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MusicGroup(int no, String userId, String name, String groupDesc) {
		super();
		this.playlistNo = no;
		this.userId = userId;
		this.playlistName = name;
		this.groupDesc = groupDesc;
	}
	public int getNo() {
		return playlistNo;
	}
	public void setNo(int no) {
		this.playlistNo = no;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return playlistName;
	}
	public void setName(String name) {
		this.playlistName = name;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	@Override
	public String toString() {
		return "MusicGroup [no=" + playlistNo + ", userId=" + userId + ", name=" + playlistName + ", groupDesc=" + groupDesc + "]";
	}
	
}
