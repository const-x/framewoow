package idv.constx.demo.common.excel.renderer;

import java.lang.reflect.Field;

import idv.constx.demo.common.excel.AbsCellRenderer;
import idv.constx.demo.common.exception.ExceptionUtils;

/**
 * 返回参照内容的指定字段值
 * 
 * @param <T>
 *
 * @author const.x
 */
public class RefValueRenderer<T> extends AbsCellRenderer<T>{
	
	
	private String refield = "name";
	
	/**
	 * 
	 * 返回参照内容的指定字段值
	 * 
	 * @param refield 指定需要取值的字段
	 *
	 * @author const.x
	 * @createDate 2015年6月6日
	 */
	public RefValueRenderer(String refield){
		this.refield = refield;
	}

	@Override
	public Object contentRender(T entity, Object value, int row) {
		if(value == null){
			return "";
		}
		Field field = this.getField(value.getClass(), refield);
		if(field == null){
			ExceptionUtils.throwBizException("未找到指定字段：" + refield + " class:" + value.getClass());
		}
		return this.getFieldValue(field, value);
	}

	
	
	private Field getField(Class<?> clazz,String name) {
		for (Field field : clazz.getDeclaredFields()) {
			if(field.getName().equals(name)){
				return field;
			}
		}
		for (Class<?> superClass = clazz.getSuperclass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			for (Field field : superClass.getDeclaredFields()) {
				if(field.getName().equals(name)){
					return field;
				}
			}
		}
		return null;
	}
	
    private Object getFieldValue(Field field,Object obj){
    	Object value = null;
		if (field != null) {
			try {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
    }
	
}
