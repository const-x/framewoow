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

public class ConfigGeneratorUtils {

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
		context.setEntity("config");
		context.setEntityAlias("系统参数");
		context.setComponent("configs");
		context.setComponentAlias("系统参数");
		context.setModule("security");
		context.setModuleAlias("系统管理");

		context.addFields(getFields());
		
		// 是否支持数据等级
		context.setLevel(false);
		// 是否支持审批
		context.setNeedAudit(false);
		// 是否支持逻辑删
		context.setLogicDel(true);
		// 是否系统级数据（只能由系统级用户维护的数据）
		context.setSystemData(true);

		// 需创建的文件
		context.genEntityCodes(false);// 实体文件和view文件
		context.genMapperAndDao(false);// mapper映射文件和dao
		context.genOtherCodes(false);// controller和service
		context.genSqls(true);// 建库脚本，模块注册脚本
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

		Field field = new Field("code", SimpleTypeEnum.STRING, "参数编码");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setUnique(true);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("name", SimpleTypeEnum.STRING, "参数名称");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setUnique(true);
		field.setSortable(true);
		list.add(field);
		
		// * 枚举字段
		String[] enumNames = { "INT", "DOUBLE", "STRING" , "DATE"};
		String[] alias = { "整数", "小数", "字符", "日期" };
		String[] values = { "0", "1", "2" , "3"};
		field = new Field("type", new EnumType(enumNames, alias, values), "参数类型");
		field.setSortable(true);
		field.setDefaultValue(2);
		list.add(field);

		field = new Field("description", SimpleTypeEnum.STRING_256, "参数说明");
		list.add(field);

		field = new Field("optional", SimpleTypeEnum.STRING_256, "可选值");
		list.add(field);
		
		field = new Field("maxval", SimpleTypeEnum.DOUBLE, "最大值");
		list.add(field);
		
		field = new Field("minval", SimpleTypeEnum.DOUBLE, "最小值");
		list.add(field);
		
		field = new Field("val", SimpleTypeEnum.STRING_256, "参数值");
		list.add(field);

		return list;
	}
	


}
