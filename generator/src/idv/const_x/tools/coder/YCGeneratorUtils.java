package idv.const_x.tools.coder;


import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.Range;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YCGeneratorUtils {

	public static void main(String args[]) throws Exception {
		//创建模块
		create();
	}

	
	private static void create() throws Exception {
		Context context = new Context();

		// 指定 所属模块、组件，以及对应实体
		context.setEntity("TemplateFlowNotifyBase");
		context.setEntityAlias("流程提醒设置");
//		context.setComponent("member");
//		context.setComponentAlias("会员");
		context.setModule("template.flow");
		context.setModuleAlias("单据流程提醒");
		//自定义表名 如果不设置的话 默认按  模块_实体 生成表名
//		context.setTableName("member_viper");
		//表名前缀
//		context.setTableProfix("oms");

		context.addFields(getFields());
		
		// 是否支持数据等级
		context.setLevel(false);
		// 是否支持审批
		context.setNeedAudit(false);
		// 是否支持逻辑删
		context.setLogicDel(false);
		// 是否系统级数据（只能由系统级用户维护的数据）
		context.setSystemData(false);
		// 是否树结构类型
		context.setTree(false);

		// 需创建的文件
		context.genSqls(true);// 建库脚本，模块注册脚本
		context.genEntityCodes(true);// 实体文件和view文件
		context.genMapperAndDao(true);// mapper映射文件和dao
		context.genOtherCodes(true);// controller和service
		context.genViews(false);// jsp页面

		context.setBasepackage("com.raycloud.yc.eco.ding.app");
		context.setAuther("const.x");
		context.setDbType(DBType.MYSQL);
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com=fsv.getHomeDirectory();
		String base = com.getPath();
		context.setBasepath(base +"/code/src/main");
		context.setSqlBasepath(base +"/code/sql");
		CodeGenerator.generator(context);
	}

	
	
	private static List<Field> getFields() throws Exception{
		List<Field> list = new ArrayList<Field>();
		// 字段示例
				Field field;
//
				field = new Field("flowId", SimpleTypeEnum.LONG, "流程Id");
				field.setIsCondition(true);
				field.setNullable(false);
				list.add(field);

		field = new Field("flowVersion", SimpleTypeEnum.STRING, "流程版本");
		field.setIsCondition(true);
		field.setNullable(false);
		list.add(field);

		field = new Field("nodeCode", SimpleTypeEnum.STRING, "流程节点");
		field.setIsCondition(true);
		field.setNullable(true);
		list.add(field);

		// * 枚举字段
		String[] enumNames = { "NODE_TIME", "TEMPLATE_FIELD" };
		String[] alias = { "节点到达时间", "表单字段时间" };
		String[] values = { "1", "2" };
		field = new Field("BaseTimeType", new EnumType(enumNames, alias, values), "超时时间基准");
		field.setSortable(true);
		field.setIsCondition(true);
		field.setNullable(false);
		list.add(field);

		field = new Field("baseTimeField", SimpleTypeEnum.STRING, "取值字段");
		field.setNullable(true);
		list.add(field);

		field = new Field("timeValue", SimpleTypeEnum.INTEGER, "时间偏移量");
		field.setNullable(false);
		list.add(field);


		// * 枚举字段
		String[] enumNames2 = { "DAT", "HOUR" };
		String[] alias2 = { "天", "小时" };
		String[] values2 = { "1", "2" };
		field = new Field("timeUnit", new EnumType(enumNames2, alias2, values2), "时间单位");
		field.setIsCondition(true);
		field.setNullable(false);
		list.add(field);
				

		return list;
	}
	


}
