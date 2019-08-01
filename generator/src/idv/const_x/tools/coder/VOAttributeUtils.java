package idv.const_x.tools.coder;



/**
 * 厌倦了给getter，setter手工加注释
 * 
 * @since 6.3
 * @version 2014-03-05 14:36:19
 * @author const.x
 */
public class VOAttributeUtils {
	
	// /**
	// * 品牌
	// */
	// public String cbrandid = null;
	public static String createPOJOField(String field, String type, String alias){
		StringBuilder str = new StringBuilder();
		str.append("	/**");
		str.append(alias);
		str.append("*/");
		String defaultvalue = " = null";
		if(type.equals("boolean")){
			defaultvalue = " = false";
		}else if(type.equals("int") || type.equals("double")|| type.equals("float")
				|| type.equals("short")|| type.equals("long")){
			defaultvalue = "";
		}
		
		str.append("\n	private ");
		str.append(type).append(" " ).append( field ).append( defaultvalue ).append( ";\n");
		return str.toString();
	}
	
	// /**
	// * 品牌
	// */
	// public String cbrandid = null;
	public static String createPOJOField(String field, String type, String alias,String ... profixes){
		StringBuilder str = new StringBuilder();
		str.append("	/**");
		str.append(alias);
		str.append("	*/");
		String defaultvalue = " = null";
		if(type.equals("boolean")){
			defaultvalue = " = false";
		}else if(type.equals("int") || type.equals("double")|| type.equals("float")
				|| type.equals("short")|| type.equals("long")){
			defaultvalue = "";
		}
		
		str.append("\n	private ");
		if(profixes != null){
			for (String profix : profixes) {
				str.append(profix).append(" ");
			}
		}
		str.append(type).append(" " ).append( field ).append( defaultvalue ).append( ";\n");
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
	//
	// /**
	// * 设置品牌
	// *
	// * @param brandvalue 品牌
	// */
	// public void setCbrandid(String brandValue) {
	// this.cbrandid = brandValue);
	// }

	public static String createGetterAndSetter(String field, String type, String alias) {

		StringBuilder str = new StringBuilder();
		str.append(createGetter(field,type,alias));
		str.append(createSetter(field,type,alias));
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


	public static String createGetter(String field, String type, String alias) {

		String methodName = CamelCaseUtils.toFristUpper(field);

		StringBuilder str = new StringBuilder();
		if (type.equalsIgnoreCase("boolean")) {
			str.append("	/** \n	 * 是否" + alias + " \n	 * \n	 * @return " + type + "\n	 */ \n");
			str.append("	public " + type + " is" + methodName + "() {\n");
			str.append("	  return  this." + field + "; \n");
			str.append("	} \n");
		} else {
			str.append("	/** \n	 * 获取" + alias + " \n	 * \n	 * @return " + type + "\n	 */ \n");
			str.append("	public " + type + " get" + methodName + "() {\n");
			str.append("	  return  this." + field + "; \n");
			str.append("	} \n");
		}

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

		public static String createSetter(String field, String type, String alias) {

			String methodName = CamelCaseUtils.toFristUpper(field);
			String setField = "value";

			StringBuilder str = new StringBuilder();

			str.append("	/** \n	 * 设置" + alias + " \n	 * \n	 * @param " + type + "\n	 */ \n");
			str.append("	public void set" + methodName + "(" + type + " " + setField + ") {\n");
			str.append("	  this." + field + " = " + setField + "; \n");
			str.append("	} \n");

			return str.toString();
		}
		
		public static String createSetterInvoke(String entity,String field, String type, String alias) {

			String methodName = CamelCaseUtils.toFristUpper(field);
			String setField = "value";

			StringBuilder str = new StringBuilder();

			str.append("// 设置" + alias + " \n ");
			str.append(entity + ".set" + methodName + "(" + setField + "); \n");

			return str.toString();
		}
		
		public static String createGetterInvoke(String field, String type, String alias) {

			String methodName = CamelCaseUtils.toFristUpper(field);

			StringBuilder str = new StringBuilder();
			if (type.equalsIgnoreCase("boolean")) {
				str.append("// 是否" + alias + " \n");
				str.append(type +" "+ field +" = is" + methodName + "();\n");
			} else {
				str.append("// 获取" + alias + "  \n");
				str.append(type +" "+ field +" = is" + methodName + "();\n");
			}

			return str.toString();
		}
}
