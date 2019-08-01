package idv.const_x.tools.coder.generator.sql;

import org.apache.commons.lang3.StringUtils;

import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;


public class MetaGenerator {

	public String generator(Context context) {

		StringBuilder sql = new StringBuilder("\n\n#元数据记录 \n");
		if(context.getDbType() == DBType.MYSQL){
			sql.append("INSERT INTO SECURITY_META_ENTITY (NAME,ALIAS,TABLENAME,CLASSNAME, REFMODULE,MODULEALIAS) VALUES (");
		}else{
			sql.append("INSERT INTO SECURITY_META_ENTITY (ID,NAME,ALIAS,TABLENAME,CLASSNAME,REFMODULE,MODULEALIAS) VALUES (");
			sql.append("").append("SEQ_SECURITY_META_ENTITY.NEXTVAL").append(",");
		}
		
		sql.append("'").append(context.getEntity()).append("',");
		sql.append("'").append(context.getEntityAlias()).append("',");
		sql.append("'").append(context.getTable()).append("',");
		String packages = context.getBasepackage()+"."+ context.getModule().toLowerCase() + ".entity." + context.getEntity();
		sql.append("'").append(packages).append("',");
		sql.append("'").append(context.getModule()).append("',");
		sql.append("'").append(context.getModuleAlias()).append("');\n");
		
        String entityId = "( SELECT ID FROM SECURITY_META_ENTITY where NAME = '" + context.getEntity() +"' and REFMODULE = '"	 + context.getModule() +"')";	
		

		
		for (Field field : context.getFields()) {
			if(context.getDbType() == DBType.MYSQL){
				sql.append("INSERT INTO SECURITY_META_FIELD (NAME,DESCRIPTION,COLUMNNAME,JAVATYPE,MAXLENGTH,SCALE,NULLABLE,UNIQUENESS,EDITABLE,DEFVALUE,REFCLASS,RELATIONSHIP,REF_ENUM,ENTITY_ID) VALUES (");
			}else{
				sql.append("INSERT INTO SECURITY_META_FIELD (ID,NAME,DESCRIPTION,COLUMNNAME,JAVATYPE,MAXLENGTH,SCALE,NULLABLE,UNIQUENESS,EDITABLE,DEFVALUE,REFCLASS,RELATIONSHIP,REF_ENUM,ENTITY_ID) VALUES (");
				sql.append("").append("SEQ_SECURITY_META_FIELD.NEXTVAL").append(",");
			}
			sql.append("'").append(field.getName()).append("',");
			sql.append("'").append(field.getAlias()).append("',");
			sql.append("'").append(field.getColumn()).append("',");
			
			sql.append("'").append(field.getType().getJavaType()).append("',");
			sql.append("'").append(field.getType().getLength()).append("',");
			sql.append("'").append(field.getType().getscale()).append("',");
			sql.append(field.isNullable()? 1 : 0).append(",");
			sql.append(field.isUnique()? 1 : 0).append(",");
			sql.append(field.isEditable()? 1 : 0).append(",");
			sql.append("'").append(field.getDefaultValue()).append("',");
			
			if(field.getType() instanceof ComplexType){
				ComplexType complex = (ComplexType)field.getType();
				
				sql.append("'").append(complex.getRefClass()).append("',");
				sql.append("'").append(ComplexType.getRelationshipName(complex.getRelationship())).append("',");
			}else{
				sql.append("NULL,NULL,");
			}	
			
			if(field.getType() instanceof EnumType){
				EnumType enumtype = (EnumType)field.getType();
				//如果没有指定枚举类名，则在本模块下创建
				String enumName = enumtype.getClassName();
				if(StringUtils.isBlank(enumName)){
					enumName = context.getBasepackage() + "." + context.getModule().toLowerCase()+".entity."+ field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1) + "Enum";
				}	
				sql.append("'").append(enumName).append("',");
			}else{
				sql.append("NULL,");
			}
			
			
			sql.append("").append(entityId).append(");\n");
		}
		
		
		
        return sql.toString();
	}
	


}
