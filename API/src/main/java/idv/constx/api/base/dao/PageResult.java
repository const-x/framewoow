package idv.constx.api.base.dao;

import java.util.List;

public class PageResult<T> {

	private int page = 0;

	private int pageSize = 0;

	private int total = 0;
	
	private int totalPage = 0;

	private List<T> datas = null;

	/**
	 * @return the page
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the total
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public void setTotal(int total) {
		this.total = total;
		if(total%pageSize > 0){
			this.totalPage = total/pageSize + 1;
		}else{
			this.totalPage = total/pageSize;
		}
	}

	
	
	public int getTotalPage() {
		return totalPage;
	}

	

	/**
	 * @return the datas
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public List<T> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

}
