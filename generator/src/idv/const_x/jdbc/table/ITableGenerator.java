package idv.const_x.jdbc.table;

import idv.const_x.jdbc.table.meta.Field;

import java.util.List;

public interface ITableGenerator {
	
	 String create(String table,String alias,List<Field> Fields);
	 
	 String alert(String table,String alias,List<Field> addFields,List<String> delFields);
	 
	 String wapperPageSql(String sql,int page, int pagesize);
	 

}
