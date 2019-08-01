package idv.constx.demo.common.entity;


import idv.constx.demo.base.entity.IMapperEntity;
import idv.constx.demo.base.entity.IDEntity;
 
/**
 * 档案引用
 */ 
public class DocRel extends IDEntity implements  IMapperEntity { 

	private static final long serialVersionUID = 1L;

	/**(缓存文件)key值*/
	private String cacheKey = null;

	/** 
	 * 获取(缓存文件)key值 
	 * 
	 * @return String
	 */ 
	public String getCacheKey() {
	  return  this.cacheKey; 
	} 
	/** 
	 * 设置(缓存文件)key值 
	 * 
	 * @param String
	 */ 
	public void setCacheKey(String value) {
	  this.cacheKey = value; 
	} 

	@Override
	public String getShowName() {
		return null;
	}

	/**  字段名:(缓存文件)key值 */
	public static final String FIELD_CACHEKEY = "cacheKey";
}