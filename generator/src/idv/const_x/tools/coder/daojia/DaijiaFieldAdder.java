package idv.const_x.tools.coder.daojia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.daojia.template.example.ExampleFactory;
import idv.const_x.tools.coder.generator.Field;

public class DaijiaFieldAdder {

	public static void main(String args[]) {

		List<Field> fields = new ArrayList<Field>();

		Field field1;
//		field1 = new Field("passportId", DaojiaSimpleTypeEnum.LONG, "客户passportID");
//		fields.add(field1);
		field1 = new Field("workerPassportId", DaojiaSimpleTypeEnum.LONG, "商家passportID");
		field1.setColumn("WORKER_PASSPORT_ID");
		fields.add(field1);
		
		field1 = new Field("categoryId", DaojiaSimpleTypeEnum.LONG, "品类ID");
		fields.add(field1);

		field1 = new Field("businessId", DaojiaSimpleTypeEnum.LONG, "业务线ID");
		fields.add(field1);
		
		field1 = new Field("serviceId", DaojiaSimpleTypeEnum.LONG, "服务项ID");
		fields.add(field1);
		

		addVOAttrs(fields);
		addVOASetterInvoker(fields);

		String table = "table_name";
		addTableColumns(fields, table);
		addMapper(fields);

	}

	private static void addVOAttrs(List<Field> fields) {
		System.out.println("VO类====================================================================================");
		for (Field data : fields) {
			String field = data.getName();
			Type type = data.getType();
			String javatype = null;
			if (!type.isSimpleType()) {
				ComplexType complex = (ComplexType) type;
				if (complex.getRelationship() != ComplexType.ONE_TO_ONE) {
					javatype = "List<" + type.getJavaType() + ">";
				} else {
					javatype = type.getJavaType();
				}
			} else {
				javatype = type.getJavaType();
			}
			System.out.println(DaojiaVOAttributeUtils.createPOJOField(data));
		}

		for (Field data : fields) {
			String field = data.getName();
			Type type = data.getType();
			String javatype = null;
			if (!type.isSimpleType()) {
				ComplexType complex = (ComplexType) type;
				if (complex.getRelationship() != ComplexType.ONE_TO_ONE) {
					javatype = "List<" + type.getJavaType() + ">";
				} else {
					javatype = type.getJavaType();
				}
			} else {
				javatype = type.getJavaType();
			}

			System.out.println(DaojiaVOAttributeUtils.createGetterAndSetter(data));
		}

		System.out.println("++Columns====");
		for (Field data : fields) {
			System.out.println("    " + data.getColumn().toUpperCase() + ",");
		}

		System.out.println("++Example====");
		PlaceHodler hodler = new PlaceHodler();
		Map<String, String> repalce = new HashMap<String, String>(3);
		for (Field data : fields) {
			repalce.put("fieldFristUp", CamelCaseUtils.toFristUpper(data.getName()));
			repalce.put("field", data.getName());
			repalce.put("column", data.getColumn());
			repalce.put("javaType", data.getType().getJavaType());
			
			String templateFile = ExampleFactory.getExampleTmpFile(data.getType());
			System.out.println(hodler.hodle(templateFile, repalce));
		}
	}

	private static void addVOASetterInvoker(List<Field> fields) {
		System.out.println(
				"VO setter invoker====================================================================================");
		for (Field data : fields) {
			String field = data.getName();
			Type type = data.getType();
			String javatype = null;
			if (!type.isSimpleType()) {
				ComplexType complex = (ComplexType) type;
				if (complex.getRelationship() != ComplexType.ONE_TO_ONE) {
					javatype = "List<" + type.getJavaType() + ">";
				} else {
					javatype = type.getJavaType();
				}
			} else {
				javatype = type.getJavaType();
			}
			System.out.println(DaojiaVOAttributeUtils.createSetterInvoke("entity", data));
		}
	}

