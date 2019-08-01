package idv.constx.demo.base.view;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.common.DateUtils;


/**
 * 视图基类
 * 
 *
 * @author const.x
 */
public class View extends HashMap<String, Object> {

	public View(){
		
	}
	
	public View(IDEntity entity) {
		List<Field> fields = getFields(entity);
		for (Field field : fields) {
			if(field.getName().startsWith("FIELD_")){
				continue;
			}
			Object val = getValueByField(entity, field);
			if(val != null && !val.equals("")){
				if(val  instanceof IDEntity){
					IDEntity value = (IDEntity)val;
					this.put(field.getName(), new View(value));
					this.put(field.getName()+"Str", value.getShowName());
				}else if(val  instanceof Date){
					this.put(field.getName(), DateUtils.toDateTimeString((Date)val));
				}else{
					this.put(field.getName(), val);
				}
			}
		}
	}

	
	public void putShortString(String field) {
		String str = (String) super.get(field);
		if (str != null && str.length() > 128) {
			super.put(field + "Short", str.substring(0, 128) + "...");
		} else {
			super.put(field + "Short", str);
		}
	}
	
	
	public String toJson(){
		JSONObject json = new JSONObject();
		for (Entry<String, Object> entry : this.entrySet()) {
			try {
				json.put(entry.getKey(), entry.getValue());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return json.toString();
	}
	
	public static List<Field> getFields(Object obj) {
		List<Field> fields = new ArrayList<Field>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field);
		}
		for (Class<?> superClass = clazz.getSuperclass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			for (Field field : superClass.getDeclaredFields()) {
				int modifiers = field.getModifiers();
				if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
					continue;
				}
				fields.add(field);
			}
		}
		return fields;
	}

	public static Object getValueByField(Object obj, Field field) {
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
				value = null;
			}
		}
		//防止在界面上显示 null
		if(value == null){
			value = "";
		}
		//解决数字长度超过8位后显示科学计数法的问题
		else if(value instanceof Double){
			DecimalFormat nf = new DecimalFormat("0.00");  
			nf.setGroupingUsed(false);  
			value = nf.format(value);
		}
		
		return value;
	}

}
