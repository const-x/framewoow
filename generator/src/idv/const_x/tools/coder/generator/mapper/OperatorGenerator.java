package idv.const_x.tools.coder.generator.mapper;

import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class OperatorGenerator{
	
	Context context;

	public String generator(Context context){
		this.context = context;
		String table = context.getTable().toUpperCase();
		Field premiry = null;
		String modifytime = "modifytime";
		for (Field data : context.getFields()) {
			if(data.getType().isPremiryKey()){
				premiry = data;
			}
			if(data.getName().equalsIgnoreCase("modifytime")){
				modifytime = data.getColumn();
			}
		}
		StringBuilder resultMapper = new  StringBuilder();
		resultMapper.append(createSelects(table,premiry,modifytime,context.isLogicDel()));
		if(context.getDbType() == DBType.ORACLE){
			resultMapper.append(this.createOracleInsert(table, premiry));
		}else{
			resultMapper.append(this.createMysqlInsert(table, premiry));
		}

		resultMapper.append(createdelete(table,premiry,context.isLogicDel()));
		resultMapper.append(createupdate(table,premiry));
		resultMapper.append(createRef(table,premiry));
        return resultMapper.toString();
	}


	private String createdelete(String table,Field premiry,boolean logicdel) {
		StringBuilder delete = new StringBuilder();
		delete.append("<!--删除操作-->\n");
		delete.append("<delete id=\"delete\" parameterType=\"java.lang.reflect.Array\">\n");
		delete.append("    delete from ").append(table).append(" where ").append(premiry.getColumn()).append(" in \n");
		delete.append("    <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> \n ");
		delete.append("      #{item}\n").append("    </foreach>\n").append("</delete>\n\n");
		
		delete.append("<delete id=\"deleteByWhere\" parameterType=\"String\">\n");
		delete.append("    delete from ").append(table).append(" where  ${where} \n").append("</delete>\n\n");
		
		if(logicdel){
			delete.append("<!--逻辑删除操作-->\n");
			delete.append("<update id=\"deleteInLogic\" parameterType=\"map\">\n");
			delete.append("    update ").append(table).append(" set DR = 1, modifytime = #{modifytime}, MODIFYER = #{modifyer} where ").append(premiry.getColumn()).append(" in \n");
			delete.append("    <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> \n ");
			delete.append("      #{item}\n").append("    </foreach>\n").append("</update>\n\n");
			
			delete.append("<update id=\"deleteByWhereInLogic\" parameterType=\"map\">\n");
			delete.append("    update  ").append(table).append(" set DR = 1, MODIFYTIME = #{modifytime}, MODIFYER = #{modifyer}  where  ${where} \n").append("</update>\n\n");
		}
        
		if(context.isNeedAudit()){
			delete.append("<delete id=\"deleteAuditById\" parameterType=\"java.lang.reflect.Array\">\n");
			delete.append("    delete from ").append(context.getAuditTable()).append(" where ").append("target_id  in \n");
			delete.append("    <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> \n ");
			delete.append("      #{item}\n").append("    </foreach>\n").append("</delete>\n\n");
		}
		
		return delete.toString();
	}

	private Object createupdate(String table,Field premiry) {
		StringBuilder update = new StringBuilder();
		update.append("<!--更新操作-->\n");
		update.append("<update id=\"update\" parameterType=\"").append(context.getEntity()).append("\" >\n");
		update.append("    update ").append(table).append("\n  <set>\n    ");
		int counter = 0; 
		for (Field data : context.getFields()) {
			if(data.isEditable() || data.getName().equals("modifyer") ||data.getName().equals("modifytime")  ){
				if(counter == 3){
					update.append("\n    ");
					counter = 0;
				}
				if(data.getType().isSimpleType()){
					update.append(data.getColumn()).append(" = #{").append(data.getName()).append(",jdbcType=").append(data.getType().getJdbcType()).append("},");
				}else{
					ComplexType type = (ComplexType)data.getType();
					if(type.getRelationship() == ComplexType.ONE_TO_ONE){
						update.append(data.getColumn()).append(" = #{").append(data.getName()).append(".id,jdbcType=DECIMAL},");
					}
				}
				counter++;
			}
		}
		update.deleteCharAt(update.length() - 1);
		update.append("\n  </set>").append("\n    where ").append(premiry.getColumn()).append(" = #{id}\n").append("</update>\n\n");
		
		if(context.isNeedAudit()){
			update.append("<update id=\"updateAudit\" parameterType=\"map\" >\n");
			update.append("    update ").append(table).append("\n  <set>\n    ");
			update.append("    AUDIT_STATUS = #{audit,jdbcType=DECIMAL},AUDITTIME = #{audittime,jdbcType=CHAR},AUDITER = #{auditer,jdbcType=DECIMAL},MODIFYER = #{auditer,jdbcType=DECIMAL},MODIFYTIME = #{audittime,jdbcType=CHAR}");
			update.append("\n  </set>").append("\n    where ").append(premiry.getColumn()).append(" = #{id}\n").append("</update>\n\n");
		}
		
		return update.toString();
	}

	


	private String createSelects(String table,Field premiry,String modifytime, boolean logicdel) {
		StringBuilder selects = new StringBuilder();
		String result = context.getEntity().toLowerCase() + "Result";
		selects.append("<!--版本验证-->\n");
		selects.append("<select id=\"findLastModifyTime\" parameterType=\"java.lang.reflect.Array\" resultMap=\"").append(result).append("\" > \n");
		selects.append("   select ").append(premiry.getColumn()).append(",").append(modifytime).append(" from ").append(context.getTable()).append(" where ").append(premiry.getColumn()).append(" in\n" );
		selects.append("   <foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">\n");
		selects.append("       #{item}\n").append("	</foreach>\n");
		selects.append("</select>\n\n");
		if(context.isTree()){
			selects.append("<!--查询上级-->\n");
			selects.append("<select id=\"findParentById\" parameterType=\"Long\" resultMap=\"").append("parentResult").append("\" > \n");
			selects.append("   select ").append(premiry.getColumn()).append(",name from ").append(context.getTable());
			selects.append("   <where>\n");
			selects.append("      ").append(premiry.getColumn()).append(" = #{0} \n");
			selects.append("   </where>\n");
			selects.append("</select>\n\n");	
		}
		selects.append("<!--查询操作-->\n");
		selects.append("<select id=\"findById\" parameterType=\"Long\" resultMap=\"").append(result).append("\" > \n");
		selects.append("    <include refid=\"select\"/>\n").append("    where a.").append(premiry.getColumn()).append(" =  #{id}\n");
		selects.append("</select>\n\n");
		
		selects.append("<select id=\"findByIds\" parameterType=\"java.lang.reflect.Array\" resultMap=\"").append(result).append("\" > \n");
		selects.append("    <include refid=\"select\"/>\n").append("    where a.").append(premiry.getColumn()).append(" in \n");
		selects.append("    <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> \n ");
		selects.append("      #{item}\n").append("    </foreach>\n");
		selects.append("</select>\n\n");
		
		
		StringBuilder where = new StringBuilder();
		where.append("   <if test=\"userId!=null  and userId!='' \">\n").append("      and a.creater = #{userId} \n").append("    </if>\n");
		if(context.isTree()){
			where.append("   <if test=\"condition.parent!=null and condition.parent.").append(premiry.getColumn()).append("!=null   \">\n")
			.append("      and a.").append(premiry.getColumn()).append(" = #{condition.parent.").append(premiry.getColumn()).append("} \n").append("    </if>\n");
			
		}
		where.append("<!--\n   <if test=\"condition.keywords!=null  and condition.keywords!='' \">\n").append("        <![CDATA[ and a.name like '%'||#{condition.keywords}||'%'   ]]>\n").append("    </if>\n-->\n");
		
		for (Field field : context.getFields()) {
			if(field.isCondition()){
				if(field.getType().isSimpleType()){
					 if(field.getType() == SimpleTypeEnum.DATETIME || field.getType() == SimpleTypeEnum.DATE){
							where.append("    <if test=\"condition.").append(field.getName()).append("Begin!=null  and condition.").append(field.getName()).append("Begin!='' \">\n")
							   .append("        <![CDATA[ and a.").append(field.getColumn()).append(" >= #{condition.").append(field.getName()).append("Begin} ]]>\n").append("    </if>\n");
							where.append("    <if test=\"condition.").append(field.getName()).append("End!=null  and condition.").append(field.getName()).append("End!='' \">\n")
							   .append("        <![CDATA[ and a.").append(field.getColumn()).append(" <= #{condition.").append(field.getName()).append("End} ]]>\n").append("    </if>\n");
					}else if(field.getType().getJavaType().equals("String")){
						where.append("    <if test=\"condition.").append(field.getName()).append("!=null  and condition.").append(field.getName()).append("!='' \">\n")
						   .append("        <![CDATA[ and a.").append(field.getColumn()).append(" like '%'||#{condition.").append(field.getName()).append("}||'%' ]]>\n").append("    </if>\n");
					}else{
						where.append("    <if test=\"condition.").append(field.getName()).append("!=null\">\n")
						   .append("        <![CDATA[ and a.").append(field.getColumn()).append(" = #{condition.").append(field.getName()).append("} ]]>\n").append("    </if>\n");
					}
				}else{
					ComplexType type = (ComplexType)field.getType();
					where.append("    <if test=\"condition.").append(field.getName()).append("!=null \">\n");
					where.append("      <if test=\"condition.").append(field.getName()).append(".").append(type.getRefNameField()).append("!=null  and condition.").append(field.getName()).append(".").append(type.getRefNameField()) .append("!='' \">\n")
					   .append("          <![CDATA[ and a.").append(field.getColumn()).append(" in ( \n")   
					   .append("           select ").append(type.getRefIDColumn()).append(" from ").append(type.getRefTable()).append(" where ").append(type.getRefNameColumn()).append(" like '%'||#{condition.").append(field.getName()).append(".").append(type.getRefNameField()) .append("}||'%' ) ]]>\n")
					   .append("        </if>\n");
					where.append("      <if test=\"condition.").append(field.getName()).append(".").append(type.getRefIDField()).append("!=null \">\n")
					   .append("          <![CDATA[ and a.").append(field.getColumn()).append(" in ( \n")   
					   .append("           select ").append(type.getRefIDColumn()).append(" from ").append(type.getRefTable()).append(" where ").append(type.getRefIDColumn()).append(" = #{condition.").append(field.getName()).append(".").append(type.getRefIDField()) .append("} ) ]]>\n")
					   .append("        </if>\n");
					where.append("    </if>\n");
				}
			}
		}
		if(context.isTree()){
			where.append("   <if test=\"condition.parent!=null  and condition.parent.id!=null \">\n")
			   .append("      <![CDATA[ and a.PARENT_ID = #{condition.parent.").append(premiry.getName()).append("} ]]>\n")
			   .append("    </if>\n");
		}
		if(logicdel){
			where.append("   and a.dr = 0 \n");
		}
		String whereStr = where.toString();
		
		
		selects.append("<select id=\"findByPage\" parameterType=\"map\" resultMap=\"").append(result).append("\" > \n");
		selects.append("   <include refid=\"select\"/>\n");
		selects.append("   <where>\n");
		selects.append(whereStr);
		selects.append("   </where>\n");
		selects.append("   <if test=\"order!=null  and order!='' \">\n").append("      ORDER BY  ${order}  \n").append("    </if>\n");
		selects.append("</select>\n\n");
        
		selects.append("<select id=\"findFieldsByPage\" parameterType=\"map\" resultMap=\"").append(result).append("\" > \n");
		selects.append("   <choose>\n     <when test=\"fields!=null  \">\n");
		selects.append("       select\n").append("       <foreach collection=\"fields\" item=\"item\" index=\"index\" separator=\",\" >\n");
		selects.append("         a.${item}\n       </foreach>\n       from ").append(context.getTable()).append("\n     </when><otherwise>\n       <include refid=\"select\"/>\n");
		selects.append("     </otherwise>\n   </choose>\n");
		selects.append("   <where>\n");
		selects.append(whereStr);
		selects.append("   </where>\n");
		selects.append("   <if test=\"order!=null  and order!='' \">\n").append("      ORDER BY  ${order}  \n").append("    </if>\n");
		selects.append("</select>\n\n");
		
		
		selects.append("<select id=\"findFieldsByWherePage\" parameterType=\"map\" resultMap=\"").append(result).append("\" > \n");
		selects.append("   <choose>\n     <when test=\"fields!=null  \">\n");
		selects.append("       select\n").append("       <foreach collection=\"fields\" item=\"item\" index=\"index\" separator=\",\" >\n");
		selects.append("         ${item}\n       </foreach>\n       from ").append(context.getTable()).append(" a \n     </when><otherwise>\n       <include refid=\"select\"/>\n");
		selects.append("     </otherwise>\n   </choose>\n");
		selects.append("   <where>\n");
		selects.append("     <if test=\"where!=null  and where!='' \">\n").append("      ${where}  \n").append("     </if>\n");
		if(logicdel){
			selects.append("     and a.dr = 0 \n");
		}
		selects.append("   </where>\n");
		selects.append("   <if test=\"order!=null  and order!='' \">\n").append("      ORDER BY  ${order}  \n").append("    </if>\n");
		selects.append("</select>\n\n");		
	
		
		//如果数据有层级关系 则添加findByparentId方法
		if(context.isTree()){
			selects.append("<select id=\"findByParentId\" parameterType=\"java.lang.reflect.Array\" resultMap=\"").append(result).append("\" > \n");
			selects.append("   <include refid=\"select\"/>\n");
			selects.append("   <where>\n");
			selects.append("   	PARENT_ID IN\n");
			selects.append("   	<foreach item=\"item\" index=\"index\" collection=\"parentId\" open=\"(\" separator=\",\" close=\")\"> \n");
			selects.append("   		#{item}\n");
			selects.append("   	</foreach>\n");
			selects.append("   </where>\n");
			selects.append("</select>\n\n");
			
		}
		
		if(context.isNeedAudit()){
			selects.append("<select id=\"findAuditByPage\" parameterType=\"Long\" resultMap=\"").append("auditResult\" > \n");
			selects.append("    <include refid=\"selectAudit\"/>\n").append("    where a.target_id = #{id} \n");
			selects.append("</select>\n\n");
		}
		selects.append("\n\n");
		return selects.toString();
	}
	
	
	private Object createOracleInsert(String table,Field premiry) {
		StringBuilder insert = new StringBuilder();
		insert.append("<!--保存操作-->\n");
		insert.append("<insert id=\"save\" parameterType=\"").append(context.getEntity()).append("\"  useGeneratedKeys=\"false\"  > \n");
		insert.append("    <selectKey resultType=\"LONG\" order=\"BEFORE\" keyProperty=\"").append(premiry.getName()).append("\">\n")
		.append("      SELECT SEQ_").append(table).append(".NEXTVAL as id from DUAL\n    </selectKey>\n");
		
		insert.append("    insert into ").append(table).append("\n          (");
		int counter = 0;
		for (Field data : context.getFields()) {
			if(counter == 8){
				insert.append("\n          ");
				counter = 0;
			}
			if(data.getType().isSimpleType()){
				insert.append(data.getColumn()).append(" ,");
			}else{
				ComplexType type = (ComplexType)data.getType();
				if(type.getRelationship() == ComplexType.ONE_TO_ONE){
					insert.append(data.getColumn()).append(" ,");
				}
			}
			counter ++;
		}
		insert.deleteCharAt(insert.length() - 1);
		insert.append(") \n    values\n          (");
		counter = 0; 
		for (Field data : context.getFields()) {
			if(counter == 5){
				insert.append("\n          ");
				counter = 0;
			}
			
			if(data.getType().isSimpleType()){
				insert.append("#{").append(data.getName()).append(",jdbcType=").append(data.getType().getJdbcType()).append("},");
			}else{
				ComplexType type = (ComplexType)data.getType();
				if(type.getRelationship() == ComplexType.ONE_TO_ONE){
					insert.append("#{").append(data.getName()).append(".id,jdbcType=DECIMAL} ,");
				}
				
			}
			counter++;
		}
		insert.deleteCharAt(insert.length() - 1);
		insert.append(")\n</insert>\n\n");
		
		if(context.isNeedAudit()){
			insert.append("<!--保存审批-->\n");
			insert.append("<insert id=\"saveAudit\" parameterType=\"OperationHistoryEntity").append("\"  useGeneratedKeys=\"false\"  > \n");
			String auditTable = context.getAuditTable();
			insert.append("    insert into ").append(auditTable).append("\n");
			insert.append("        (OPERATTIME ,STATUS ,OPERATOR_ID ,NOTE ,TARGET_ID )\n");
			insert.append("    values\n");
			insert.append("        (#{operattime,jdbcType=CHAR} ,#{status,jdbcType=DECIMAL} ,#{operator.id,jdbcType=DECIMAL} ,#{note,jdbcType=VARCHAR} ,#{target,jdbcType=DECIMAL} )\n");
			insert.append("</insert>\n\n");
		}
		
		return insert.toString();
	}

	private Object createMysqlInsert(String table,Field premiry) {
		StringBuilder insert = new StringBuilder();
		insert.append("<!--保存操作-->\n");
		insert.append("<insert id=\"save\" parameterType=\"").append(context.getEntity()).append("\"  useGeneratedKeys=\"true\" keyProperty=\"")
		.append(premiry.getName()).append("\"  > \n");

		insert.append("    insert into ").append(table).append("\n          (");
		int counter = 0;
		for (Field data : context.getFields()) {
			if(data.isPrimary()){
				continue;
			}
			if(counter == 8){
				insert.append("\n          ");
				counter = 0;
			}
			if(data.getType().isSimpleType()){
				insert.append(data.getColumn()).append(" ,");
			}else{
				ComplexType type = (ComplexType)data.getType();
				if(type.getRelationship() == ComplexType.ONE_TO_ONE){
					insert.append(data.getColumn()).append(" ,");
				}
			}
			counter ++;
		}
		insert.deleteCharAt(insert.length() - 1);
		insert.append(") \n    values\n          (");
		counter = 0; 
		for (Field data : context.getFields()) {
			if(data.isPrimary()){
				continue;
			}
			if(counter == 8){
				insert.append("\n          ");
				counter = 0;
			}
			if(data.getType().isSimpleType()){
				insert.append("#{").append(data.getName()).append("} ,");
			}else{
				ComplexType type = (ComplexType)data.getType();
				if(type.getRelationship() == ComplexType.ONE_TO_ONE){
					insert.append("#{").append(data.getName()).append(".id} ,");
				}
			}
			counter++;
		}
		insert.deleteCharAt(insert.length() - 1);
		insert.append(")\n</insert>\n\n");
		
		if(context.isNeedAudit()){
			insert.append("<!--保存审批-->\n");
			insert.append("<insert id=\"saveAudit\" parameterType=\"OperationHistoryEntity").append("\"  useGeneratedKeys=\"false\"  > \n");
			String auditTable = context.getAuditTable();
			insert.append("    insert into ").append(auditTable).append("\n");
			insert.append("        (OPERATTIME ,STATUS ,OPERATOR_ID ,NOTE ,TARGET_ID )\n");
			insert.append("    values\n");
			insert.append("        (#{operattime,jdbcType=CHAR} ,#{status,jdbcType=DECIMAL} ,#{operator.id,jdbcType=DECIMAL} ,#{note,jdbcType=VARCHAR} ,#{target,jdbcType=DECIMAL} )\n");
			insert.append("</insert>\n\n");
		}
		
		return insert.toString();
	}

	private String createRef(String table,Field premiry) {
		StringBuilder ref = new StringBuilder();
		for (Field field : context.getFields()) {
			if(!field.getType().isSimpleType()){
				ComplexType type = (ComplexType)field.getType();
				if(type.getRelationship() != ComplexType.ONE_TO_ONE){
					String refTable = context.getTable() + "_" + field.getName().toUpperCase() + "_REL"; 
					if(context.getDbType() == DBType.ORACLE){
						ref.append("<!--保存").append(field.getAlias()).append("-->\n")
						.append("<insert id=\"save").append(CamelCaseUtils.toFristUpper(field.getName())).append("\" parameterType=\"map\"   useGeneratedKeys=\"false\"   >\n")
						.append("    insert into ").append(refTable).append(" ( ").append(context.getEntity().toUpperCase()).append("_ID,").append(field.getColumn()).append(" ) \n")
						.append("    select temp.* from (\n")
						.append("    <foreach collection=\"entitys\" item=\"item\" index=\"index\" separator=\"UNION ALL \" >\n ")
						.append("    	SELECT #{").append(context.getEntity().toLowerCase()).append("Id} as ").append(context.getEntity().toUpperCase()).append("_ID,#{")
						.append("item.").append(type.getRefIDField()).append("} as ").append(field.getColumn()).append(" FROM dual \n")
						.append("    </foreach>\n    ) temp\n</insert>\n\n");
					}else{
						ref.append("<!--保存").append(field.getAlias()).append("-->\n")
						.append("<insert id=\"save").append(CamelCaseUtils.toFristUpper(field.getName())).append("\" parameterType=\"map\"   useGeneratedKeys=\"false\"   >\n")
						.append("    insert into ").append(refTable).append(" ( ").append(context.getEntity().toUpperCase()).append("_ID,").append(field.getColumn()).append(" ) \n")
	                    .append("    values \n")
	                    .append("    <foreach collection=\"entitys\" item=\"item\" index=\"index\" separator=\",\" >\n ")
	                    .append("        #{").append(context.getEntity().toLowerCase()).append("Id}, #{").append("item.").append(type.getRefIDField()).append("} )\n")
						.append("    </foreach>\n</insert>\n\n");
					}
					
					ref.append("<!--清除").append(field.getAlias()).append("-->\n")
					.append("<delete id=\"delete").append(CamelCaseUtils.toFristUpper(field.getName())).append("ById\" parameterType=\"java.lang.reflect.Array\">\n")
					.append("    delete from ").append(refTable).append(" where ").append(context.getEntity().toUpperCase()).append("_ID in\n")
					.append("    <foreach item=\"item\" index=\"index\" collection=\"").append(context.getEntity().toLowerCase()).append("ids\" open=\"(\" separator=\",\" close=\")\"> \n")
					.append("       #{item}\n    </foreach>\n</delete>\n\n");
                    
				}
			}
		}
		return ref.toString();
	}
}
