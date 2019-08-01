package idv.const_x.tools.coder.generator.valdate;

import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.jdbc.table.meta.Type;

public class StaticValidateFactory {
  
	public static AbsValidateStyles getValidate(Type type){
		AbsValidateStyles validate = null;
		if((type == SimpleTypeEnum.INTEGER || type == SimpleTypeEnum.LONG)){
			validate =  new Digits();	
		}else if(type == SimpleTypeEnum.DOUBLE || type == SimpleTypeEnum.FLOAT){
			validate =  new Number();	
		}else if(type == SimpleTypeEnum.EMAIL){
			validate =  new Email();	
		}else if(type == SimpleTypeEnum.PHONE){
			validate =  new Phone();	
		}else if(type == SimpleTypeEnum.IDCARD){
			validate =  new IdCard();
		}else if(type == SimpleTypeEnum.MOBILE){
			validate =  new Mobile();
		}else if(type == SimpleTypeEnum.DATE){
			validate =  new Date();
		}else if(type == SimpleTypeEnum.DATETIME){
			validate =  new Datetime();
		}else if(type == SimpleTypeEnum.TIME){
			validate =  new Time();
		}				
		return validate;
	}
	
}
