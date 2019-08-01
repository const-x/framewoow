package idv.const_x.tools.coder;



/**
 * 字段相关代码生成工具
 * @author const.x
 *
 */
public class FieldUtils {


	/**
	 * 生成POJO代码
	 * @param datas
	 * @return
	 */
	public static String createPOJO(String[][] datas) {
		StringBuilder b = new StringBuilder();
		for (String[] infos : datas) {
			b.append(
					VOAttributeUtils.createPOJOField(infos[0], infos[1],
							infos[2])).append("\n");
		}
		for (String[] infos : datas) {
			b.append(
					VOAttributeUtils.createGetterAndSetter(infos[0], infos[1],
							infos[2])).append("\n");
		}
		return b.toString();
	}

	/**
	 * 生成mybaitis的result部分
	 * @param datas
	 * @return
	 */
	public static String createMybatis(String[][] datas) {
		StringBuilder b = new StringBuilder();
		for (String[] infos : datas) {
			b.append("    <!-- ").append(infos[2]).append(" -->\n")
					.append("    <result property=\"").append(infos[0])
					.append("\"  javaType=\"").append(infos[1])
					.append("\" jdbcType=\"");
			if (infos[1].equalsIgnoreCase("Long")
					|| infos[1].equalsIgnoreCase("int")
					|| infos[1].equalsIgnoreCase("integer")) {
				b.append("INTEGER");
			} else if (infos[1].equalsIgnoreCase("double")
					|| infos[1].equalsIgnoreCase("float")
					|| infos[1].equalsIgnoreCase("decimal")) {
				b.append("DECIMAL");
			} else {
				b.append("VARCHAR");
			}
			b.append("\" column=\"").append(infos[0].toLowerCase())
					.append("\" />\n");
		}
		b.append("\n");
		for (String[] infos : datas) {
			b.append("a." + infos[0] + ",");
		}
		b.append("\n");
		for (String[] infos : datas) {
			b.append(infos[0] + " = #{" + infos[0] + "},");
		}
		b.append("\n");
		for (String[] infos : datas) {
			b.append(infos[0] + ",");
		}
		b.append("\n");
		for (String[] infos : datas) {
			b.append("#{item." + infos[0] + "},");
		}
		b.append("\n");
		for (String[] infos : datas) {
			b.append("#{" + infos[0] + "},");
		}
		return b.toString();
	}
    
	/**
	 * 生成jsp页面部分
	 * @param datas
	 * @return
	 */
	public static String createJSP(String[][] datas) {
		StringBuilder b = new StringBuilder();
		b.append("index \n");
		for (String[] infos : datas) {
			b.append("                <th width=\"200\"  >").append(infos[2])
					.append("</th>\n");
		}
		for (String[] infos : datas) {
			b.append("                <td style=\"text-align:center\" >${item.")
					.append(infos[0]).append("}</td>\n");
		}
		b.append("create \n");
		for (String[] infos : datas) {
			b.append("		<p>\n		 <label>").append(infos[2])
					.append("：</label>\n")
					.append("		 <input type=\"text\" id=\"").append(infos[0])
					.append("\" name=\"").append(infos[0])
					.append("\" class=\"required ");

			if (infos[1].equalsIgnoreCase("Long")
					|| infos[1].equalsIgnoreCase("int")
					|| infos[1].equalsIgnoreCase("integer")) {
				b.append("digits");
			} else if (infos[1].equalsIgnoreCase("double")
					|| infos[1].equalsIgnoreCase("float")
					|| infos[1].equalsIgnoreCase("decimal")) {
				b.append("number");
			} else {
				if (infos[2].contains("时间") || infos[2].contains("有效期")) {
					b.append("date");
				}
			}
			b.append("\" maxlength=\"20\" alt=\"").append(infos[2])
					.append("\" />\n		</p>\n");
		}
		b.append("update \n");
		for (String[] infos : datas) {
			b.append("		<p>\n		 <label>").append(infos[2])
					.append("：</label>\n")
					.append("		 <input type=\"text\" id=\"").append(infos[0])
					.append("\" name=\"").append(infos[0])
					.append("\" class=\"required readonly ");

			if (infos[1].equalsIgnoreCase("Long")
					|| infos[1].equalsIgnoreCase("int")
					|| infos[1].equalsIgnoreCase("integer")) {
				b.append("digits");
			} else if (infos[1].equalsIgnoreCase("double")
					|| infos[1].equalsIgnoreCase("float")
					|| infos[1].equalsIgnoreCase("decimal")) {
				b.append("number");
			} else {
				if (infos[2].contains("时间") || infos[2].contains("有效期")) {
					b.append("date");
				}
			}
			b.append("\" maxlength=\"20\" value=\"${entity.").append(infos[0])
					.append("}\"  alt=\"").append(infos[2])
					.append("\" />\n		</p>\n");
		}

		return b.toString();
	}

	/**
	 * 生成标准返回的JSON字符串
	 * @param datas
	 * @param isArrayReturn
	 * @param isPageReturn
	 * @return
	 */
	public static String createJson(String[][] datas, boolean isArrayReturn,
			boolean isPageReturn) {
		StringBuilder b = new StringBuilder();
		String space = "";
		b.append("	 * <pre>\n").append("	 * {\n").append("	 * 		\"data\":\n")
				.append("	 * 		    ")
				.append(isArrayReturn && !isPageReturn ? "[" : "")
				.append("{\n");
		if (isPageReturn) {
			b.append("	 * 			\"totalnum\":1,\n	 * 			\"totalpage\":1,\n	 * 			\"result\":[{\n");
			space = "    ";
		}
		for (String[] infos : datas) {
			b.append("	 * 			").append(space).append("\"").append(infos[0])
					.append("\":\"").append(infos[2]).append("\",\n");
		}
		b.deleteCharAt(b.length() - 1);
		b.deleteCharAt(b.length() - 1);
		b.append("\n");
		if (isPageReturn) {
			b.append("	 * 			}]\n");
		}
		b.append("	 * 		    }")
				.append(isArrayReturn && !isPageReturn ? "[" : "")
				.append(",\n").append("	 * 		\"code\":\"0000\"\n")
				.append("	 * }\n").append("	 * </pre>\n");
		return b.toString();
	}

	/**
	 * 生成所有set方法的调用
	 * @param datas
	 * @param name
	 * @return
	 */
	public static String createSetters(String[][] datas, String name) {
		StringBuilder b = new StringBuilder();
		for (String[] infos : datas) {
			b.append(name).append(".set")
					.append(CamelCaseUtils.toFristUpper(infos[0]))
					.append("( value );\n");
		}
		return b.toString();
	}

}
