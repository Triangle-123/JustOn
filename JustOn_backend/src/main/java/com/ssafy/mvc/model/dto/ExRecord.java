package com.ssafy.mvc.model.dto;

public class ExRecord {
	private int exRecordNo;
	private String userId;
	private String exDate;
	private String part;
	private int partValue;
	public ExRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExRecord(int exRecordNo, String userId, String exDate, String part, int partValue) {
		super();
		this.exRecordNo = exRecordNo;
		this.userId = userId;
		this.exDate = exDate;
		this.part = part;
		this.partValue = partValue;
	}
	public int getExRecordNo() {
		return exRecordNo;
	}
	public void setExRecordNo(int exRecordNo) {
		this.exRecordNo = exRecordNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getExDate() {
		return exDate;
	}
	public void setExDate(String exDate) {
		this.exDate = exDate;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public int getPartValue() {
		return partValue;
	}
	public void setPartValue(int partValue) {
		this.partValue = partValue;
	}
	@Override
	public String toString() {
		return "ExRecord [exRecordNo=" + exRecordNo + ", userId=" + userId + ", exDate=" + exDate + ", part=" + part
				+ ", partValue=" + partValue + "]";
	}
	
}
