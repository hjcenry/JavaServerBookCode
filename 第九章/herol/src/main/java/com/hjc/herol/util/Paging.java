package com.hjc.herol.util;

import java.util.List;

public class Paging {
	// 每页显示的数量
	private int countPerPage;
	// 总记录数
	private int totalItem;
	// 当前页面
	private int currentPageIndex;
	// 页面总数
	private int pageCount;
	// 页面显示的页码数量
	private int everyPageNum = 20;
	// 每页的起始编号
	private int pageStartRow;
	private int beginPageIndex;
	private int endPageIndex;
	private List list;

	public Paging() {

	}

	public Paging(Integer countPerPage, int totalItem, Integer currentPageIndex) {
		this.countPerPage = countPerPage == null ? 10 : countPerPage;
		this.totalItem = totalItem;
		this.currentPageIndex = currentPageIndex == null ? 1
				: (currentPageIndex == null ? 1 : currentPageIndex);
		this.pageCount = (int) Math.ceil(totalItem * 1.0 / this.countPerPage);
		this.pageStartRow = (this.currentPageIndex - 1) * this.countPerPage;
		this.beginPageIndex = this.currentPageIndex - everyPageNum / 2 > 0 ? this.currentPageIndex
				- everyPageNum / 2
				: 1;
		this.endPageIndex = (this.currentPageIndex + everyPageNum / 2 - 1) > pageCount ? pageCount
				: (this.currentPageIndex + everyPageNum / 2 - 1);

	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getEveryPageNum() {
		return everyPageNum;
	}

	public void setEveryPageNum(int everyPageNum) {
		this.everyPageNum = everyPageNum;
	}

	public int getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

}
