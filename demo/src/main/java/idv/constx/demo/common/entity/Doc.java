package idv.constx.demo.common.entity;


import idv.constx.demo.base.entity.IMapperEntity;
import idv.constx.demo.base.entity.IDEntity;
 
/**
 * 文件档案
 */ 
public class Doc extends IDEntity implements  IMapperEntity { 

	private static final long serialVersionUID = 1L;

	/**文件名称*/
	private String name = null;
	/**文件类型*/
	private String suffix = null;
	/**存储路径（相对）*/
	private String path = null;
	/**MD5码*/
	private String mD5 = null;
	/**(缓存文件)过期时间*/
	private String expiration = null;
	/**(缓存文件)key值*/
	private String cacheKey = null;

	/** 
	 * 获取文件名称 
	 * 
	 * @return String
	 */ 
	public String getName() {
	  return  this.name; 
	} 
	/** 
	 * 设置文件名称 
	 * 
	 * @param String
	 */ 
	public void setName(String value) {
	  this.name = value; 
	} 
	/** 
	 * 获取文件类型 
	 * 
	 * @return String
	 */ 
	public String getSuffix() {
	  return  this.suffix; 
	} 
	/** 
	 * 设置文件类型 
	 * 
	 * @param String
	 */ 
	public void setSuffix(String value) {
	  this.suffix = value; 
	} 
	/** 
	 * 获取存储路径（相对） 
	 * 
	 * @return String
	 */ 
	public String getPath() {
	  return  this.path; 
	} 
	/** 
	 * 设置存储路径（相对） 
	 * 
	 * @param String
	 */ 
	public void setPath(String value) {
	  this.path = value; 
	} 
	/** 
	 * 获取MD5码 
	 * 
	 * @return String
	 */ 
	public String getMD5() {
	  return  this.mD5; 
	} 
	/** 
	 * 设置MD5码 
	 * 
	 * @param String
	 */ 
	public void setMD5(String value) {
	  this.mD5 = value; 
	} 
	/** 
	 * 获取(缓存文件)过期时间 
	 * 
	 * @return String
	 */ 
	public String getExpiration() {
	  return  this.expiration; 
	} 
	/** 
	 * 设置(缓存文件)过期时间 
	 * 
	 * @param String
	 */ 
	public void setExpiration(String value) {
	  this.expiration = value; 
	} 
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

	/**  字段名:文件名称 */
	public static final String FIELD_NAME = "name";
	/**  字段名:文件类型 */
	public static final String FIELD_SUFFIX = "suffix";
	/**  字段名:存储路径（相对） */
	public static final String FIELD_PATH = "path";
	/**  字段名:MD5码 */
	public static final String FIELD_MD5 = "mD5";
	/**  字段名:(缓存文件)过期时间 */
	public static final String FIELD_EXPIRATION = "expiration";
	/**  字段名:(缓存文件)key值 */
	public static final String FIELD_CACHEKEY = "cacheKey";
}