package com.poscoict.web.paging;

public class PageMakerDto {
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int total;
	private Criteria cri;
	
	public PageMakerDto(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		this.endPage = (int)(Math.ceil(cri.getPageNum()/5.0)) * 5;
		this.startPage = endPage - 4;
		
		int realEnd = (int)(Math.ceil(total * 1.0 / cri.getAmount()));
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = startPage > 1;
		this.next = endPage < realEnd;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
}
