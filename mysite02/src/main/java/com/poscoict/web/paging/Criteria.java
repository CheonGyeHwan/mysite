package com.poscoict.web.paging;

public class Criteria {
	private int pageNum;
	private int amount;
	private int skip;
	
	public Criteria() {
		this.pageNum = 1;
		this.amount = 5;
		this.skip = 0;
	}
	
	public Criteria(int pageNum) {
		this.pageNum = pageNum;
		this.amount = 5;
		this.skip = (pageNum - 1) * amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.skip = (pageNum - 1) * amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		this.skip = (pageNum - 1) * amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}
	
}
