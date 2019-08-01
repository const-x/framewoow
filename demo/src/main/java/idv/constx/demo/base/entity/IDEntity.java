package idv.constx.demo.base.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import idv.constx.demo.common.exception.ExceptionUtils;


/**
 * 
 * 可唯一标识的实体
 * 
 * @author const.x
 */
public abstract class IDEntity implements Serializable {
	
	private static final long serialVersionUID = 8808240239802033985L;
	
	protected Set<String> modifiedFields = new HashSet<String>();
	
	public Set<String> getModifiedFields() {
		return modifiedFields;
	}


	public static final String FIELD_ID = "id";
	public static final String FIELD_CREATER = "creater";
	public static final String FIELD_MODIFYER = "modifyer";
	public static final String FIELD_CREATETIME = "createtime";
	public static final String FIELD_MODIFYTIME = "modifytime";

	// 唯一标识
	private Long id = null;

	// 创建人
	private Long creater = null;

	// 最后修改人
	private Long modifyer = null;

	// 创建时间
	private String createtime = null;

	// 最后修改时间
	private String modifytime = null;

	/**
	 * 获取当前实体在前台界面上展示的内容
	 * 一般是由实体中具有代表性的内容组合而成，用户通过展示的内容，就可以快速的定位到相应的实体记录
	 * @return
	 */
	public abstract String getShowName();
	
	/**
	 * 获取唯一标识
	 * 
	 * @return Long 唯一标识
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置唯一标识
	 * 
	 * @param value 唯一标识
	 */
	public void setId(Long value) {
		this.setAttrbute(FIELD_ID, value);
	}

	/**
	 * 获取创建人
	 * 
	 * @return String 创建人
	 */
	public Long getCreater() {
		return this.creater;
	}

	/**
	 * 设置创建人
	 * 
	 * @param value 创建人
	 */
	public void setCreater(Long value) {
		this.setAttrbute(FIELD_CREATER, value);
	}

	/**
	 * 获取最后修改人
	 * 
	 * @return String 最后修改人
	 */
	public Long getModifyer() {
		return this.modifyer;
	}

	/**
	 * 设置最后修改人
	 * 
	 * @param value 最后修改人
	 */
	public void setModifyer(Long value) {
		this.setAttrbute(FIELD_MODIFYER, value);
	}

	/**
	 * 获取创建时间
	 * 
	 * @return String 创建时间
	 */
	public String getCreatetime() {
		return this.createtime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param value 创建时间
	 */
	public void setCreatetime(String value) {
		// mySql datetime 转换为 Sting时 会在后面添加.0
		if (null!=value && value.endsWith(".0")) {
			value = value.substring(0, value.length() - 2);
		}
		this.setAttrbute(FIELD_CREATETIME, value);
	}

	/**
	 * 获取最后修改时间
	 * 
	 * @return String 最后修改时间
	 */
	public String getModifytime() {
		return this.modifytime;
	}

	/**
	 * 设置最后修改时间
	 * 
	 * @param value 最后修改时间
	 */
	public void setModifytime(String value) {
		// mySql datetime 转换为 Sting时 会在后面添加.0
		if (null!=value && value.endsWith(".0")) {
			value = value.substring(0, value.length() - 2);
		}
		this.setAttrbute(FIELD_MODIFYTIME, value);
	}
	
	public void setAttrbute(String field,Object value){
		try {
			modifiedFields.add(field);
			Field f = this.getClass().getDeclaredField(field);
			if (f.isAccessible()) {
				f.set(this, value);
			} else {
				f.setAccessible(true);
				f.set(this, value);
				f.setAccessible(false);
			}
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
	}
	
	public void setAttrbuteWhenExist(String field,Object value){
		modifiedFields.add(field);
		try {
			Field f = null;
			try {
				f = this.getClass().getDeclaredField(field);
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
			if(f != null){
				if (f.isAccessible()) {
					f.set(this, value);
				} else {
					f.setAccessible(true);
					f.set(this, value);
					f.setAccessible(false);
				}
			}
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
			
	}
	
	public Object getAttrbute(String field){
		Object value = null;
		try {
			Field f = this.getClass().getDeclaredField(field);
			if (f.isAccessible()) {
				value = f.get(this);
			} else {
				f.setAccessible(true);
				value = f.get(this);
				f.setAccessible(false);
			}
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
		return value;
	}
	
	
	
	
	@Override
	public String toString() {
		return "{'id':"+this.getId()+",'showname':'"+this.getShowName()+"'}";
	}

	/**
	 * 复制
	 * @param source
	 * @return
	 */
	public static  <T extends IDEntity> T clone(T source){
		return clone(source,null);
	}
	/**
	 * 复制指定字段
	 * @param source
	 * @param fields
	 * @return
	 */
	public static  <T extends IDEntity> T clone(T source,Collection<String> fields){
		try {
			T news = (T) source.getClass().newInstance();
			for (Class<?> clazz = source.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				for (Field field : clazz.getDeclaredFields()) {
					if(fields != null){
						if(!fields.contains(field.getName())){
							continue;
						}
					}
					int modifiers = field.getModifiers();
					if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
						continue;
					}
					if (field.isAccessible()) {
						Object value = field.get(source);
						field.set(news, getCloneValue(value));
					} else {
						field.setAccessible(true);
						Object value = field.get(source);
						field.set(news, getCloneValue(value));
						field.setAccessible(false);
					}
				}
			}
			return news;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 转换为json对象
	 * @param source
	 * @return
	 */
	public static  <T extends IDEntity> JSONObject json(T source){
		return json(source,null);
	}
	
	/**
	 * 指定字段转换为json对象
	 * @param source
	 * @param fields
	 * @return
	 */
	public static  <T extends IDEntity> JSONObject json(T source,Collection<String> fields){
		try {
			JSONObject json = new JSONObject();
			for (Class<?> clazz = source.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				for (Field field : clazz.getDeclaredFields()) {
					if(fields != null){
						if(!fields.contains(field.getName())){
							continue;
						}
					}
					int modifiers = field.getModifiers();
					if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
						continue;
					}
					if (field.isAccessible()) {
						Object value = field.get(source);
						json.put(field.getName(), value);
					} else {
						field.setAccessible(true);
						Object value = field.get(source);
						json.put(field.getName(), getJsonValue(value));
						field.setAccessible(false);
					}
				}
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Object getJsonValue(Object value) throws Exception {
		if (value == null) {
			return value;
		}
		if (value instanceof IDEntity) {
			value = IDEntity.json((IDEntity)value);
			return value;
		}
		if (value instanceof Collection) {
			JSONArray array = new JSONArray();
			Collection source = (Collection) value;
			for (Object o : source) {
				array.put(getJsonValue(o));
			}
			return array;
		}
		return value;
	}
	
	
	private static Object getCloneValue(Object value) throws Exception {
		if (value == null) {
			return value;
		}
		if (value instanceof IDEntity) {
			value = IDEntity.clone((IDEntity)value);
			return value;
		}
		if (value instanceof Collection) {
			Collection source = (Collection) value;
			Collection dest = source.getClass().newInstance();
			for (Object o : source) {
				dest.add(getCloneValue(o));
			}
			return dest;
		}
		return value;
	}

}
