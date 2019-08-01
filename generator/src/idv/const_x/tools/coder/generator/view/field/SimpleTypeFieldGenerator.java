package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.Required;

import org.apache.commons.lang3.StringUtils;

public abstract class SimpleTypeFieldGenerator extends AbsFieldGenerator{
	
	protected String getStyle(Field field){
		String style = "";
		Type type = field.getType();
		
		
		if(field.getValidates().size() > 0){
			StringBuilder classes = new StringBuilder("validType:[");
			StringBuilder message = new StringBuilder("message:{");
			boolean setMsg = false;
			for(AbsValidateStyles clazz : field.getValidates()){
				classes.append("'").append(clazz.getStyle()).append("',");
				if(StringUtils.isNotBlank(clazz.getMessage())){
					message.append(clazz.getMessage()).append(",");
					setMsg = true;
				}
			}
			if(setMsg){
				message.deleteCharAt(message.lastIndexOf(","));
				message.append("}");
				classes.append(message.toString());
			}else{
				classes.deleteCharAt(classes.lastIndexOf(","));
			}
			classes.append("]");
			style = classes.toString();
		}
		return style;
	}
	

}
