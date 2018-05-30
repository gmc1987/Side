/**
 * 
 */
package com.tssa.common.mode;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author gmc
 * @see 分页对象
 */
public class PageMode<T> implements Serializable {

	private static final long serialVersionUID = 6294833497024371877L;
	
	private static final int MAX_PAGE_SIZE = 100;
	
	/**
	 * 数据集
	 */
	private List<T> records;
	
	/**
	 * 当前页
	 */
	private int pageNumber;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 页记录数
	 */
	private int pageSize;
	
	/**
	 * 总记录数
	 */
	private int count;
	
	/**
	 * 是否还有下一页
	 */
	private boolean hasNextPage;
	
	/**
	 * 是否还有上一页
	 */
	private boolean hasPreviousPage;
	
	/**
	 * 第一个索引
	 */
	@SuppressWarnings("unused")
	private int firstIndex;
	
	/**
	 * 最后一个索引
	 */
	@SuppressWarnings("unused")
	private int lastIndex;
	
	public PageMode(){
		this.records = Collections.emptyList();
		this.pageNumber = 1;
		this.pageSize = 10;
		this.pageCount = 1;
		this.count = 0;
		this.hasNextPage = false;
		this.hasPreviousPage = false;
		this.firstIndex = 1;
		this.lastIndex = 10;
	}
	
	public PageMode(List<T> records, int pageNumber, int pageSize, int count){
		this.records = records;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.count = count;
		
		if(this.pageSize > MAX_PAGE_SIZE){
			this.pageSize = MAX_PAGE_SIZE;
		}
		
		if(this.pageSize > 0){
			this.pageCount = (this.count % this.pageSize == 0) ? (this.count / this.pageSize) 
					: (this.count / this.pageSize) + 1;
		}else{
			this.pageSize = MAX_PAGE_SIZE;
			this.pageCount = 1;
		}
		
		if(this.pageCount < 1){
			this.pageCount = 1;
		}
		
		if(this.pageNumber > 1) {
			this.pageNumber = this.pageNumber / this.pageSize + 1;
		}
		
		if(this.pageNumber > this.pageCount){
			this.pageNumber = this.pageCount;
		}
		
		if(this.pageNumber < 1){
			this.pageNumber = 1;
		}
		
		int offset = (this.pageNumber - 1) * this.pageSize;
		if (offset < 0) {
			offset = 0;
		}
		
		this.records = records;
		
		if(this.pageSize == MAX_PAGE_SIZE || this.pageCount == 1){
			this.count = this.pageSize = this.records.size();
		}
		
		this.hasNextPage = this.pageCount > this.pageNumber;
		this.hasPreviousPage = this.pageNumber > 1;
		this.firstIndex = this.getFirstIndex();
		this.lastIndex = this.getLastIndex();
	}
	
	public PageMode(List<T> records, int pageNumber, int pageSize, int pageCount, int count, 
			boolean hasNextPage, boolean hasPreviousPage){
		this.records = records;
		this.pageNumber = pageNumber;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public int getFirstIndex() {
		return (getPageNumber() - 1) * this.pageSize + 1;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		final int fullPage = getFirstIndex() + this.pageSize - 1;
		return getCount() < fullPage ? getCount() : fullPage;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
}
