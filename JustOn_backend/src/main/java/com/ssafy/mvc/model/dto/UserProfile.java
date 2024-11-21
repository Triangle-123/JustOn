package com.ssafy.mvc.model.dto;

public class UserProfile {
	private int fileNo;
	private String filePath;
	private String oriName;
	private String systemName;
	private String uploadDate;
	private String userId;
	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserProfile(int no, String filePath, String oriName, String systemName, String uploadDate, String id) {
		super();
		this.fileNo = no;
		this.filePath = filePath;
		this.oriName = oriName;
		this.systemName = systemName;
		this.uploadDate = uploadDate;
		this.userId = id;
	}
	public int getNo() {
		return fileNo;
	}
	public void setNo(int no) {
		this.fileNo = no;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOriName() {
		return oriName;
	}
	public void setOriName(String oriName) {
		this.oriName = oriName;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String id) {
		this.userId = id;
	}
	@Override
	public String toString() {
		return "UserProfile [no=" + fileNo + ", filePath=" + filePath + ", oriName=" + oriName + ", systemName="
				+ systemName + ", uploadDate=" + uploadDate + ", id=" + userId + "]";
	}
}
