package com.ssafy.mvc.model.dto;

public class ReviewSearch {
	private int page;
	private int listSize;
	
	public ReviewSearch() {
		this(1, 10); // 기본 페이지는 1번페이지
		// 기본 목록 수는 10개
	}

	public ReviewSearch(int page) {
		this(page, 10);
	}
	
	public ReviewSearch(int page, int listSize) {
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
		return (page - 1) * listSize;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	
	
}
