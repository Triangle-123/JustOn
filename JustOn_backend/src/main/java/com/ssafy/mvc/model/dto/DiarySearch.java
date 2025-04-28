package com.ssafy.mvc.model.dto;

public class DiarySearch {
	private int page;
	private int listSize;
	
	public DiarySearch() {
		this(1, 10);
	}

	public DiarySearch(int page) {
		this(page, 10);
	}
	
	public DiarySearch(int page, int listSize) {
		this.page = page;
		this.listSize = listSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getOffset() {
		return (page - 1) * listSize; // 
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}
