package com.ssafy.mvc.model.dto;

public class VideoExList {
	private int videoExNo;
	private String part;
	private int exNums;
	private int exWeight;
	private int videoNo;
	private String userId;
	public VideoExList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoExList(int videoExNo, String part, int exNums, int exWeight, int videoNo, String userId) {
		super();
		this.videoExNo = videoExNo;
		this.part = part;
		this.exNums = exNums;
		this.exWeight = exWeight;
		this.videoNo = videoNo;
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
	public int getExNums() {
		return exNums;
	}
	public void setExNums(int exNums) {
		this.exNums = exNums;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "VideoExList [videoExNo=" + videoExNo + ", part=" + part + ", exNums=" + exNums + ", exWeight="
				+ exWeight + ", videoNo=" + videoNo + ", userId=" + userId + "]";
	}
	
	
}
