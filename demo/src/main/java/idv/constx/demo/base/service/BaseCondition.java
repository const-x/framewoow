package idv.constx.demo.base.service;

import java.io.Serializable;

public class BaseCondition implements  Serializable { 

	private static final long serialVersionUID = 1L;
	
	/**关键字*/
	private String keywords = null;

	/** 
	 * 获取关键字 
	 * 
	 * @return String
	 */ 
	public String getKeywords() {
	  return  this.keywords; 
	} 
	/** 
	 * 设置关键字 
	 * 
	 * @param String
	 */ 
	public void setKeywords(String value) {
	  this.keywords = value; 
	} 

}
