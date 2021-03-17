package idv.const_x.tools.coder.generator.java;

import idv.const_x.file.FileOutWriter;
import idv.const_x.utils.FileUtils;
import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.VOAttributeUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.jdbc.table.meta.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class POJOGenerator {

	public List<File> generator(JavaContext context) {
		List<File> files = new ArrayList<>();
		Context ctx = context.getBaseContext();
		String entry = ctx.getEntity();
		String module = ctx.getModule().toLowerCase();
		String alias = ctx.getEntityAlias();
		String basepath = ctx.getJavaBasepath() + File.separator;
		List<Field> fields = ctx.getFields();
		String basepackage = ctx.getBasepackage();
		
		String saveToFile = basepath  + module+ File.separator+ "pojo"  + File.separator + entry + ".java";
		File file = FileUtils.createFile(saveToFile);
		FileOutWriter writer = new FileOutWriter(file,true);
		files.add(file);
		writer.write(this.createEntry(fields, alias, module, entry,basepackage,context.isTree(),context.getBaseContext().isLevel(),context.getBaseContext().isNeedAudit()));
		writer.close();
		
		for (Field data : fields) {
			Type type = data.getType();
			if(type  instanceof EnumType){
				EnumType enumtype = (EnumType)type;
				//如果没有指定枚举类名，则在本模块下创建
				if(StringUtils.isBlank(enumtype.getClassName())){
					String enumName = data.getName().substring(0, 1).toUpperCase() + data.getName().substring(1) + "Enum";
					saveToFile = basepath + module + File.separator + "pojo" + File.separator + enumName + ".java";
					file = FileUtils.createFile(saveToFile);
					writer = new FileOutWriter(file,true);
					files.add(file);
					writer.write(this.createEnum(data, enumName, module, basepackage));
					writer.close();
				}
			}
		}
		
//		saveToFile = basepath + module + File.separator + "view" + File.separator + entry + "View.java";
//		file = FileUtils.createFile(saveToFile);
//		files.add(file);
//		writer = new FileOutWriter(file,true);
//		writer.write(this.createView(fields, alias, module, entry,basepackage,context.isTree(),context.getBaseContext().isNeedAudit()));
//		writer.close();
		
		saveToFile = basepath + module + File.separator + "query" + File.separator + entry + "Query.java";
		file = FileUtils.createFile(saveToFile);
		files.add(file);
		writer = new FileOutWriter(file,true);
		writer.write(this.createContidtion(fields, alias, module, entry,basepackage,context.isTree(),context.getBaseContext().isLevel()));
		writer.close();
		
		return files;
	}
	
	
	private String createContidtion(List<Field> fields, String alias,
			String module, String entry, String basepackage, boolean tree,
			boolean level) {
		StringBuilder builder = new StringBuilder();
		String packages = basepackage+"."+ module + ".query" ;
		builder.append("package ").append(packages).append(";\n\n\n");
		builder.append("import com.raycloud.ding.commons.model.enums.Comparison;\n");
		builder.append("import com.raycloud.ding.commons.model.enums.SortDirection;\n");
		builder.append("import com.raycloud.ding.commons.model.model.Condition;\n");
		builder.append("import com.raycloud.ding.commons.model.model.ConditionArea;\n");
		builder.append("import com.raycloud.ding.commons.model.model.ConditionGroup;\n");
		builder.append("import com.raycloud.ding.commons.model.model.OrderField;\n");
		builder.append("import com.raycloud.ding.commons.model.query.BaseQuery;\n");

		builder.append("import java.util.ArrayList;\n");
		builder.append("import java.util.Arrays;\n");
		builder.append("import java.util.List;\n");

		
		List<String> imported = new ArrayList<String>();
		for (Field data : fields) {
			if(data.isCondition()){
				Type type = data.getType();
				if(!type.isSimpleType()){
					ComplexType complex = (ComplexType)type;
					if(!imported.contains(complex.getRefClass())){
						builder.append("import ").append(complex.getRefClass()).append(";\n");
						imported.add(complex.getRefClass());
					}
				}
			}
		}
		
		builder.append(" \n/**\n * ").append(alias).append("查询条件\n").append(" */ \n");
		builder.append("public class ").append(entry).append("Query extends  BaseQuery ").append("{ \n\n");

		builder.append("	private List<Condition> base;\n\n");

		builder.append("	public ").append(entry).append("Query(){\n");
		builder.append("		this.base = new ArrayList<>();\n");
		builder.append("		ConditionGroup group = new ConditionGroup();\n");
		builder.append("		group.setConditions(base);\n");
		builder.append("		ConditionArea area = new ConditionArea();\n");
		builder.append("		area.setGroups(Arrays.asList(group));\n");
		builder.append("		this.areas =Arrays.asList(area);\n");
		builder.append("	}\n\n");
	
		if(tree){
			String classname = basepackage+"."+ module + ".entity."+ entry;
			builder.append(VOAttributeUtils.createPOJOField("parent", classname, "上级实体"));
		}
		
		for (Field data : fields) {
			if(data.isCondition()){
				String uppder = CamelCaseUtils.toFristUpper(data.getName());
				Type type = data.getType();
				builder.append("	public ").append(entry).append("Query set").append(uppder).append("(").append(data.getType().getJavaType()).append(" ").append(data.getName()).append("){\n");
				builder.append("		Condition condition = new Condition(\"").append(data.getColumn()).append("\",Comparison.EQUAL,").append(data.getName()).append(");\n");
				builder.append("		this.base.add(condition);\n");
				builder.append("		return this;\n");
				builder.append("	}\n\n");
				if(data.getType() == SimpleTypeEnum.DATETIME || data.getType() == SimpleTypeEnum.DATE|| data.getType() == SimpleTypeEnum.TIMESTAMP){
					builder.append("	public ").append(entry).append("Query set").append(uppder).append("Start(").append(data.getType().getJavaType()).append(" ").append(data.getName()).append("){\n");
					builder.append("		Condition condition = new Condition(\"").append(data.getColumn()).append("\",Comparison.START,").append(data.getName()).append(");\n");
					builder.append("		this.base.add(condition);\n");
					builder.append("		return this;\n");
					builder.append("	}\n\n");

					builder.append("	public ").append(entry).append("Query set").append(uppder).append("End(").append(data.getType().getJavaType()).append(" ").append(data.getName()).append("){\n");
					builder.append("		Condition condition = new Condition(\"").append(data.getColumn()).append("\",Comparison.END,").append(data.getName()).append(");\n");
					builder.append("		this.base.add(condition);\n");
					builder.append("		return this;\n");
					builder.append("	}\n\n");
				}



			}
		}
		
		builder.append("\n\n");
		
		for (Field data : fields) {
			if(data.isSortable()){
				String uppder = CamelCaseUtils.toFristUpper(data.getName());
				builder.append("	public ").append(entry).append("Query orderBY").append(uppder).append("(boolean isAsc){\n");
				builder.append("		orderFields.add(new OrderField(\"").append(data.getColumn()).append("\", isAsc ? SortDirection.ASC : SortDirection.DESC));\n");
				builder.append("		return this;\n");
				builder.append("	}\n\n");
			}
		}
		
		builder.append("}");
		return builder.toString();
	}


	private String createEntry(List<Field> fields, String alias, String module, String entry,String basepackage,boolean isTree,boolean isLevel,boolean isAudit) {
		StringBuilder builder = new StringBuilder();
		String packages = basepackage+"."+ module + ".pojo" ;
		builder.append("package ").append(packages).append(";\n\n\n");
		String extend;

		builder.append("import com.alibaba.fastjson.annotation.JSONField;\n");
		builder.append("import com.alibaba.fastjson.annotation.JSONType;\n");
		builder.append("import com.raycloud.yc.eco.ding.app.com.deploy.business.deploy.pojo.BusinessDeployBasePojo;\n");
		extend = " extends BusinessDeployBasePojo" ;

		List<String> imported = new ArrayList<String>();
		for (Field data : fields) {
			Type type = data.getType();
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(!imported.contains(complex.getRefClass())){
					builder.append("import ").append(complex.getRefClass()).append(";\n");
					System.out.println("实体" +entry +"需导入类：" + complex.getRefClass() + ",请确认其是否存在 , 请确认其表名称是否为:" +complex.getRefTable() );
					imported.add(complex.getRefClass());
				}
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE && !imported.contains("java.util.List")){
			    	builder.append("import java.util.List;\n");
			    	imported.add("java.util.List");
			    }
			}
		}
		builder.append(" \n/**\n * ").append(alias).append("\n").append(" */ \n");
		builder.append("@JSONType(ignores = {\"splitTableName\", \"splitDBName\", \"tableId\", \"extendJson\", \"extend_json\"})\n");
		builder.append("public class ").append(entry).append(extend).append("{ \n\n");
		builder.append("	private static final long serialVersionUID = 1L;\n\n");
		
		
		for (Field data : fields) {
			String field = data.getName();

			Type type = data.getType();
			String javatype = data.getType().getJavaType();
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					javatype = "List<" +type.getJavaType() + ">";
			    }
			}
			if(data.isTransient()){
				builder.append(VOAttributeUtils.createPOJOField(field, javatype, data.getAlias(),"transient"));
			}else{
				builder.append(VOAttributeUtils.createPOJOField(field, javatype, data.getAlias()));
			}
			
		}
		builder.append("\n");
		
		for (Field data : fields) {
			String field = data.getName();

			Type type = data.getType();
			String fieldtype = type.getJavaType();
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					fieldtype = "List<" +type.getJavaType() + ">";
				}else{
					String methodName = CamelCaseUtils.toFristUpper(field);
					builder.append("	/** \n	 * 设置" + data.getAlias() + " \n	 * \n	 * @param id " + alias + "ID值\n	 */ \n");
					builder.append("	public void set" + methodName + "ByID(Long id) { \n	");
					builder.append("  "+type.getJavaType()  + " value = new "+type.getJavaType()+"(); \n");
					builder.append("	  value.setId(id); \n");
					builder.append("	  this." + field + " = value; \n");
					builder.append("	} \n");
					
				}
			}
			
			builder.append(VOAttributeUtils.createGetterAndSetter(field, fieldtype, data.getAlias()));
		}

		

		builder.append("}");
		return builder.toString();
	}
	
	
	private String createEnum(Field field,String enumName, String module,String basepackage ){
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(basepackage).append("." ).append(module).append(".pojo" ).append(";\n\n");
		
		builder.append(" /**\n * ").append(field.getAlias()).append("枚举\n").append(" */ \n");
		builder.append("  public enum ").append(enumName).append(" { \n\n ");
		
		EnumType type = (EnumType)field.getType();
		
		String[] enumNames = type.getEnumNames();
		String[] enumAlias = type.getAlias();
		String[] enumValues = type.getValues();
		
		for(int i = 0; i < enumNames.length; i++){
			builder.append("		/** ").append(enumValues[i]).append(":").append(enumAlias[i]).append("*/ \n");
			builder.append("		").append(enumNames[i]).append("(").append(enumValues[i]).append(",\"").append(enumAlias[i]).append("\"),\n");
		}
		builder.delete(builder.length() - 2, builder.length());
		builder.append("; \n\n");
		builder.append("		private int value;\n");
		builder.append("		private String name;\n\n");
		builder.append("		public int getValue() {\n			return value;\n		}\n\n");
		builder.append("		public String getName() {\n			return name;\n		}\n\n");
		builder.append("		private ").append(enumName).append("(int value, String name) {\n			this.value = value;\n			this.name = name;\n		}\n\n");
      
		builder.append("		public static ").append(enumName).append(" get").append(CamelCaseUtils.toFristUpper(field.getName())).append("ByInt(int value){\n");
		builder.append("		   ").append(enumName).append("[]  values = ").append(enumName).append(".values();\n");
		builder.append("		   for(").append(enumName).append(" enumValue : values){\n");
		builder.append("				if(enumValue.getValue() == value){\n					return enumValue;\n				}\n");
		builder.append("			}\n			return null;\n		}\n\n");
		
		builder.append("  }");
		
		return  builder.toString();
	}
	
	private String createView(List<Field> fields,  String alias, String module, String entry,String basepackage,boolean isTree,boolean isAudit) {
		List<Field> images = new ArrayList<Field>();
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(basepackage).append("." ).append(module).append(".view" ).append(";\n\n\n");
		builder.append("import java.io.Serializable;\n");
		if(isTree){
			builder.append("import java.util.ArrayList;\nimport java.util.List;\n");
		}
		if(isAudit){
			builder.append("import ").append(basepackage).append(".base.entity.AuditStatusEnum;\n");
		}
		builder.append("import ").append(basepackage).append(".base.view.View;\n");
		for (Field data : fields) {
			if (data.getType() instanceof EnumType) {
				EnumType enumtype = (EnumType) data.getType();
				if (StringUtils.isNotBlank(enumtype.getClassName())) {
					builder.append("import ").append(enumtype.getClassName()).append(";\n");
				}else{
					String enumName = data.getName().substring(0, 1).toUpperCase() + data.getName().substring(1) + "Enum";
					builder.append("import ").append(basepackage).append(".").append(module).append(".entity.").append(enumName).append(";\n");
				}
			}
		}
		builder.append("import ").append(basepackage).append(".").append(module).append(".entity.").append(entry).append(";\n");

		builder.append("\n");
		
		builder.append(" \n/**\n * ").append(alias).append("视图对象\n").append(" */ \n");
		builder.append("public class ").append(entry).append("View extends View implements Serializable{\n\n");
		builder.append("private static final long serialVersionUID = 1L;\n\n");
		builder.append("  public ").append(entry).append("View(").append(entry).append(" ").append(entry.toLowerCase()).append(") { \n");
		builder.append("	super(").append(entry.toLowerCase()).append(");\n");
		
		if(isAudit){
			builder.append("    if(").append(entry.toLowerCase()).append(".getAuditStatus() != null){\n");
			builder.append("        super.put(\"auditStatusStr\",AuditStatusEnum.getAuditStatusByInt(viper.getAuditStatus()).getName());\n");
			builder.append("    }\n\n");
		}
		for (Field data : fields) {
			String methodName = data.getName().substring(0, 1).toUpperCase() + data.getName().substring(1);
			if(data instanceof ImageField){
				images.add(data);
			}else if(data.getType() == SimpleTypeEnum.BOOLEAN){
				String str = data.getName()+"Str";
				builder.append("    super.put(\"").append(str).append("\", ").append(entry.toLowerCase()).append(".get").append(methodName).append("()== 1 ? \"是\":\"否\");\n");
			}else if(data.getType()  instanceof EnumType){
				EnumType type = (EnumType)data.getType();
				String enumName = null;
				if(StringUtils.isNotBlank(type.getClassName())){
					 enumName = type.getClassName().substring(type.getClassName().lastIndexOf(".") + 1);
				}else{
					 enumName = data.getName().substring(0, 1).toUpperCase() + data.getName().substring(1) + "Enum";
				}
				
				String str = data.getName()+"Str";
				builder.append("    if(").append(entry.toLowerCase()).append(".get").append(methodName).append("() != null){\n");
				builder.append("        super.put(\"").append(str).append("\", ").append(enumName).append(".get").append(CamelCaseUtils.toFristUpper(data.getName())).append("ByInt(")
                .append(entry.toLowerCase()).append(".get").append(methodName).append("())").append(".getName());\n    }\n");
			}else if(data.getType().getLength() >= 128){
				builder.append("    super.putShortString(\"").append(data.getName()).append("\");\n");
			}else if(data.getType()  instanceof ComplexType){
				ComplexType complex = (ComplexType)data.getType();
				if(complex.getRelationship() == ComplexType.ONE_TO_ONE){
					 builder.append("    if(").append(entry.toLowerCase()).append(".get").append(methodName).append("() != null){\n")
					.append("		super.put(\"").append(data.getName()).append("Str\", ").append(entry.toLowerCase()).append(".get").append(methodName).append("().getShowName());\n")
					 .append("    }\n");
				}else{
					builder.append("    if(").append(entry.toLowerCase()).append(".get").append(methodName).append("() != null && ").append(entry.toLowerCase()).append(".get").append(methodName).append("().size() > 0){\n")
					.append("		StringBuilder ids = new StringBuilder();\n")
					.append("		StringBuilder names = new StringBuilder();\n")
					.append("		for (").append(complex.getRefClass()).append(" ").append(data.getName()).append(" : ").append(entry.toLowerCase()).append(".get").append(methodName).append("()){\n")
					.append("			names.append(").append(data.getName()).append(".getShowName()).append(\",\");\n")
					.append("			ids.append(").append(data.getName()).append(".getId()).append(\",\");\n")
					.append("		}\n")
					.append("		names.deleteCharAt(names.length() - 1);\n")
					.append("		ids.deleteCharAt(ids.length() - 1);\n")
					.append("		super.put(\"").append(data.getName()).append("Str\", names.toString());\n")
					.append("		super.put(\"").append(data.getName()).append("Ids\", ids.toString());\n")
					.append("    }\n");
				}
				
			}

		}
		
		builder.append("  }\n\n");
		
		if(isTree){
			builder.append("  private List<").append(entry).append("View> children = new ArrayList<").append(entry).append("View>();\n\n");
			builder.append("  public void addChild(").append(entry).append("View view){\n    children.add(view);\n  }\n\n");
			builder.append("  public void addChildren( List<").append(entry).append("View> views){\n    if(children == null){\n      children = views;\n")
					.append("    }else{\n      children.addAll(views);\n    }\n  }\n\n");
			builder.append("  public List<").append(entry).append("View> getChildren(){\n    return children;\n  }\n\n");
		}
		
		
		if(images.size() > 0){
			for (Field data : images) {
				ImageField img = (ImageField)data;
				if(img.isMultiple()){
					String field = data.getName() + "Urls";
					builder.append("  /** \n  * 设置").append(data.getAlias()).append("地址\n  *\n  */\n")
					.append("  public void set").append(CamelCaseUtils.toFristUpper(field)).append("(String[] values) {\n	 ")
					.append("  super.put(\"").append(field).append("\",values);\n").append("  }\n\n");
				}else{
					String field = data.getName() + "Url";
					builder.append("  /** \n  * 设置").append(data.getAlias()).append("地址\n  *\n  */\n")
					.append("  public void set").append(CamelCaseUtils.toFristUpper(field)).append("(String value) {\n	 ")
					.append("  super.put(\"").append(field).append("\",value);\n").append("  }\n\n");
				}
			}
	    }
		
		builder.append("}");
		return builder.toString();
	}


	public void clear(Context context) {
		String entry = context.getEntity();
		String module = context.getModule().toLowerCase();
		String basepath = context.getJavaBasepath() + File.separator + module + File.separator ;
		String saveToFile = basepath + "entity" + File.separator + entry + ".java";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
		
		List<Field> fields = context.getFields();
		for (Field data : fields) {
			if(data.getType()  instanceof EnumType){
				String enumName = data.getName().substring(0, 1).toUpperCase() + data.getName().substring(1) + "Enum";
				saveToFile = basepath + "entity" + enumName + ".java";
				file = new File(saveToFile);
				if (file.exists()) {
					file.delete();
				}
			}
		}
		
		saveToFile = basepath + "view" + File.separator + entry + "View.java";
		file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
		
		saveToFile = basepath + "service" + File.separator + entry + "Condition.java";
		file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}

}
