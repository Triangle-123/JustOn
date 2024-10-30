package com.ssafy.mvc.model.dto;

public class Diary {
	private int diaryNo;
	private String title;
	private String content;
	private String regDate;
	private String userId;
	public Diary() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Diary(int no, String title, String content, String regDate, String id) {
		super();
		this.diaryNo = no;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.userId = id;
	}
	public int getNo() {
		return diaryNo;
	}
	public void setNo(int no) {
		this.diaryNo = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getId() {
		return userId;
	}
	public void setId(String id) {
		this.userId = id;
	}
	@Override
	public String toString() {
		return "diary [no=" + diaryNo + ", title=" + title + ", content=" + content + ", regDate=" + regDate + ", id=" + userId
				+ "]";
	}
	
	
}
