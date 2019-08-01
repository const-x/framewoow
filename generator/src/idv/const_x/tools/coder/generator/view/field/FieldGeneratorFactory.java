package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;

public class FieldGeneratorFactory {
	
	public static IFieldGenerator getFieldGenerator(Field field){
		IFieldGenerator generator = null;
		if(field.isEditable()){
			Type type =  field.getType();
			if(type.isSimpleType()){
				if(field instanceof ImageField){
					generator = new ImageFieldGenerator();
				}else if(field  instanceof RichTextField){
					generator = new RichTextFieldGenerator();
				}else if(type  instanceof EnumType){
					generator = new EnumTypeFieldGenerator();
				}else{
					if(type == SimpleTypeEnum.DATETIME){
						generator = new DateTimeFieldGenerator();
					}else if(type == SimpleTypeEnum.DATE){
						generator = new DateFieldGenerator();
					}else if(type == SimpleTypeEnum.TIME){
						generator = new TimeFieldGenerator();
					}else if(type == SimpleTypeEnum.BOOLEAN){
						generator = new BooleanFieldGenerator();
					}else{
						generator = new TextFieldGenerator();
					}
				}
			}else{
				generator = new ComplexTypeFieldGenerator();
			}
		}else{
			if(field.getDefaultValue() != null ){
				generator = new HiddenFieldGenerator();
			}
		}
		
		return generator;
	}

}
