package com.ssafy.mvc.model.dto;

public class Music {
	private int musicNo;
	private String musicId;
	private String src;
	private String title;
	private String userId;
	public Music() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Music(int musicNo, String musicId, String src, String title, String userId) {
		super();
		this.musicNo = musicNo;
		this.musicId = musicId;
		this.src = src;
		this.title = title;
		this.userId = userId;
	}
	public int getMusicNo() {
		return musicNo;
	}
	public void setMusicNo(int musicNo) {
		this.musicNo = musicNo;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Music [musicNo=" + musicNo + ", musicId=" + musicId + ", src=" + src + ", title=" + title + ", userId="
				+ userId + "]";
	}
	
}
