package idv.const_x.tools.coder.generator.sql;

import java.io.File;
import java.util.ArrayList;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.Field;

import idv.const_x.jdbc.table.meta.Type;

public class MysqlTableGenerator {

	public String generator(Context context) {

		String table = context.getTable();
		StringBuilder builder = new StringBuilder();
		builder.append("#创建数据库表\n");
		builder.append("DROP TABLE IF EXISTS " + table + ";\n");
		builder.append("CREATE TABLE " + table + " (\n");
		String primary = "";
		ArrayList<Field> lists = new ArrayList<Field>();
		for(Field field : context.getFields()){
			if(!field.getType().isSimpleType() ){
				ComplexType ctype = (ComplexType)field.getType();
				if(ctype.getRelationship() == ComplexType.MANY_TO_MANY){
					lists.add(field);
					continue;
				}
			}
			this.createColumn(field, builder);
			if(field.isPrimary()){
				primary = field.getColumn();
			}
		}
		builder.append("    PRIMARY KEY (").append(primary).append(")");
		builder.append("\n) DEFAULT CHARSET=utf8 COMMENT='").append(context.getEntityAlias()).append("';\n\n");
		
		for (Field field : lists) {
			String refTable = table + "_" + field.getName().toUpperCase() + "_REL"; 
			System.out.println("已生成关联关系表：" + refTable + "  请根据实际情况进行删除或创建");
			builder.append("#创建").append(context.getEntityAlias()).append("_").append(field.getAlias()).append("关系表(可选表)\n");
			builder.append("DROP TABLE IF EXISTS " + refTable + ";\n");
			builder.append("CREATE TABLE " + refTable + " (\n");
			builder.append("    ").append(context.getEntity().toUpperCase()).append("_ID BIGINT(19)  COMMENT '").append(context.getEntityAlias()).append("主键',\n");
			builder.append("    ").append(field.getColumn()).append(" BIGINT(19)  COMMENT '").append(field.getAlias()).append("主键'\n");
			builder.append(") DEFAULT CHARSET=utf8 COMMENT='").append(context.getEntityAlias()).append("_").append(field.getAlias()).append("关系表';\n\n");
		}
		
		if(context.isNeedAudit()){
			    builder.append("#创建审批记录表\n");
				builder.append("DROP TABLE IF EXISTS " + context.getAuditTable() + ";\n");
				builder.append("CREATE TABLE " + context.getAuditTable() + " (\n");
				builder.append("	OPERATTIME NCHAR(19) ,\n");
				builder.append("	STATUS DECIMAL(2) ,\n");
				builder.append("	OPERATOR_ID BIGINT(20) ,\n");
				builder.append("	NOTE NVARCHAR2(256) ,\n");
				builder.append("	TARGET_ID BIGINT(20) \n");
				builder.append(") DEFAULT CHARSET=utf8 COMMENT='").append(context.getEntityAlias()).append("审批记录';");
		}
		
		System.out.println("建表语句已生成  请在开发库中执行，并对字段长度,是否可空,默认值等内容进行微调");
		return builder.toString();
	}
	
	
	
	private void createColumn(Field field,StringBuilder builder){
		Type type = field.getType();
		String jdbcType = type.getJdbcType();
		if(field.isPrimary()){
			jdbcType = "BIGINT";
		}
		builder.append("    ").append(field.getColumn().toUpperCase()).append(" ").append(jdbcType);
		if(type.getLength() != -1){
			builder.append("(").append(type.getLength());
			if(type.getscale() != -1){
				builder.append(",").append(type.getscale());
			}
			builder.append(") ");
		}
		if(!field.isNullable()){
			builder.append(" NOT NULL ");
		}
		if(field.getDefaultValue() != null && !type.isPremiryKey()){
			builder.append(" DEFAULT '").append(field.getDefaultValue()).append("' ");
		}
		if(type.isPremiryKey()){
			builder.append(" AUTO_INCREMENT ");
		}
		if(type  instanceof EnumType){
			EnumType etype = (EnumType)type;
			builder.append(" COMMENT '").append(field.getAlias()).append(" (");
			String[] values = etype.getValues();
			String[] alias = etype.getAlias();
			for(int i = 0 ; i < values.length; i ++){
				builder.append(" ").append(values[i]).append(":").append(alias[i]);
			}
			builder.append(")',\n");
		}else{
			builder.append(" COMMENT '").append(field.getAlias()).append("',\n");
		}
		 
	}
	


}
