package com.ssafy.mvc.model.dto;

public class VideoReview {
	private int reviewNo;
	private String content;
	private String writer;
	private String regDate;
	private int videoNo;
	private int parentNo;
	private String userId;
	public VideoReview() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoReview(int reviewNo, String content, String writer, String regDate, int videoNo, int parentNo,
			String userId) {
		super();
		this.reviewNo = reviewNo;
		this.content = content;
		this.writer = writer;
		this.regDate = regDate;
		this.videoNo = videoNo;
		this.parentNo = parentNo;
		this.userId = userId;
	}
	public int getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(int videoNo) {
		this.videoNo = videoNo;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "VideoReview [reviewNo=" + reviewNo + ", content=" + content + ", writer=" + writer + ", regDate="
				+ regDate + ", videoNo=" + videoNo + ", parentNo=" + parentNo + ", userId=" + userId + "]";
	}
	
}