	private static void addTableColumns(List<Field> fields, String table) {
		System.out.println("数据库表:================================================================================");
		StringBuilder sb = new StringBuilder();

		sb.append("ALTER TABLE ").append(table).append("( \n ");
		for (Field field : fields) {
			Type type = field.getType();
			String jdbcType = type.getJdbcType();
			sb.append("    ").append(field.getColumn().toUpperCase()).append(" ").append(jdbcType);
			if (type.getLength() != -1) {
				sb.append("(").append(type.getLength());
				if (type.getscale() != -1) {
					sb.append(",").append(type.getscale());
				}
				sb.append(") ");
			}
			sb.append(" NOT NULL ");
			if (type.getJavaType().equalsIgnoreCase("long") || type.getJavaType().equalsIgnoreCase("double")
					|| type.getJavaType().equalsIgnoreCase("int") || type.getJavaType().equalsIgnoreCase("integer")
					|| type.getJavaType().equalsIgnoreCase("float")) {
				sb.append(" DEFAULT '0' ");
			}else if(type.getJavaType().equalsIgnoreCase("String") ){
				sb.append(" DEFAULT '' ");
			}else if(type.getJavaType().equalsIgnoreCase("Date") ){
				sb.append(" DEFAULT CURRENT_TIMESTAMP ");
			}

			if (type.isPremiryKey()) {
				sb.append(" AUTO_INCREMENT ");
			}
			if (type instanceof EnumType) {
				EnumType etype = (EnumType) type;
				sb.append(" COMMENT '").append(field.getAlias()).append(" (");
				String[] values = etype.getValues();
				String[] alias = etype.getAlias();
				for (int i = 0; i < values.length; i++) {
					sb.append(" ").append(values[i]).append(":").append(alias[i]);
				}
				sb.append(")',\n");
			} else {
				sb.append(" COMMENT '").append(field.getAlias()).append("',\n");
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(");\n");
		System.out.println(sb.toString());
	}

	private static void addMapper(List<Field> fields) {
		StringBuilder result = new StringBuilder();
		for (Field field : fields) {
			Type type = field.getType();
			String alias = field.getAlias();
			if (type.isPremiryKey()) {
				result.append("    <!-- ").append(alias).append(" -->\n");
				result.append("    <id  property=\"id\" javaType=\"Long\"  column=\"")
						.append(field.getColumn().toUpperCase()).append("\" />\n");
			} else {
				result.append("    <!-- ").append(alias).append(" -->\n");
				result.append("    <result property=\"").append(field.getName()).append("\" javaType=\"")
						.append(type.getJavaType()).append("\" column=\"").append(field.getColumn().toUpperCase())
						.append("\" jdbcType=\"").append(field.getType().getJdbcType()).append("\" />\n");

				;
			}
		}
		System.out.println("mapper映射:==============================================================================");
		System.out.println(result.toString());

		System.out.println("++Base_Column_List ====");
		for (Field field : fields) {
			System.out.print(field.getColumn() + ",");
		}
		System.out.println();
		System.out.println("++insert:====");
		for (Field field : fields) {
			System.out.print(field.getColumn() + ",");
		}
		System.out.println();
		for (Field field : fields) {
			System.out.print("#{" + field.getName() + ",jdbcType=" + field.getType().getJdbcType() + "},");
		}
		System.out.println();
		System.out.println("++insertSelective:====");
		for (Field field : fields) {
			System.out.print("      <if test=\"" + field.getName() + " != null\" >\n");
			System.out.print("          " + field.getColumn() + "\n");
			System.out.print("      </if>\n");
		}
		System.out.println();
		for (Field field : fields) {
			System.out.print("      <if test=\"" + field.getName() + " != null\" >\n");
			System.out.print("          #{" + field.getName() + ",jdbcType=" + field.getType().getJdbcType() + "},\n");
			System.out.print("      </if>\n");
		}
		System.out.println("++updateByPrimaryKey updateByExample [updateByExampleWithBLOBs updateByPrimaryKeyWithBLOBs]:====");
		for (Field field : fields) {
			System.out.print("      " + field.getColumn() + " = #{record." + field.getName() + ",jdbcType="
					+ field.getType().getJdbcType() + "} ,\n");
		}
		System.out.println("++updateByPrimaryKeySelective updateByExampleSelective:====");
		for (Field field : fields) {
			System.out.print("      <if test=\"" + field.getName() + " != null\" >\n");
			System.out.print("          " + field.getColumn() + " = #{record." + field.getName() + ",jdbcType="
					+ field.getType().getJdbcType() + "} ,\n");
			System.out.print("      </if>\n");
		}
		System.out.println();
	}

}
