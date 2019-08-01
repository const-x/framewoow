package idv.const_x.tools.coder.generator.mapper;

import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.Field;
import idv.const_x.jdbc.table.meta.Type;


public class ResultMapGenerator {
	
	Context context;
	
	
	public String generator(Context context){
		this.context = context;
		 StringBuilder resultMapper = new  StringBuilder();
		 StringBuilder complexMapper = new  StringBuilder();
		 StringBuilder resultMapperPlus = new  StringBuilder();
		resultMapper.append("<resultMap id=\"").append(context.getEntity().toLowerCase()).append("Result").append("\" type=\"")
		.append(context.getEntity()).append("\" > \n"); 
		for (Field data : context.getFields()) {
			createMapperField(data, data.getType(), data.getAlias(),resultMapper,complexMapper,resultMapperPlus);
		}
		resultMapper.append(complexMapper.toString());
		resultMapper.append("</resultMap>\n\n");
		this.createOthers(resultMapperPlus);
		resultMapper.append(resultMapperPlus.toString());
		return resultMapper.toString();
	}
	
	
	private void createOthers(StringBuilder resultMapperPlus){
		if(context.isTree()){
			resultMapperPlus.append("<!-- 上级实体 -->\n");
			resultMapperPlus.append("<resultMap id=\"parentResult\" type=\"").append(context.getEntity().toLowerCase()).append("\"> \n");
			resultMapperPlus.append("  <id column=\"id\" jdbcType=\"DECIMAL\" property=\"id\" />\n");
			resultMapperPlus.append("  <result column=\"modifyTime\" jdbcType=\"VARCHAR\" property=\"modifytime\" />\n");
			resultMapperPlus.append("</resultMap>\n\n");
		}
		
		if(context.isNeedAudit()){
			resultMapperPlus.append("<!-- 审批记录 -->\n")
			.append("<resultMap id=\"auditResult\" type=\"OperationHistoryEntity\">\n")
			.append("    <!-- 操作对象-->\n")
			.append("    <id  property=\"target\" javaType=\"Long\" jdbcType=\"DECIMAL\"  column=\"TARGET_ID\" />\n")
			.append("    <!-- 操作时间 -->\n")
			.append("    <result property=\"operattime\" javaType=\"String\" jdbcType=\"CHAR\" column=\"OPERATTIME\" />\n")
			.append("    <!-- 审批状态 -->\n")
			.append("    <result property=\"status\" javaType=\"Integer\" jdbcType=\"DECIMAL\" column=\"STATUS\" />\n")
			.append("    <!-- 备注 -->\n")
			.append("    <result property=\"note\" javaType=\"String\" jdbcType=\"VARCHAR\" column=\"NOTE\" />\n")
			.append("    <!-- 操作人 -->\n")
			.append("    <association property=\"operator\" javaType=\"User\" jdbcType=\"DECIMAL\" column=\"OPERATOR_ID\"  >\n")
			.append("        <id column=\"operator_id\" jdbcType=\"DECIMAL\" property=\"id\" />\n")
			.append("        <result column=\"operator_name\" jdbcType=\"VARCHAR\" property=\"name\" />\n")
			.append("    </association>\n")
			.append("</resultMap>\n\n");
		}
	}
	
