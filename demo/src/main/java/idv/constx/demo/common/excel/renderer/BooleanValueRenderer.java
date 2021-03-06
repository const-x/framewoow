package idv.constx.demo.common.excel.renderer;

import org.apache.commons.lang3.StringUtils;

import idv.constx.demo.common.excel.AbsCellRenderer;

public class BooleanValueRenderer<T> extends AbsCellRenderer<T>{

	@Override
	public Object contentRender(T entity, Object value, int row) {
		if(value == null){
			return "";
		}
		String str = String.valueOf(value);
		if(StringUtils.isBlank(str)){
			return "";
		}
		Integer val = Integer.valueOf(str);
		if(val == 1){
			return "是";
		}else{
			return "否";
		}
	}

	
}
