package com.ssafy.mvc.model.dto;

public class Music {
	private int musicNo;
	private String musicId;
	private String src;
	private String userId;
	public Music() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Music(int no, String musicId, String src, String userId) {
		super();
		this.musicNo = no;
		this.musicId = musicId;
		this.src = src;
		this.userId = userId;
	}
	public int getNo() {
		return musicNo;
	}
	public void setNo(int no) {
		this.musicNo = no;
	}
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Music [no=" + musicNo + ", musicId=" + musicId + ", src=" + src + ", userId=" + userId + "]";
	}
	
}
