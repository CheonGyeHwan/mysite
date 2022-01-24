package com.poscoict.mysite.vo;

public class PagingVo {
	private String keyword;
	private int currentPage;
	private int boardAmount;
	private int skip;
	private int startPage;
	private int endPage;
	private int total;
	private boolean prev;
	private boolean next;
	
	public PagingVo(int currentPage, int total) {
		this.keyword = null;
		this.currentPage = currentPage;
		this.total = total;
		this.boardAmount = 10;
		this.skip = 0 + boardAmount * (currentPage - 1);
		
		this.endPage = (int)(Math.ceil(currentPage/5.0)) * 5;
		this.startPage = endPage - 4;
		
		int realEnd = (int)(Math.ceil(total * 1.0 / boardAmount));
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = startPage > 1;
		this.next = endPage < realEnd;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getBoardAmount() {
		return boardAmount;
	}

	public void setBoardAmount(int boardAmount) {
		this.boardAmount = boardAmount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

}
