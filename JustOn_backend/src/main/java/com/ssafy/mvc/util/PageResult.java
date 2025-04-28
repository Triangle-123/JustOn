package com.ssafy.mvc.util;

/**
 *
 * 페이징 처리를 위한 변수를 표현
 * 
 */
public class PageResult {
	
	private int page; // 현재 요청된 페이지 번호
	private boolean prev, next; // 목록 하단의 이전 & 다음 버튼 표시 여부 판단
	private int beginPage, endPage, lastPage; // 목록 하단의 페이지 시작번호, 종료번호, 마지막페이지
	
	
	private static final int LIST_SIZE = 10;
	private static final int TAB_SIZE = 10;
	
	public PageResult(int page, int totalCount) {
		this(page, totalCount, LIST_SIZE, TAB_SIZE);
	}
	public PageResult(int page, int totalCount, int listSize) {
		this(page, totalCount, listSize, TAB_SIZE);
	}
	
	public PageResult(int page, int totalCount, int listSize, int tabSize) {
		this.page = page;
		this.lastPage = (totalCount % listSize) == 0 ? totalCount / listSize : totalCount / listSize + 1;  

		int tab = (page - 1) / tabSize + 1;
		this.beginPage = (tab - 1) * tabSize + 1; // 탭의 시작페이지
		this.endPage = tab * tabSize; // 탭의 끝페이지
		if(endPage > lastPage) endPage = lastPage;
		
		// 이전 번호가 있는지
		this.prev = beginPage != 1;
		
		// 다음 번호가 있는지
		this.next = endPage != lastPage;		
	}
	public int getPage() {
		return page;
	}
	public boolean isPrev() {
		return prev;
	}
	public boolean isNext() {
		return next;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	
	
}
