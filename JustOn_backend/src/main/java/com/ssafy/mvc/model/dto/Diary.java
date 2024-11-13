package com.ssafy.mvc.model.dto;

import java.util.List;

public class Diary {
	private int diaryNo;
//	private String title;
	private String content;
	private String regDate;
	private String userId;
	private List<DiaryEx> diaryExList;
	
	public Diary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Diary(int diaryNo, String content, String regDate, String userId, List<DiaryEx> diaryExList) {
		super();
		this.diaryNo = diaryNo;
		this.content = content;
		this.regDate = regDate;
		this.userId = userId;
		this.diaryExList = diaryExList;
	}

	public int getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(int diaryNo) {
		this.diaryNo = diaryNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<DiaryEx> getDiaryExList() {
		return diaryExList;
	}

	public void setDiaryExList(List<DiaryEx> diaryExList) {
		this.diaryExList = diaryExList;
	}

	@Override
	public String toString() {
		return "Diary [diaryNo=" + diaryNo + ", content=" + content + ", regDate=" + regDate + ", userId=" + userId
				+ ", diaryExList=" + diaryExList + "]";
	}

	
}
