package idv.const_x.jdbc.table;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import idv.const_x.jdbc.table.meta.Field;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.Type;

public class OracleTableGenerator implements ITableGenerator{
	
    @Override
	public  String create(String table,String alias,List<Field> Fields) {

		StringBuilder builder = new StringBuilder();
		builder.append("#创建数据库表\n");
		builder.append("DROP TABLE IF EXISTS " + table + ";\n");
		builder.append("CREATE TABLE " + table + " (\n");
		for(Field field : Fields){
			createColumn(field, builder);
		}
		StringBuilder primaries = new StringBuilder();
		for(Field field : Fields){
			if(field.isPrimary()){
				primaries.append(field.getColumn()).append(",");
			}
		}
		if(primaries.length() != 0){
			primaries.deleteCharAt(primaries.length() - 1);
			builder.append("    PRIMARY KEY ").append(table.toUpperCase()).append("_PRIMARY (").append(primaries.toString()).append("),\n");
		}
	
		for(Field field : Fields){
			if(field.isUnique()){
				builder.append("    UNIQUE KEY ").append(table.toUpperCase()).append("_UNIQUE_").append(field.getName().toUpperCase())
				.append(" (").append(field.getName()).append("),\n");
			}
		}
		builder.delete(builder.lastIndexOf(","), builder.length());
		builder.append("\n) DEFAULT CHARSET=utf8 COMMENT='").append(alias).append("';");
		return builder.toString();
	}
	
  
	
	@Override
	public String alert(String table, String alias, List<Field> addFields, List<String> delFields) {
		StringBuilder builder = new StringBuilder();
		builder.append("#修改数据库表\n");
		builder.append("ALTER TABLE ").append(table).append(" \n");
		if(addFields != null){
			for(Field field : addFields){
				builder.append(" ADD COLUMN ");
				createColumn(field, builder);
			}
			for(Field field : addFields){
				if(field.isUnique()){
					builder.append("    UNIQUE KEY ").append(table.toUpperCase()).append("_UNIQUE_").append(field.getName().toUpperCase())
					.append(" (").append(field.getName()).append("),\n");
				}
			}
		}
		if(delFields != null){
			for(String field : delFields){
				builder.append(" DROP COLUMN ").append(field).append(",\n");
			}
		}
		if(alias != null){
			builder.append(" COMMENT='").append(alias).append("'");
		}else{
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
    
	

	public  void createColumn(Field field,StringBuilder builder){
		Type type = field.getType();
		builder.append("    ").append(field.getColumn().toLowerCase()).append(" ").append(type.getJdbcType());
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



	@Override
	public String wapperPageSql(String sql, int page, int pagesize) {
		StringBuilder pageSql = new StringBuilder("select * from(select pageview.*,ROWNUM rn from( ");
		int startIndex = (page - 1) * pagesize;
		int endIndex = (page) * pagesize;
		pageSql.append(sql).append(") pageview where ROWNUM<= ").append(endIndex).append(" ) where rn> ").append(startIndex);
		return pageSql.toString();
	}


}
