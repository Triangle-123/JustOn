package com.ssafy.mvc.model.dto;

public class DiaryEx {
	private int diaryExNo;
	private int playNum;
	private int diaryNo;
	private int videoNo;
	
	public DiaryEx() {}

	public DiaryEx(int diaryExNo, int playNum, int diaryNo, int videoNo) {
		super();
		this.diaryExNo = diaryExNo;
		this.playNum = playNum;
		this.diaryNo = diaryNo;
		this.videoNo = videoNo;
	}

	public int getDiaryExNo() {
		return diaryExNo;
	}

	public void setDiaryExNo(int diaryExNo) {
		this.diaryExNo = diaryExNo;
	}

	public int getPlayNum() {
		return playNum;
	}

	public void setPlayNum(int playNum) {
		this.playNum = playNum;
	}

	public int getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(int diaryNo) {
		this.diaryNo = diaryNo;
	}

	public int getVideoNo() {
		return videoNo;
	}

	public void setVideoNo(int videoNo) {
		this.videoNo = videoNo;
	}

	@Override
	public String toString() {
		return "DiaryEx [diaryExNo=" + diaryExNo + ", playNum=" + playNum + ", diaryNo=" + diaryNo + ", videoNo="
				+ videoNo + "]";
	}
	
	
}
