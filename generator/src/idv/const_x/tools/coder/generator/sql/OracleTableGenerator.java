package idv.const_x.tools.coder.generator.sql;

import java.io.File;
import java.util.ArrayList;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.Field;

import idv.const_x.jdbc.table.meta.Type;
public class OracleTableGenerator {

	public String generator(Context context) {

		String table = context.getTable().toUpperCase();
		StringBuilder builder = new StringBuilder();
		
		builder.append("#创建主键SEQUENCE\n");
		builder.append("DROP SEQUENCE SEQ_").append(context.getTable().toUpperCase());
		builder.append(";\nCREATE SEQUENCE SEQ_").append(context.getTable().toUpperCase())
		.append("\n INCREMENT BY 1\n START WITH 1\n NOMAXvalue\n NOCYCLE\n NOCACHE;\n\n");
		
		builder.append("#创建数据库表\n");
		this.createDrop(builder, table);
		builder.append("CREATE TABLE " + table + " (\n");
		String primary = "";
		ArrayList<Field> lists = new ArrayList<Field>();
		for(Field field : context.getFields()){
			if(!field.getType().isSimpleType() ){
				ComplexType ctype = (ComplexType)field.getType();
				if(ctype.getRelationship() != ComplexType.ONE_TO_ONE){
					lists.add(field);
					continue;
				}
			}
			this.createColumn(field, builder);
			if(field.isPrimary()){
				primary = field.getColumn();
			}
		}
		for(Field field : context.getFields()){
			if(field.isUnique()){
				builder.append("    CONSTRAINT ").append(table.toUpperCase()).append("_UNIQUE_").append(field.getName().toUpperCase())
				.append(" UNIQUE (").append(field.getColumn()).append("),\n");
			}
		}
		builder.append("    CONSTRAINT ").append(table.toUpperCase()).append("_PRIMARY").append(" PRIMARY KEY(").append(primary).append(")\n");
		builder.append("\n);\n\n");
		
		builder.append("comment on table ").append(table.toUpperCase()).append(" is '").append(context.getComponentAlias()).append("';\n");
		for(Field field : context.getFields()){
			if(!field.getType().isSimpleType() ){
				ComplexType ctype = (ComplexType)field.getType();
				if(ctype.getRelationship() == ComplexType.MANY_TO_MANY){
					continue;
				}
			}
			this.createComment(field,table, builder);
		}
		builder.append("\n\n");
		
		for (Field field : lists) {
			String refTable = table + "_" + field.getName().toUpperCase() + "_REL"; 
			System.out.println("已生成关联关系表：" + refTable + "  请根据实际情况进行删除或创建");
			builder.append("#创建").append(context.getEntityAlias()).append("_").append(field.getAlias()).append("关系表(可选表)\n");
			this.createDrop(builder, refTable);
			builder.append("CREATE TABLE " + refTable + " (\n");
			builder.append("    ").append(context.getEntity().toUpperCase()).append("_ID DECIMAL(19),\n");
			builder.append("    ").append(field.getColumn()).append(" DECIMAL(19)\n");
			builder.append(");\n");
			builder.append("comment on table ").append(refTable).append(" is '").append(context.getEntityAlias()).append("_").append(field.getAlias()).append("关系表';\n");
			builder.append("comment on column ").append(refTable).append(".").append(context.getEntity().toUpperCase()).append("_ID is '")
			.append(context.getEntityAlias()).append("主键';\n");
			builder.append("comment on column ").append(refTable).append(".").append(field.getColumn()).append(" is '")
			.append(field.getAlias()).append("主键';\n\n");
		}
		
		if (context.isNeedAudit()) {
			builder.append("#创建审批记录表\n");
			this.createDrop(builder, context.getAuditTable());
			builder.append("CREATE TABLE "+ context.getAuditTable().toUpperCase() + " (\n");
			builder.append("	OPERATTIME NCHAR(19) ,\n");
			builder.append("	STATUS DECIMAL(2) ,\n");
			builder.append("	OPERATOR_ID DECIMAL(20) ,\n");
			builder.append("	NOTE NVARCHAR2(256) ,\n");
			builder.append("	TARGET_ID DECIMAL(20) \n");
			builder.append(");\n\n");
		}
	
		System.out.println("建表语句已生成  请在开发库中执行，并对字段长度,是否可空,默认值等内容进行微调");
		
		
		return builder.toString();
	}
	
	
	private void createDrop(StringBuilder builder,String table){
		builder.append("DROP TABLE　").append(table).append(" ;\n");
		//builder.append("BEGIN\n").append("  EXECUTE IMMEDIATE 'DROP TABLE　").append(table).append("';\n  EXCEPTION WHEN OTHERS THEN NULL;\nEND;\n");
	}
	
	
	private void createColumn(Field field,StringBuilder builder){
		Type type = field.getType();
		String jdbcType = type.getJdbcType();
		if(jdbcType.equalsIgnoreCase("VARCHAR")){
			jdbcType = "NVARCHAR2";
		}else if(jdbcType.equalsIgnoreCase("CHAR")){
			jdbcType = "NCHAR";
		}
		builder.append("    ").append(field.getColumn().toUpperCase()).append(" ").append(jdbcType);
		if(type.getLength() != -1){
			builder.append("(").append(type.getLength());
			if(type.getscale() != -1){
				builder.append(",").append(type.getscale());
			}
			builder.append(") ");
		}
		if(field.getDefaultValue() != null && !type.isPremiryKey()){
			builder.append(" DEFAULT '").append(field.getDefaultValue()).append("' ");
		}
		if(!field.isNullable()){
			builder.append(" NOT NULL ");
		}
		builder.append(",\n");
		
	}
	
	
	private void createComment(Field field,String table,StringBuilder builder){
		Type type = field.getType();
		builder.append("comment on column ").append(table.toUpperCase()).append(".").append(field.getColumn().toUpperCase()).append(" is '");
		if(type  instanceof EnumType){
			EnumType etype = (EnumType)type;
			builder.append(field.getAlias()).append(" (");
			String[] values = etype.getValues();
			String[] alias = etype.getAlias();
			for(int i = 0 ; i < values.length; i ++){
				builder.append(" ").append(values[i]).append(":").append(alias[i]);
			}
			builder.append(")';\n");
		}else{
			builder.append(field.getAlias()).append("';\n");
		}
	}
	


}
