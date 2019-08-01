package idv.constx.demo.security.entity;


import java.util.ArrayList;
import java.util.List;

import idv.constx.demo.base.entity.IMapperEntity;
import idv.constx.demo.base.entity.TreeEntity;
import idv.constx.demo.security.entity.Module;
import idv.constx.demo.security.entity.User;
 
/**
 * 业务模块
 */ 
public class Module extends TreeEntity<Module> implements  IMapperEntity { 

	private static final long serialVersionUID = 1L;

	/**模块名称*/
	private String name = null;
	/**入口地址*/
	private String url = null;
	/**标志符，用于授权名称（类似module:save）*/
	private String sn = null;
	/**模块描述*/
	private String description = null;
	/**模块的排序号*/
	private Integer priority = null;
	/**是否启用*/
	private Integer enable = null;
	/**模块类型*/
	private Integer type = null;
	
	
	private List<Module> children = new ArrayList<Module>();

	/** 
	 * 获取模块名称 
	 * 
	 * @return String
	 */ 
	public String getName() {
	  return  this.name; 
	} 
	/** 
	 * 设置模块名称 
	 * 
	 * @param String
	 */ 
	public void setName(String value) {
	  super.setAttrbute(Module.FIELD_NAME, value);
	} 
	/** 
	 * 获取入口地址 
	 * 
	 * @return String
	 */ 
	public String getUrl() {
	  return  this.url; 
	} 
	/** 
	 * 设置入口地址 
	 * 
	 * @param String
	 */ 
	public void setUrl(String value) {
		 super.setAttrbute(Module.FIELD_URL, value);
	} 
	/** 
	 * 获取标志符，用于授权名称（类似module:save） 
	 * 
	 * @return String
	 */ 
	public String getSn() {
	  return  this.sn; 
	} 
	/** 
	 * 设置标志符，用于授权名称（类似module:save） 
	 * 
	 * @param String
	 */ 
	public void setSn(String value) {
		 super.setAttrbute(Module.FIELD_SN, value);
	} 
	/** 
	 * 获取模块描述 
	 * 
	 * @return String
	 */ 
	public String getDescription() {
	  return  this.description; 
	} 
	/** 
	 * 设置模块描述 
	 * 
	 * @param String
	 */ 
	public void setDescription(String value) {
		 super.setAttrbute(Module.FIELD_DESCRIPTION, value);
	} 
	/** 
	 * 获取模块的排序号 
	 * 
	 * @return Integer
	 */ 
	public Integer getPriority() {
	  return  this.priority; 
	} 
	/** 
	 * 设置模块的排序号 
	 * 
	 * @param Integer
	 */ 
	public void setPriority(Integer value) {
		 super.setAttrbute(Module.FIELD_PRIORITY, value);
	} 
	/** 
	 * 获取是否启用 
	 * 
	 * @return Integer
	 */ 
	public Integer getEnable() {
	  return  this.enable; 
	} 
	/** 
	 * 设置是否启用 
	 * 
	 * @param Integer
	 */ 
	public void setEnable(Integer value) {
		 super.setAttrbute(Module.FIELD_ENABLE, value);
	} 
	/** 
	 * 获取模块类型 
	 * 
	 * @return Integer
	 */ 
	public Integer getType() {
	  return  this.type; 
	} 
	/** 
	 * 设置模块类型 
	 * 
	 * @param Integer
	 */ 
	public void setType(Integer value) {
		 super.setAttrbute(Module.FIELD_TYPE, value);
	} 

	@Override
	public String getShowName() {
		return this.name;
	}

	
	
	
	public List<Module> getChildren() {
		return children;
	}
	public void setChildren(List<Module> children) {
		 super.setAttrbute(Module.FIELD_CHILDREN, children);
	}




	/**  字段名:主键 */
	public static final String FIELD_ID = "id";
	/**  字段名:模块名称 */
	public static final String FIELD_NAME = "name";
	/**  字段名:入口地址 */
	public static final String FIELD_URL = "url";
	/**  字段名:标志符，用于授权名称（类似module:save） */
	public static final String FIELD_SN = "sn";
	/**  字段名:模块描述 */
	public static final String FIELD_DESCRIPTION = "description";
	/**  字段名:模块的排序号 */
	public static final String FIELD_PRIORITY = "priority";
	/**  字段名:是否启用 */
	public static final String FIELD_ENABLE = "enable";
	/**  字段名:模块类型 */
	public static final String FIELD_TYPE = "type";
	/**  字段名:上级业务模块 */
	public static final String FIELD_PARENT = "parent";
	/**  字段名:创建人 */
	public static final String FIELD_CREATER = "creater";
	/**  字段名:创建时间 */
	public static final String FIELD_CREATETIME = "createtime";
	/**  字段名:最后修改人 */
	public static final String FIELD_MODIFYER = "modifyer";
	/**  字段名:最后修改时间 */
	public static final String FIELD_MODIFYTIME = "modifytime";
}