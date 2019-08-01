package idv.const_x.jdbc.table;

import idv.const_x.jdbc.DBType;

public class GeneratorFactory {

	
	public static ITableGenerator getTableGenerator(DBType type){
		if(type == DBType.MYSQL){
			return new MySqlTableGenerator();
		}
		if(type == DBType.ORACLE){
			return new OracleTableGenerator();
		}
		return null;
	}
}
