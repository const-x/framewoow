package idv.const_x.tools.coder.generator.mapper;

import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;


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
        resultMapper.append(this.createMysqlInsert(table, premiry));
        resultMapper.append(createdelete(table,premiry,context.isLogicDel()));
        resultMapper.append(createSelects(table,premiry,modifytime,context.isLogicDel()));
        resultMapper.append(createupdate(table,premiry));
        return resultMapper.toString();
    }


    private String createdelete(String table,Field premiry,boolean logicdel) {
        StringBuilder delete = new StringBuilder();
        delete.append("    <!--删除操作-->\n");
        delete.append("    <delete id=\"deleteByKey\" parameterType=\"hashMap\">\n");
        delete.append("     DELETE FROM\n");
        delete.append("     <include refid=\"table_name\"/>\n");
        delete.append("        WHERE ").append(premiry.getColumn()).append("=#{").append(premiry.getName()).append("}\n");
        delete.append("        <if test=\"corpId != null\">\n");
        delete.append("            and corp_id = #{corpId}\n");
        delete.append("        </if>\n");
        delete.append("    </delete>\n");
        delete.append("    \n");
        delete.append("    <delete id=\"deleteByCorpId\" parameterType=\"hashMap\">\n");
        delete.append("     DELETE FROM\n");
        delete.append("     <include refid=\"table_name\"/>\n");
        delete.append("        WHERE \n");
        delete.append("        corp_id = #{corpId}\n");
        delete.append("    </delete>\n");
        delete.append("\n");
    
        return delete.toString();
    }

	private Object createupdate(String table,Field premiry) {
		StringBuilder update = new StringBuilder();
		update.append("    <!--更新操作-->\n");
        update.append("    <update id=\"update").append(context.getRepalce().get("entryFristUpper")).append("ByKey\" parameterType=\"")
                .append(context.getRepalce().get("entryFristlower")).append("\">\n");
        update.append("         UPDATE\n");
        update.append("         <include refid=\"table_name\"/>\n");
        update.append("         <set>\n           ");

        int counter = 0;
        for (Field data : context.getFields()) {
            if(data.isEditable() || data.getName().equals("modifyer") ||data.getName().equals("modifytime")  ){
                if(counter == 4){
                    update.append("\n           ");
                    counter = 0;
                }
                if(data.getType().isSimpleType()){
                    update.append(data.getColumn()).append(" = #{").append(data.getName()).append(",jdbcType=").append(data.getType().getJdbcType()).append("},");
                }else{
                    ComplexType type = (ComplexType)data.getType();
                    if(type.getRelationship() == ComplexType.ONE_TO_ONE){
                        update.append(data.getColumn()).append(" = #{").append(data.getName()).append(".id,jdbcType=BIGINT},");
                    }
                }
                counter++;
            }
        }
        update.deleteCharAt(update.length() -1);
        update.append("\n         </set>\n");
        update.append("        WHERE ").append(premiry.getColumn()).append("=#{").append(premiry.getName()).append("}\n");
        update.append("    </update>\n");
        update.append("    \n");


        update.append("    <!--更新操作-->\n");
        update.append("    <update id=\"update").append(context.getRepalce().get("entryFristUpper")).append("SelectiveByKey\" parameterType=\"")
                .append(context.getRepalce().get("entryFristlower")).append("\">\n");
        update.append("         UPDATE\n");
        update.append("         <include refid=\"table_name\"/>\n");
        update.append("         <set>\n       ").append("        <trim prefix=\"(\" suffix=\")\" prefixOverrides=\",\">\n");


        for (Field data : context.getFields()) {
            if(data.isEditable() || data.getName().equals("modifyer") ||data.getName().equals("modifytime")  ){
                if(data.isPrimary()){
                    continue;
                }
                if(data.getType().isSimpleType()){
                    update.append("                 <if test=\"").append(data.getName()).append(" != null \">\n")
                            .append("                       ").append(data.getColumn()).append(" = #{").append(data.getName()).append("}\n                 </if>\n");
                }else{
                    ComplexType type = (ComplexType)data.getType();
                    if(type.getRelationship() == ComplexType.ONE_TO_ONE){
                        update.append("                 <if test=\"").append(data.getName()).append(" != null \">\n")
                                .append("            ").append(data.getColumn()).append(" = #{").append(data.getName()).append(".id}\n            </if>\n");
                    }
                }
            }
        }
        update.append("               </trim>\n         </set>\n");
        update.append("        WHERE ").append(premiry.getColumn()).append("=#{").append(premiry.getName()).append("}\n");
        update.append("    </update>\n");
        update.append("    \n");

		return update.toString();
	}



    private String createSelects(String table,Field premiry,String modifytime, boolean logicdel) {
        StringBuilder selects = new StringBuilder();
        selects.append("    <!--查询操作-->\n");
        selects.append("    <select id=\"get").append(context.getRepalce().get("entryFristUpper")).append("ListWithPage\" parameterType=\"hashMap\" resultMap=\"").append(context.getRepalce().get("entryFristlower")).append("\" databaseId=\"mysql\">\n");
        selects.append("        <include refid=\"base_select\"/>\n");
        selects.append("        <include refid=\"BusinessDeployCommon.where_base\"/>\n");
        selects.append("        <include refid=\"BusinessDeployCommon.order_base\"/>\n");
        selects.append("        <include refid=\"BusinessDeployCommon.limit\"/>\n");
        selects.append("    </select>\n\n");
        selects.append("    <select id=\"get").append(context.getRepalce().get("entryFristUpper")).append("ListCount\" parameterType=\"hashMap\" resultType=\"int\" databaseId=\"mysql\">\n");
        selects.append("        SELECT count(1) FROM\n");
        selects.append("        <include refid=\"table_name\"/>\n");
        selects.append("        <include refid=\"BusinessDeployCommon.where_base\"/>\n");
        selects.append("    </select>\n\n");
        selects.append("    <select id=\"get").append(context.getRepalce().get("entryFristUpper")).append("List\" parameterType=\"hashMap\" resultMap=\"").append(context.getRepalce().get("entryFristlower")).append("\" databaseId=\"mysql\">\n");
        selects.append("        <include refid=\"base_select\"/>\n");
        selects.append("        <include refid=\"BusinessDeployCommon.where_base\"/>\n");
        selects.append("        <include refid=\"BusinessDeployCommon.order_base\"/>\n");
        selects.append("    </select>\n\n");
        selects.append("    <select id=\"get").append(context.getRepalce().get("entryFristUpper")).append("ByKey\" parameterType=\"hashMap\" resultMap=\"").append(context.getRepalce().get("entryFristlower")).append("\" databaseId=\"mysql\">\n");
        selects.append("        <include refid=\"base_select\"/>\n");
        selects.append("        WHERE ").append(premiry.getColumn()).append("=#{").append(premiry.getName()).append("}\n");
        selects.append("        <if test=\"corpId != null\">\n");
        selects.append("            and corp_id = #{corpId}\n");
        selects.append("        </if>\n");
        selects.append("    </select>\n");
        selects.append("    \n");
        selects.append("    \n");

        return selects.toString();
    }



    private Object createMysqlInsert(String table,Field premiry) {
        StringBuilder insert = new StringBuilder();
        insert.append("    <!--保存操作-->\n");
        insert.append("    <insert id=\"insert").append(context.getRepalce().get("entryFristUpper")).append("\" parameterType=\"").append(context.getRepalce().get("entryFristlower"))
                .append("\"  useGeneratedKeys=\"true\" keyProperty=\"")
                .append(premiry.getName()).append("\"  > \n");

        insert.append("        insert into \n").append("        <include refid=\"table_name\"/>\n").append("        <trim prefix=\"(\" suffix=\")\" prefixOverrides=\",\">\n");
        for (Field data : context.getFields()) {
            if(data.isPrimary()){
                continue;
            }
            if(data.getType().isSimpleType()){
                insert.append("            <if test=\"").append(data.getName()).append(" != null \">\n")
                        .append("               ").append(data.getColumn()).append("\n            </if>\n");
            }else{
                ComplexType type = (ComplexType)data.getType();
                if(type.getRelationship() == ComplexType.ONE_TO_ONE){
                    insert.append("            <if test=\"").append(data.getName()).append(" != null \">\n")
                            .append("               ").append(data.getColumn()).append("\n            </if>\n");
                }
            }
        }
        insert.append("        </trim>\n").append("        values\n        <trim prefix=\"(\" suffix=\")\" prefixOverrides=\",\">\n");
        for (Field data : context.getFields()) {
            if(data.isPrimary()){
                continue;
            }
            if(data.getType().isSimpleType()){
                insert.append("            <if test=\"").append(data.getName()).append(" != null \">\n")
                        .append("                #{").append(data.getName()).append("}\n            </if>\n");
            }else{
                ComplexType type = (ComplexType)data.getType();
                if(type.getRelationship() == ComplexType.ONE_TO_ONE){
                    insert.append("            <if test=\"").append(data.getName()).append(" != null \">\n")
                            .append("             #{").append(data.getName()).append(".id}\n            </if>\n");
                }
            }
        }
        insert.append("        </trim>\n    </insert>\n\n");

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


