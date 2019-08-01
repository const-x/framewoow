package idv.const_x.tools.coder.init;


import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import idv.const_x.tools.coder.VOAttributeUtils;
import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.file.search.FileLineReader;
import idv.const_x.file.search.FileLineRepalceHandler;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class ModuleGeneratorUtils {

	public static void main(String args[]) throws Exception {
		//初始化工程
//		init();
		//创建模块
		create();
		//创建对VO的调用
		//setterInvoke();
	}

	private static void init() {
		try{
			String basePath = "E:\\workspace\\demo\\";
			String baseContent = "idv.constx.demo";
			String baseRepalce = "com.company.project";
			
			String content = "";
			String repalce = "";
			FileFilter JAVA = new FileNameExtensionFilter("JAVA", "java");
			FileFilter JSP = new FileNameExtensionFilter("JSP", "jsp");
			FileFilter ALL = null;
			
			FileLineReader reader = new FileLineReader();
			FileLineRepalceHandler handler = new FileLineRepalceHandler();
			reader.setHandler(handler);
			
			content = "package "+baseContent;
			repalce = "package "+baseRepalce;
			handler.setReplace(repalce);
			handler.setContent(content);
			reader.findFiles(basePath + "src\\main\\java", JAVA);
			
			content = "import "+baseContent;
			repalce = "import "+baseRepalce;
			handler.setReplace(repalce);
			handler.setContent(content);
			reader.findFiles(basePath + "src\\main\\java", JAVA);
			
			handler.setReplace(baseRepalce);
			handler.setContent(baseContent);
			reader.findFiles(basePath + "src\\main\\resources", ALL);
			
			handler.setReplace(baseRepalce);
			handler.setContent(baseContent);
			reader.findFiles(basePath + "src\\main\\webapp\\WEB-INF\\views", JSP);
			System.out.println("项目初始化成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	private static void create() throws Exception {
		Context context = new Context();

		// 指定 所属模块、组件，以及对应实体
		context.setEntity("module");
		context.setEntityAlias("业务模块");
		context.setComponent("module");
		context.setComponentAlias("模块管理");
		context.setModule("security");
		context.setModuleAlias("系统设置");

		context.addFields(getFields());
		
		// 是否支持数据等级
		context.setLevel(false);
		// 是否支持审批
		context.setNeedAudit(false);
		// 是否支持逻辑删
		context.setLogicDel(false);
		// 是否系统级数据（只能由系统级用户维护的数据）
		context.setSystemData(true);
		// 是否树结构类型
		context.setTree(true);

		// 需创建的文件
		context.genEntityCodes(true);// 实体文件和view文件
		context.genMapperAndDao(false);// mapper映射文件和dao
		context.genOtherCodes(false);// controller和service
		context.genSqls(false);// 建库脚本，模块注册脚本
		context.genViews(false);// jsp页面

		context.setBasepackage("idv.constx.demo");
		context.setAuther("const.x");
		context.setDbType(DBType.ORACLE);
		context.setBasepath("E:/workspace/demo/src/main");
		context.setSqlBasepath("E:/workspace/demo/doc/sql");
		context.setLogBasepath("F:/demo/log");
		CodeGenerator.generator(context);
	}
	
	private static void setterInvoke() throws Exception {
		List<Field> list = getFields();
		for (Field field : list) {
			System.out.print(VOAttributeUtils.createSetterInvoke("entity", field.getName(), field.getType().getJavaType(), field.getAlias()));
		}
	}
	
	private static List<Field> getFields() throws Exception {
		List<Field> list = new ArrayList<Field>();

		
		Field field = new Field("name", SimpleTypeEnum.STRING, "模块名称");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setUnique(true);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("url", SimpleTypeEnum.STRING, "入口地址");
		list.add(field);
		
		field = new Field("sn", SimpleTypeEnum.STRING, "标志符，用于授权名称（类似module:save）");
		list.add(field);
		
		
		field = new Field("description", SimpleTypeEnum.STRING_256, "模块描述");
		list.add(field);
		
		
		field = new Field("priority", SimpleTypeEnum.INTEGER, "模块的排序号");
		list.add(field);
		
		field = new Field("enable", SimpleTypeEnum.BOOLEAN, "是否启用");
		list.add(field);
		
		
		
		
		// * 枚举字段
		String[] enumNames = { "ROOT", "OPERATION", "BUSINESS" };
		String[] alias = { "根节点", "业务操作", "业务模块" };
		String[] values = { "0", "2", "1" };
		EnumType type = new EnumType(enumNames, alias, values);
		type.setClassName("idv.constx.demo.security.entity.ModuleTypeEnum");
		field = new Field("type", type, "模块类型");
		field.setSortable(true);
		field.setExport(true); 
		list.add(field);

		
//		
//		ComplexType complex = new ComplexType("idv.constx.demo.security.entity.Module");
//		complex.setRelationship(ComplexType.ONE_TO_ONE);
//		complex.setRefIDField("id");
//		complex.setRefNameField("realname");
//		complex.setRefIDColumn("ID");
//		complex.setRefNameColumn("NAME");
//		complex.setRefTable("SECURITY_MODULE");
//		field = new Field("parent", complex, "所属模块");
//		field.setIsCondition(true);
//		field.setNullable(false);
//		field.setSortable(true);
//		list.add(field);
		
		return list;
	}
	


}
