package idv.constx.demo.util.dwz;

import java.io.Serializable;



/** 
 * 	
 * @author 	const.x
 * Version  1.1.0
 * @since   2012-6-11 上午10:28:30 
 */

public class Page implements Serializable{
	private static final long serialVersionUID = -8934124995382665621L;
	
	public final static String ORDER_DIRECTION_ASC = "ASC"; 
	public final static String ORDER_DIRECTION_DESC = "DESC";
	
	/**
	 * 默认每页记录数
	 */
	private static final int DEFAULT_PAGE_SIZE = 15;
	/**
	 * 当前页码
	 */
	private int pageNum = 1;
	private int numPerPage = DEFAULT_PAGE_SIZE;
	private String orderField = "id";
	private String orderDirection = ORDER_DIRECTION_DESC;
	
	/**
	 * 总页数
	 */
	private int totalPage = 1;
	
	
	public int getStartIndex() {
		return startIndex;
	}

	private int startIndex = 0;

	/**
	 * 前一页
	 */
	private int prePage = 1;

	/**
	 * 下一页
	 */
	private int nextPage = 1;
	
	/**
	 * 总记录数
	 */
	private long totalCount = 0;

	/**  
	 * 返回 pageNum 的值   
	 * @return pageNum  
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**  
	 * 设置 pageNum 的值  
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum > 0 ? pageNum : 1;
		this.startIndex = (this.pageNum - 1) * this.numPerPage;
	}

	/**  
	 * 返回 numPerPage 的值   
	 * @return numPerPage  
	 */
	public int getNumPerPage() {
		return numPerPage;
	}

	/**  
	 * 设置 numPerPage 的值  
	 * @param numPerPage
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage > 0 ? numPerPage : 10;
		this.startIndex = (this.pageNum - 1) * this.numPerPage;
	}

	/**  
	 * 返回 orderField 的值   
	 * @return orderField  
	 */
	public String getOrderField() {
		return orderField;
	}

	/**  
	 * 设置 orderField 的值  
	 * @param orderField
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**  
	 * 返回 orderDirection 的值   
	 * @return orderDirection  
	 */
	public String getOrderDirection() {
		return orderDirection;
	}

	/**  
	 * 设置 orderDirection 的值  
	 * @param orderDirection
	 */
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**  
	 * 返回 totalPage 的值   
	 * @return totalPage  
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**  
	 * 设置 totalPage 的值  
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**  
	 * 返回 prePage 的值   
	 * @return prePage  
	 */
	public int getPrePage() {
		prePage = pageNum - 1;
		if (prePage < 1) {
			prePage = 1;
		}
		return prePage;
	}

	/**  
	 * 设置 prePage 的值  
	 * @param prePage
	 */
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	/**  
	 * 返回 nextPage 的值   
	 * @return nextPage  
	 */
	public int getNextPage() {
		nextPage = pageNum + 1;
		if (nextPage > totalPage) {
			nextPage = totalPage;
		}
		return nextPage;
	}

	/**  
	 * 设置 nextPage 的值  
	 * @param nextPage
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**  
	 * 返回 totalCount 的值   
	 * @return totalCount  
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**  
	 * 设置 totalCount 的值  
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		totalPage = (int)(totalCount - 1) / this.numPerPage + 1;
	}

	
	//适配easyUI
	public void setRows(int rows) {
		this.setNumPerPage(rows);
	}
	
	public void setPage(int page){
		this.setPageNum(page);
	}
	
	public void setSort(String field){
		this.setOrderField(field);
	}

	public void setOrder(String dire){
		this.setOrderDirection(dire);
	}

	
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[page：").append(this.pageNum).append(",size：").append(this.numPerPage)
		  .append(",start：").append(this.startIndex).append(",total：").append(this.totalCount)
		  .append(",order：").append(this.orderField).append(",direction：").append(this.orderDirection)
		  .append("]");
		return sb.toString();
	}

	
}
