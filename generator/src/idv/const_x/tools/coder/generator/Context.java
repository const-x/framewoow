package idv.const_x.tools.coder.generator;

import idv.const_x.jdbc.DBType;
import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Context {

	String module;
	String moduleAlias;
	String component;
	String componentAlias;
	String entity;
	String entityAlias;
	
	String auther = "const.x";
	
	String basepath;
	String basepackage;
    String mapperBasepath;
    String javaBasepath;
    String viewBasepath;
    String templateBasepath;
    
    String tableName;
    String tableProfix;
    String AuditTableName;
    String sqlBasepath;
    String logBasepath;
    
    
    boolean isTree =false;
    boolean isLevel=false;
    boolean needAudit=false;
    boolean logicDel=false;
    boolean hasExport=false;
    
    DBType dbType = DBType.MYSQL;
    
    
    boolean mapperAndDao = true;
    boolean entityCodes = true;
    boolean otherCodes = true;
    boolean views = true;
    boolean sqls = true;
    boolean isSystemData = false;
    
    
    
    List<Field> fields = new ArrayList<Field>(); 
    
    
    private String toFristUpper(String string){
    	return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    public Context(){
    	templateBasepath = Context.class.getResource("/").getPath();
    	templateBasepath = templateBasepath.replace("bin", "src");
    	templateBasepath = templateBasepath + "\\idv\\const_x\\tools\\coder\\generator\\template";
    }
    
	public String getBasepackage() {
		return basepackage;
	}
  
	public boolean isMapperAndDao() {
		return mapperAndDao;
	}

	public void genMapperAndDao(boolean mapperAndDao) {
		this.mapperAndDao = mapperAndDao;
	}

	public boolean isEntityCodes() {
		return entityCodes;
	}

	public void genEntityCodes(boolean entityCodes) {
		this.entityCodes = entityCodes;
	}

	public boolean isOtherCodes() {
		return otherCodes;
	}

	public void genOtherCodes(boolean otherCodes) {
		this.otherCodes = otherCodes;
	}

	public boolean isViews() {
		return views;
	}

	public void genViews(boolean views) {
		this.views = views;
	}
	
	public boolean isSqls() {
		return sqls;
	}

	public void genSqls(boolean sqls) {
		this.sqls = sqls;
	}

 
	public String getSqlBasepath() {
		return sqlBasepath;
	}


	public void setSqlBasepath(String sqlBasepath) {
		this.sqlBasepath = sqlBasepath;
	}

	public String getTemplateBasepath() {
		return templateBasepath;
	}
  
	
	public boolean isNeedAudit() {
		return needAudit;
	}

	public void setNeedAudit(boolean needAudit) {
		this.needAudit = needAudit;
	}

	public String getTableProfix() {
		return tableProfix;
	}

	public void setTableProfix(String tableProfix) {
		this.tableProfix = tableProfix;
	}


	public String getAuditTable() {
		if(AuditTableName == null){
			AuditTableName = (module + "_" + CamelCaseUtils.toUnderlineName(entity)) + "_audit";
			AuditTableName = AuditTableName.toUpperCase();
		}
		return AuditTableName;
	}
	
	public String getModule() {
		return module;
	}
	
	public String getTable() {
		if(tableName == null){
			if(StringUtils.isNotBlank(tableProfix)){
				tableName = tableProfix +"_"+ module + "_" + CamelCaseUtils.toUnderlineName(entity);
			}else{
				tableName = (module + "_" + CamelCaseUtils.toUnderlineName(entity));
			}
			tableName = tableName.toUpperCase();
		}
		return tableName;
	}
	


	public boolean isTree() {
		return isTree;
	}
	
	public void setTree(boolean isTree) {
		this.isTree = isTree;
	}

	public String getMapperBasepath() {
		if(mapperBasepath == null){
			this.mapperBasepath = basepath +File.separator + "resources"+ File.separator +"mybatis"+ File.separator +"mapper";
		}
		return mapperBasepath;
	}
	
	public String getJavaBasepath() {
		if(javaBasepath == null){
			this.javaBasepath = basepath + File.separator + "java";
			if(StringUtils.isNotBlank(this.basepackage)){
				String[] ss = basepackage.split("\\.");
				for(String s : ss){
					javaBasepath += File.separator + s;
				}
			}
		}
		return javaBasepath;
	}

	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}
	
	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}

	public void setModule(String module) {
		this.module =  toFristUpper(module);
	}
    
	
	public String getViewBasepath() {
		if(viewBasepath == null){
			this.viewBasepath = basepath + File.separator +"webapp"+ File.separator +"WEB-INF"+ File.separator +"views";
		}
		return viewBasepath;
	}
	
	public void removeAllFields() {
		this.fields.clear();
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity =  toFristUpper(entity);;
	}

	
	public String getEntityAlias() {
		return entityAlias;
	}

	
	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void addFields(List<Field> fields) {
		this.fields.addAll(fields);
		for (Field field : fields) {
			if(field.isExport()){
				this.hasExport = true;
			}
		}
	}

	public void addField(Field field) {
		this.fields.add(field);
		if(field.isExport()){
			this.hasExport = true;
		}
	}

	public String getModuleAlias() {
		return moduleAlias;
	}

	public void setModuleAlias(String moduleAlias) {
		this.moduleAlias = moduleAlias;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = toFristUpper(component);
	}

	public String getComponentAlias() {
		return componentAlias;
	}

	public void setComponentAlias(String componentAlias) {
		this.componentAlias = componentAlias;
	}

	public boolean isLevel() {
		return isLevel;
	}

	public void setLevel(boolean isLevel) {
		this.isLevel = isLevel;
	}

	public boolean isLogicDel() {
		return logicDel;
	}

	public void setLogicDel(boolean logicDel) {
		this.logicDel = logicDel;
	}

	public boolean isSystemData() {
		return isSystemData;
	}

	public void setSystemData(boolean systemData) {
		this.isSystemData = systemData;
	}

	public String getAuther() {
		return auther;
	}

	public void setAuther(String auther) {
		this.auther = auther;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName.toUpperCase();
	}

	public void setAuditTableName(String auditTableName) {
		AuditTableName = auditTableName;
	}

	public DBType getDbType() {
		return dbType;
	}

	public void setDbType(DBType dbType) {
		this.dbType = dbType;
	}

	public String getLogBasepath() {
		return logBasepath;
	}

	public void setLogBasepath(String logBasepath) {
		this.logBasepath = logBasepath;
	}

	public boolean hasExport() {
		return hasExport;
	}

    
    
	
	
	
	
}
