package idv.constx.demo.base.entity;


import java.util.List;

import com.google.common.collect.Lists;



/**
 * 含层级关系的实体
 *
 * @author const.x
 */
public abstract class TreeEntity<T extends TreeEntity> extends IDEntity{
  
	// 上级
	private T parent = null;
	
	
	private List<T> children = Lists.newArrayList();
	
	/** 
	 * 获取上级
	 * 
	 * @return User 上级
	 */ 
	public T getParent() { 
	  return  this.parent; 
	} 
	/** 
	 * 设置上级
	 * 
	 * @param value 上级
	 */ 
	public void setParent(T value) { 
		this.setAttrbute(FIELD_PARENT, value);
	}
	
	public void addChild(T value) {
		children.add(value);
		super.modifiedFields.add(FIELD_CHILDREN);
	}

	public void addChildren(List<T> values) {
		if (children == null) {
			children = values;
		} else {
			children.addAll(values);
		}
		super.modifiedFields.add(FIELD_CHILDREN);
	}

	public List<T> getChildren() {
		return children;
	}
	
	public void setChildren(List<T> values) {
		this.setAttrbute("children", values);
	}
	
	/**  字段名:上级 */
	public static final String FIELD_PARENT= "parent";
	/**  字段名:数据等级 */
	public static final String FIELD_CHILDREN= "children";
}
