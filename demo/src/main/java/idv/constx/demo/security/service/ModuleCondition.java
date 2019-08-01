package idv.constx.demo.security.service;



import idv.constx.demo.base.service.BaseCondition;
import idv.constx.demo.security.entity.Module;
 
/**
 * VIP会员
 */ 
public class ModuleCondition extends  BaseCondition { 

	private static final long serialVersionUID = 1L;
	/**模块名称*/
	private String name = null;
	/**标志符，用于授权名称（类似module:save）*/
	private String sn = null;
	/**是否启用*/
	private Integer enable = null;
	/**模块类型*/
	private Integer type = null;
	
	private Module parent = null;
	
	

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
	  this.name = value; 
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
	  this.sn = value; 
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
	  this.enable = value; 
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
	  this.type = value; 
	}
	public Module getParent() {
		return parent;
	}
	public void setParent(Module parent) {
		this.parent = parent;
	}

	
	

    
	
}