	/**
	 * <id column="id" jdbcType="DECIMAL" property="id" />
	 * <result column="name" jdbcType="VARCHAR" property="name" />
	 * <p>
	
	 *</p> 
	 * @param resultMapperPlus 
	 * @param entry 
	 */
	private  void createMapperField(Field field, Type type, String alias,StringBuilder result,StringBuilder complex, StringBuilder resultMapperPlus) {
		if(type.isPremiryKey()){
			result.append("    <!-- ").append(alias).append(" -->\n");
			result.append("    <id  property=\"id\" javaType=\"Long\" jdbcType=\"DECIMAL\"  column=\"").append(field.getColumn().toUpperCase()).append("\" />\n"); 
		}else{
			if(!type.isSimpleType()){
				ComplexType ctype = (ComplexType)type;
				complex.append("    <!-- ").append(alias).append(" -->\n");
				
				if(ctype.getRelationship() != ComplexType.ONE_TO_ONE){
					complex.append("    <collection property=\"").append(field.getName()).append("\" javaType=\"java.util.ArrayList\"  ofType=\"")
					.append(type.getJavaType()).append("\" ").append(" select=\"").append("find").append(CamelCaseUtils.toFristUpper(field.getName())).append("ById\" column=\"{")
					.append(context.getEntity().toLowerCase()).append("id=").append("id").append("}\" ").append("></collection>\n");
					
					
					resultMapperPlus.append("\n<!--关联查询").append(alias).append("-->\n");
					String refTable = context.getTable() + "_" + field.getName().toUpperCase() + "_REL"; 
					resultMapperPlus.append("<resultMap id=\"").append(type.getJavaType().toLowerCase()).append("Result\" type=\"").append(type.getJavaType()).append("\" >\n");
					resultMapperPlus.append("   <id column=\"").append(ctype.getRefIDColumn()).append("\" jdbcType=\"DECIMAL\" property=\"").append(ctype.getRefIDField()).append("\" />\n");
					resultMapperPlus.append("   <result column=\"").append(ctype.getRefNameColumn()).append("\" jdbcType=\"VARCHAR\" property=\"").append(ctype.getRefNameField()).append("\" />\n");
					resultMapperPlus.append("</resultMap> \n\n");
					resultMapperPlus.append("<select id=\"find").append(CamelCaseUtils.toFristUpper(field.getName())).append("ById\" parameterType=\"java.util.HashMap\" resultMap=\"").append(type.getJavaType().toLowerCase()).append("Result\">\n");
					
					resultMapperPlus.append("    select ").append(ctype.getRefIDColumn()).append(",").append(ctype.getRefNameColumn()).append(" from ").append(ctype.getRefTable())
					      .append(" where ").append(ctype.getRefIDColumn()).append(" in (\n").append("        select ").append(field.getColumn()).append(" from ").append(refTable).append(" where ")
					      .append(context.getEntity().toUpperCase()).append("_ID = #{").append(context.getEntity().toLowerCase()).append("id} \n    )\n");
					resultMapperPlus.append("</select>\n\n");
				}else{
					complex.append("    <association property=\"").append(field.getName()).append("\" javaType=\"").append(type.getJavaType())
					.append("\" jdbcType=\"").append(type.getJdbcType()).append("\" column=\"" ).append(field.getColumn().toUpperCase()).append("\" ");
//					通过left join关联
					complex.append(" > \n        <id column=\"").append(field.getName()).append("_id\" jdbcType=\"DECIMAL\" property=\"").append(ctype.getRefIDField()).append("\" />\n ")
					.append("       <result column=\"").append(field.getName()).append("_name\" jdbcType=\"VARCHAR\" property=\"").append(ctype.getRefNameField()).append("\" />\n    </association>\n");
			
//	                                           通过select查询子实体				
//					complex.append("select=\"");
//					if(type.getJavaType().equals(context.getEntity())){
//						complex.append("findParentById").append("\" />\n");
//					}else{
//						complex.append("find").append(field.getName()).append("\" />\n");
//						resultMapperPlus.append("\n<!--关联查询").append(alias).append("-->\n");
//					
//						resultMapperPlus.append("<resultMap id=\"").append(type.getJavaType().toLowerCase()).append("Result\" type=\"").append(type.getJavaType()).append("\" >\n");
//						resultMapperPlus.append("   <id column=\"").append(ctype.getRefIDColumn()).append("\" jdbcType=\"DECIMAL\" property=\"").append(ctype.getRefIDField()).append("\" />\n");
//						resultMapperPlus.append("   <result column=\"").append(ctype.getRefNameColumn()).append("\" jdbcType=\"VARCHAR\" property=\"").append(ctype.getRefNameField()).append("\" />\n");
//						resultMapperPlus.append("</resultMap> \n\n");
//						resultMapperPlus.append("<select id=\"find").append(field.getName()).append("\" parameterType=\"Long\" resultMap=\"").append(type.getJavaType().toLowerCase()).append("Result\">\n");
//						
//						resultMapperPlus.append("    select id,name from ").append(ctype.getRefTable()).append(" where ").append(ctype.getRefIDField()).append(" = #{0}\n");
//						resultMapperPlus.append("</select>\n");
//					}
				}
				System.out.println("已将"+ context.getEntity() + "对应属性 " + field.getName() +" 自动关联到表：" + ctype.getRefTable() + " 请在Mapper文件中确认是否正确 ");
			}else{
				result.append("    <!-- ").append(alias).append(" -->\n");
				result.append("    <result property=\"").append(field.getName()).append("\" javaType=\"").append(type.getJavaType())
				.append("\" jdbcType=\"").append(field.getType().getJdbcType()).append("\" column=\"").append(field.getColumn().toUpperCase()).append("\" />\n");;
			}	
		}
	}
}
