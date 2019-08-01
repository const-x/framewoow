package idv.const_x.jdbc.table.meta;

import org.apache.commons.lang3.StringUtils;

import idv.const_x.tools.coder.CamelCaseUtils;



public class Field {
	//字段名
	private String name = null;
	//数据库字段名
	private String column = null;
	//类别
	private Type type = null;
	//中文名
	private String alias = null;
	//是否可为空
	private boolean nullable = true;
	//是否唯一
	private boolean unique = false;
	//是否主键
	private boolean primary = false;
	//默认值
	private Object defaultValue = null;
	
	public Field(String name, Type type, String alias) {
		this.name = name.trim();
		this.type = type;
		this.alias = alias.trim();
	}
	

	/** 
	 * 获取字段名 
	 * 
	 * @return String 字段名 
	 */ 
	public String getName() { 
	  return  this.name; 
	} 
	/** 
	 * 设置字段名 
	 * 
	 * @param value 字段名
	 */ 
	public void setName(String value) { 
	  this.name = value; 
	} 

	/** 
	 * 获取类别 
	 * 
	 * @return Type 类别 
	 */ 
	public Type getType() { 
	  return  this.type; 
	} 
	/** 
	 * 设置类别 
	 * 
	 * @param value 类别
	 */ 
	public void setType(Type value) { 
	  this.type = value; 
	} 

	/** 
	 * 获取中文名 
	 * 
	 * @return String 中文名 
	 */ 
	public String getAlias() { 
	  return  this.alias; 
	} 
	/** 
	 * 设置中文名 
	 * 
	 * @param value 中文名
	 */ 
	public void setAlias(String value) { 
	  this.alias = value; 
	} 



	/** 
	 * 是否是否主键
	 * 
	 * @return boolean 是否主键
	 */ 
	public boolean isPrimary() { 
	  return  this.primary; 
	} 
	/** 
	 * 设置是否主键
	 * 
	 * @param value 是否主键
	 */ 
	public void setPrimary(boolean value) { 
	  this.primary = value; 
	} 

	
	/** 
	 * 是否是否可为空 
	 * 
	 * @return boolean 是否可为空 
	 */ 
	public boolean isNullable() { 
	  return  this.nullable; 
	} 
	/** 
	 * 设置是否可为空  默认为true
	 * 
	 * @param value 是否可为空
	 */ 
	public void setNullable(boolean value) { 
	  this.nullable = value; 
	} 
	
	
	/** 
	 * 是否是否唯一 
	 * 
	 * @return boolean 是否唯一 
	 */ 
	public boolean isUnique() { 
	  return  this.unique; 
	} 
	/** 
	 * 设置是否唯一   默认为false
	 * 
	 * @param value 是否唯一
	 */ 
	public void setUnique(boolean value) { 
	  this.unique = value; 
	} 

	/** 
	 * 获取默认值 
	 * 
	 * @return Object 默认值 
	 */ 
	public Object getDefaultValue() { 
	  return  this.defaultValue; 
	} 
	/** 
	 * 设置默认值 
	 * 
	 * @param value 默认值
	 */ 
	public void setDefaultValue(Object value) { 
	  this.defaultValue = value; 
	} 


	/** 
	 * 获取数据库列名
	 * 
	 * @return Object 默认值 
	 */ 
	public String getColumn() {
		if(StringUtils.isBlank(this.column)){
			if (!type.isSimpleType() && !this.name.toLowerCase().endsWith("_id")) {
				column =  (this.name + "_id").toLowerCase();
			}else{
				column = CamelCaseUtils.toUnderlineName(this.name);
			}
			column = column.toUpperCase();
		}
		return column;
	}

    /**
     * 设置数据库列名
     * @param column
     */
	public void setColumn(String column) {
		this.column = column.trim();
	}

    
	
}
