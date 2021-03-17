package idv.const_x.tools.coder.generator.mapper;

import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.jdbc.table.meta.ComplexType;


public class SelectFieldGenerator {
	
	
	
	public String generator(Context context){
		StringBuilder resultMapper = new  StringBuilder();
		this.createLeftJoinResult(resultMapper, context.getFields(), context.getTable(),"select");

		if(context.isNeedAudit()){
			resultMapper.append("<sql id=\"selectAudit\">\n")
			.append("	select a.OPERATTIME ,a.STATUS ,a.NOTE ,a.TARGET_ID,\n")
			.append("	operator.ID as operator_id,operator.NAME as operator_name,\n")
			.append("	from MEMBERMNG_VIPER_AUDIT a\n")
			.append("	left join security_user operator on operator.ID = a.OPERATOR_ID\n")
			.append("</sql>\n\n");
		}
		
		System.out.println("创建对 " +context.getTable() + " 的查询语句将查询出所有字段的内容，这或许会产生效率问题，请根据实际使用情况修改");
		return resultMapper.toString();
	}
	

	
	private void createLeftJoinResult(StringBuilder resultMapper,List<Field> fields,String table,String id ){

		resultMapper.append("<sql id=\"table_name\"> ").append(table).append("_${tableId} </sql>\n\n");
		resultMapper.append("<sql id=\"base_select\">\n").append("   select ");
		List<Field> associations = new ArrayList<Field>();
		int counter = 0;
		for (Field data : fields) {
			if(data.getType().isSimpleType()){
				if(counter == 6){
					resultMapper.append("\n          ");
					counter = 0;
				}
				resultMapper.append(data.getColumn()).append(" ,");
				counter ++;
				
			}else{
				associations.add(data);
			}
		}
		
		for(Field data :associations){
			ComplexType type = (ComplexType) data.getType();
			if(type.getRelationship() != ComplexType.ONE_TO_ONE){
				continue;
			}
			resultMapper.append("\n          ").append(data.getName()).append(".").append(type.getRefIDColumn()).append(" as ").append(data.getName()).append("_id,")
			.append(data.getName()).append(".").append(type.getRefNameColumn()).append(" as ").append(data.getName()).append("_name,");
		}
	
	    resultMapper.deleteCharAt(resultMapper.length() - 1);
		resultMapper.append("\n");
		resultMapper.append("   from \n").append("   <include refid=\"table_name\"/>\n");
		
		for(Field data :associations){
			ComplexType type = (ComplexType) data.getType();
			if(type.getRelationship() != ComplexType.ONE_TO_ONE){
				continue;
			}
			resultMapper.append("   left join " ).append(type.getRefTable()).append(" ").append(data.getName()).append(" on ").append(data.getName()).append(".").append(type.getRefIDColumn()).append(" = a.").append(data.getColumn()).append("\n");
		}
		
		resultMapper.append("</sql>\n\n");
	}

}
