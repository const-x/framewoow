package idv.const_x.tools.coder.init;


import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import idv.const_x.tools.coder.VOAttributeUtils;
import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.Range;
import idv.const_x.file.search.FileLineReader;
import idv.const_x.file.search.FileLineRepalceHandler;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class MetaGeneratorUtils {

	public static void main(String args[]) throws Exception {

		create();
	
	}


	
	

	private static void create() throws Exception {
		Context context = new Context();

		// 指定 所属模块、组件，以及对应实体
		context.setEntity("MetaEntity");
		context.setEntityAlias("实体");
		context.setComponent("metadata");
		context.setComponentAlias("元数据管理");
		context.setModule("security");
		context.setModuleAlias("系统设置");

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
		context.genMapperAndDao(true);// mapper映射文件和dao
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
	
	
	private static List<Field> getFields() throws Exception {
		List<Field> list = new ArrayList<Field>();

		
		Field field = new Field("name", SimpleTypeEnum.STRING, "元数据名称");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setUnique(true);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("alias", SimpleTypeEnum.STRING, "元数据别名");
		field.setIsCondition(true);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("tablename", SimpleTypeEnum.STRING, "表名称");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setUnique(true);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("classname", SimpleTypeEnum.STRING_64, "实体类名");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setUnique(true);
		field.setSortable(true);
		list.add(field);
		
		
		field = new Field("refmodule", SimpleTypeEnum.STRING, "所属模块");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("modulealias", SimpleTypeEnum.STRING, "所属模块别名");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setSortable(true);
		list.add(field);
		
		
		return list;
	}
	


}
