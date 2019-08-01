package idv.const_x.tools.coder.daojia;

import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Field;

/**
 * 厌倦了给getter，setter手工加注释
 * 
 * @since 6.3
 * @version 2014-03-05 14:36:19
 * @author const.x
 */
public class DaojiaVOAttributeUtils {
	
	// /**
	// * 品牌
	// */
	// public String cbrandid = null;
	public static String createPOJOField(Field field){
		StringBuilder str = new StringBuilder();
		str.append("	/**");
		str.append(field.getAlias() ).append("，对应的数据库字段为 <code>"+field.getColumn()+"</code> ");
		str.append("*/\n");
		if(field.getType().getJavaType().equalsIgnoreCase("long")){
			str.append("	@JsonSerialize(using = NumberAsStringSerializer.class)\n");
		}
		str.append("	@DSFMember(sortId = ?)\n"); 
		str.append("	private ");
		str.append(field.getType().getJavaType()).append(" " ).append( field.getName() );
		if(field.getDefaultValue() != null){
			str.append(" = ").append(field.getDefaultValue());
		}
		str.append( ";\n");
		return str.toString();
	}
	
	

	public static String createGetterAndSetter(Field field) {

		StringBuilder str = new StringBuilder();
		str.append(createGetter(field));
		str.append(createSetter(field));
		return str.toString();
	}
   
	// /**
	// * 获取品牌
	// *
	// * @return String 品牌
	// */
	// public String getCbrandid() {
	// return cbrandid;
	// }


	public static String createGetter(Field field) {

		String methodName = CamelCaseUtils.toFristUpper(field.getName());

		StringBuilder str = new StringBuilder();
			str.append("	/** \n	 * 获取" + field.getAlias()  + "，对应的数据库字段为 <code>"+field.getColumn()+"</code>  \n	 * \n	 * @return " + field.getType().getJavaType() + "\n	 */ \n");
			str.append("	public " +  field.getType().getJavaType() + " get" + methodName + "() {\n");
			str.append("	  return  this." + field + "; \n");
			str.append("	} \n");

		return str.toString();
	}
	
        ///**
		// * 设置品牌
		// *
		// * @param brandvalue 品牌
		// */
		// public void setCbrandid(String brandValue) {
		// this.cbrandid = brandValue);
		// }

		public static String createSetter(Field field) {

			String methodName = CamelCaseUtils.toFristUpper(field.getName());
			String setField = "value";

			StringBuilder str = new StringBuilder();
           
			str.append("	/** \n	 * 设置" +  field.getAlias()  + "，对应的数据库字段为 <code>"+field.getColumn()+"</code>  \n	 * \n	 * @param " + field.getType().getJavaType() + "\n	 */ \n");
			str.append("	public void set" + methodName + "(" + field.getType().getJavaType() + " " + setField + ") {\n");
			str.append("	  this." + field + " = " + setField + "; \n");
			str.append("	} \n");

			return str.toString();
		}
		
		public static String createSetterInvoke(String entity, Field field) {

			String methodName = CamelCaseUtils.toFristUpper(field.getName());
			String setField = "value";

			StringBuilder str = new StringBuilder();

			str.append("// 设置" + field.getAlias() + " \n ");
			str.append(entity + ".set" + methodName + "(" + setField + "); \n");

			return str.toString();
		}
		
		public static String createGetterInvoke(Field field) {

			String methodName = CamelCaseUtils.toFristUpper(field.getName());

			StringBuilder str = new StringBuilder();
				str.append("// 获取" + field.getAlias() + "  \n");
				str.append(field.getType().getJavaType() +" "+ field +" = get" + methodName + "();\n");

			return str.toString();
		}
}
