package idv.constx.demo.common.excel.renderer;


import org.apache.commons.lang3.StringUtils;

import idv.constx.demo.base.entity.IEnum;
import idv.constx.demo.common.excel.AbsCellRenderer;

/**
 * 返回枚举内容的指定字段值
 * 
 * @param <T>
 *
 * @author const.x
 */
public class EnumValueRenderer<T> extends AbsCellRenderer<T>{
	
	
	IEnum[] values;
	
	/**
	 * 
	 * 返回参照内容的指定字段值
	 * 
	 * @param e 指定需要取值的枚举
	 *
	 * @author const.x
	 * @createDate 2015年6月6日
	 */
	public  EnumValueRenderer(IEnum[] values){
		this.values = values;
	}

	@Override
	public Object contentRender(T entity, Object value, int row) {
		if(value == null){
			return "";
		}
		String str = String.valueOf(value);
		if(StringUtils.isBlank(str)){
			return "";
		}
		String[] array = str.split(",");
		StringBuilder sb = new StringBuilder();
		for (String string : array) {
			for(IEnum enumVal : values){
				if(enumVal.getValue() == Integer.valueOf(string)){
					sb.append(enumVal.getName()).append(";");
				}
			}
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	

}
