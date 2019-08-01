package idv.const_x.tools.coder;

import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Field;

import java.util.ArrayList;
import java.util.List;


public class FieldAdder {

	
	public static void main(String args[]) {
		
		List<Field> fields = new ArrayList<Field>();

		Field field1;
		
//		
		field1 = new Field("nickname", SimpleTypeEnum.STRING, "微信昵称");
		fields.add(field1);
		
		field1 = new Field("headurl", SimpleTypeEnum.STRING, "微信头像");
		fields.add(field1);
		field1 = new Field("openid", SimpleTypeEnum.STRING, "微信openid");
		fields.add(field1);
		
		field1 = new Field("signStatus", SimpleTypeEnum.INTEGER, "签约状态");
		fields.add(field1);
//		
		field1 = new Field("photo", SimpleTypeEnum.STRING, "商家头像");
		fields.add(field1);
		field1 = new Field("staffNo", SimpleTypeEnum.STRING, "商家工号");
		fields.add(field1);
		field1 = new Field("phone", SimpleTypeEnum.STRING, "商家手机");
		fields.add(field1);
		field1 = new Field("passportid", SimpleTypeEnum.LONG, "passportID");
		fields.add(field1);
		field1 = new Field("workid", SimpleTypeEnum.LONG, "商家ID");
		fields.add(field1);
		field1 = new Field("username", SimpleTypeEnum.STRING, "商家姓名");
		fields.add(field1);
		
//		
//		field1 = new Field("location", new ComplexType(
//				"com.bbg.vrp.Location"), "出发位置");
//		fields.add(field1);
//		
//		field1 = new Field("destination", new ComplexType(
//				"com.bbg.vrp.Location"), "目的地");
//		fields.add(field1);
//		
//	
//		field1 = new Field("weiget", SimpleTypeEnum.DOUBLE, "常温总重（kg）");
//		fields.add(field1);
//		field1 = new Field("volume", SimpleTypeEnum.DOUBLE, "常温体积（L）");
//		fields.add(field1);
//		
//		field1 = new Field("coldWeiget", SimpleTypeEnum.DOUBLE, "冷藏总重（kg）");
//		fields.add(field1);
//		field1 = new Field("coldVolume", SimpleTypeEnum.DOUBLE, "冷藏体积（L）");
//		fields.add(field1);
//		
//		field1 = new Field("frozenWeiget", SimpleTypeEnum.DOUBLE, "冷冻总重（kg）");
//		fields.add(field1);
//		field1 = new Field("frozenVolume", SimpleTypeEnum.DOUBLE, "冷冻体积（L）");
//		fields.add(field1);
//		
//		field1 = new Field("startTime", SimpleTypeEnum.DATETIME, "可开始运输时间");
//		fields.add(field1);
//		
//		field1 = new Field("endTime", SimpleTypeEnum.DATETIME, "最晚运输时间");
//		fields.add(field1);
		


//		addVOAttrs(fields);
		addVOASetterInvoker(fields);
//		String table = "bms_dict_data";
//		addTableColumns(fields, table);
//		addMapper(fields);
//		
//		String entity = "DictData";
//		String module = "Dictionary";
//		addMetaField(fields, entity, module);
	}
	private static void addVOASetterInvoker(List<Field> fields){
		System.out.println("VO setter invoker=====================");
		for (Field data : fields) {
			String field = data.getName();
			Type type = data.getType();
			String javatype  = null;
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					javatype = "List<" +type.getJavaType() + ">";
			    }else{
			    	javatype = type.getJavaType();
			    }
			}else{
				javatype = type.getJavaType();
			}
			System.out.println(VOAttributeUtils.createSetterInvoke("entity",field, javatype, data.getAlias()));
		}
	}
	
	private static void addVOAttrs(List<Field> fields){
		System.out.println("VO类=====================");
		for (Field data : fields) {
			String field = data.getName();
			Type type = data.getType();
			String javatype  = null;
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					javatype = "List<" +type.getJavaType() + ">";
			    }else{
			    	javatype = type.getJavaType();
			    }
			}else{
				javatype = type.getJavaType();
			}
			System.out.println(VOAttributeUtils.createPOJOField(field, javatype, data.getAlias()));
		}
		
		for (Field data : fields) {
			String field = data.getName();
			Type type = data.getType();
			String javatype  = null;
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					javatype = "List<" +type.getJavaType() + ">";
			    }else{
			    	javatype = type.getJavaType();
			    }
			}else{
				javatype = type.getJavaType();
			}
			
			System.out.println(VOAttributeUtils.createGetterAndSetter(field, javatype, data.getAlias()));
		}
	}
	
	
	private static void addTableColumns(List<Field> fields,String table){
		System.out.println("数据库表:=================");
		StringBuilder sb = new StringBuilder();
//		sb.append("ALTER TABLE ").append(table).append("( \n ");
//		for (Field field : fields) {
//			Type type = field.getType();
//			String jdbcType = type.getJdbcType();
//			sb.append("    ").append(field.getColumn().toUpperCase()).append(" ").append(jdbcType);
//			if(type.getLength() != -1){
//				sb.append("(").append(type.getLength());
//				if(type.getscale() != -1){
//					sb.append(",").append(type.getscale());
//				}
//				sb.append(") ");
//			}
//			if(!field.isNullable()){
//				sb.append(" NOT NULL ");
//			}
//			if(field.getDefaultValue() != null && !type.isPremiryKey()){
//				sb.append(" DEFAULT '").append(field.getDefaultValue()).append("' ");
//			}
//			if(type.isPremiryKey()){
//				sb.append(" AUTO_INCREMENT ");
//			}
//			if(type  instanceof EnumType){
//				EnumType etype = (EnumType)type;
//				sb.append(" COMMENT '").append(field.getAlias()).append(" (");
//				String[] values = etype.getValues();
//				String[] alias = etype.getAlias();
//				for(int i = 0 ; i < values.length; i ++){
//					sb.append(" ").append(values[i]).append(":").append(alias[i]);
//				}
//				sb.append(")',\n");
//			}else{
//				sb.append(" COMMENT '").append(field.getAlias()).append("',\n");
//			}
//		}
		
		sb.append("ALTER TABLE ").append(table).append(" ADD  (\n");
		for (Field field : fields) {
			Type type = field.getType();
			String jdbcType = type.getJdbcType();
			if(jdbcType.equalsIgnoreCase("VARCHAR")){
				jdbcType = "NVARCHAR2";
			}else if(jdbcType.equalsIgnoreCase("CHAR")){
				jdbcType = "NCHAR";
			}
			sb.append("    ").append(field.getColumn().toUpperCase()).append(" ").append(jdbcType);
			if(type.getLength() != -1){
				sb.append("(").append(type.getLength());
				if(type.getscale() != -1){
					sb.append(",").append(type.getscale());
				}
				sb.append(") ");
			}
			if(field.getDefaultValue() != null && !type.isPremiryKey()){
				sb.append(" DEFAULT '").append(field.getDefaultValue()).append("' ");
			}
			if(!field.isNullable()){
				sb.append(" NOT NULL ");
			}
			sb.append(",\n");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(");\n");
		for (Field field : fields) {
			sb.append("COMMENT ON COLUMN ").append(table).append(".").append(field.getColumn()).append(" is '").append(field.getAlias()) .append("'; ");
		}
		System.out.println(sb.toString());
	}
	
	
	private static void addMapper(List<Field> fields) {

		StringBuilder result = new StringBuilder();
		for (Field field : fields) {
			Type type = field.getType();
			String alias = field.getAlias();
			if (type.isPremiryKey()) {
				result.append("    <!-- ").append(alias).append(" -->\n");
				result.append(
						"    <id  property=\"id\" javaType=\"Long\"  column=\"")
						.append(field.getColumn().toUpperCase())
						.append("\" />\n");
			} else {
				result.append("    <!-- ").append(alias).append(" -->\n");
				result.append("    <result property=\"")
						.append(field.getName()).append("\" javaType=\"")
						.append(type.getJavaType()).append("\" column=\"")
						.append(field.getColumn().toUpperCase())
						.append("\" />\n");
				;
			}
		}
		System.out.println("mapper映射:===============");
		System.out.println(result.toString());
		
		System.out.println("select:");
		for (Field field : fields) {
			System.out.print("a." + field.getColumn() + ",");
		}
		System.out.println();
		System.out.println("insert:");
		for (Field field : fields) {
			System.out.print( field.getColumn() + ",");
		}
		System.out.println();
		for (Field field : fields) {
			System.out.print( "#{"+field.getName()+"} ,");
		}
		System.out.println();
		System.out.println("update:");
		for (Field field : fields) {
			System.out.print( field.getColumn() +" = #{"+field.getName()+"} ,");
		}
		System.out.println();
	}
	
	private static void addMetaField(List<Field> fields,String entity,String module) {
		System.out.println("metadata:=================");
		for (Field field : fields) {
			String sql = " insert into SECURITY_META_FIELD ( ID,NAME,DESCRIPTION,COLUMNNAME,JAVATYPE,MAXLENGTH,SCALE,NULLABLE,UNIQUENESS,EDITABLE,DEFVALUE,"
			+ "REFCLASS,RELATIONSHIP,REF_ENUM,ENTITY_ID ) values ( SEQ_SECURITY_META_FIELD.NEXTVAL, '"+field.getName()+"', '"+field.getAlias()+"', '"
			+field.getColumn()+"', '"+field.getType().getJavaType()+"', '"+field.getType().getLength()+"', '"+field.getType().getscale()+"', "
			+(field.isNullable()?1:0)+", "+(field.isUnique()?1:0)+", "+(field.isEditable()?1:0)+", "+field.getDefaultValue()+", ";
			if(field.getType().isSimpleType()){
				sql += "NULL,NULL,NULL";
			}else{
				if(field.getType() instanceof EnumType){
					EnumType etype = (EnumType)field.getType();
					sql += ("NULL,NULL,'"+etype.getClassName() +"'");
				}else{
					ComplexType etype = (ComplexType)field.getType();
					sql += ("'"+etype.getRefClass() +"','"+ etype.getRelationship() +  "',NULL");
				}
				
				
			}
			sql += ",( SELECT ID FROM SECURITY_META_ENTITY where NAME = '"+entity+"' and REFMODULE = '"+module+"') )"; 
			System.out.println(sql);
		}
	}

	
}
