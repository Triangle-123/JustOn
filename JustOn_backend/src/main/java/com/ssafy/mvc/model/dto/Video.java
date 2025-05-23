package com.ssafy.mvc.model.dto;

public class Video {
	private int videoNo;
	private String videoId;
	private String src;
	private String title;
	private int viewCnt;
	private String thumb;
	private String userId;
	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Video(int videoNo, String videoId, String src, String title, int viewCnt, String thumb, String userId) {
		super();
		this.videoNo = videoNo;
		this.videoId = videoId;
		this.src = src;
		this.title = title;
		this.viewCnt = viewCnt;
		this.thumb = thumb;
		this.userId = userId;
	}
	public int getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(int videoNo) {
		this.videoNo = videoNo;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
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
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Video [videoNo=" + videoNo + ", videoId=" + videoId + ", src=" + src + ", title=" + title + ", viewCnt="
				+ viewCnt + ", thumb=" + thumb + ", userId=" + userId + "]";
	}
	
	
}