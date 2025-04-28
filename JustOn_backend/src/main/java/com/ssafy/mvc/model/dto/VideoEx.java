package com.ssafy.mvc.model.dto;

public class VideoEx {
	private int videoExNo;
	private String part;
	private int exWeight;
	private int videoNo;
	private int selCnt;
	private String userId;
	public VideoEx() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoEx(int videoExNo, String part, int exWeight, int videoNo, int selCnt, String userId) {
		super();
		this.videoExNo = videoExNo;
		this.part = part;
		this.exWeight = exWeight;
		this.videoNo = videoNo;
		this.selCnt = selCnt;
		this.userId = userId;
	}
	public int getVideoExNo() {
		return videoExNo;
	}
	public void setVideoExNo(int videoExNo) {
		this.videoExNo = videoExNo;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public int getExWeight() {
		return exWeight;
	}
	public void setExWeight(int exWeight) {
		this.exWeight = exWeight;
	}
	public int getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(int videoNo) {
		this.videoNo = videoNo;
	}
	public int getSelCnt() {
		return selCnt;
	}
	public void setSelCnt(int selCnt) {
		this.selCnt = selCnt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "VideoEx [videoExNo=" + videoExNo + ", part=" + part + ", exWeight=" + exWeight + ", videoNo=" + videoNo
				+ ", selCnt=" + selCnt + ", userId=" + userId + "]";
	}
	
}
