package idv.const_x.tools.coder.init;


import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.VOAttributeUtils;
import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.Range;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class DocGeneratorUtils {

	public static void main(String args[]) throws Exception {
		//创建模块
		create();
		//创建对VO的调用
		//setterInvoke();
	}

	
	private static void create() throws Exception {
		Context context = new Context();

		// 指定 所属模块、组件，以及对应实体
		context.setEntity("doc");
		context.setEntityAlias("文件档案");
		context.setComponent("upload");
		context.setComponentAlias("上传组件");
		context.setModule("base");
		context.setModuleAlias("基础服务");
		//自定义表名 如果不设置的话 默认按  模块_实体 生成表名
//		context.setTableName("member_viper");

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
		context.genEntityCodes(false);// 实体文件和view文件
		context.genMapperAndDao(false);// mapper映射文件和dao
		context.genOtherCodes(false);// controller和service
		context.genViews(false);// jsp页面

		context.setBasepackage("idv.constx.demo");
		context.setAuther("const.x");
		context.setDbType(DBType.ORACLE);
		context.setBasepath("E:/workspace/demo_easyui/src/main");
		context.setSqlBasepath("E:/workspace/demo_easyui/doc/sql");
		context.setLogBasepath("F:/demo_easyui/log");
		CodeGenerator.generator(context);
	}
	
	private static void setterInvoke() throws Exception {
		List<Field> list = getFields();
		for (Field field : list) {
			System.out.print(VOAttributeUtils.createSetterInvoke("entity", field.getName(), field.getType().getJavaType(), field.getAlias()));
		}
	}
	
	
	private static List<Field> getFields() throws Exception{
		List<Field> list = new ArrayList<Field>();
		Field field = null;
		
		field = new Field("name", SimpleTypeEnum.STRING, "文件名称");
		field.setNullable(false);
		list.add(field);
		
		field = new Field("suffix", SimpleTypeEnum.STRING, "文件类型");
		field.setNullable(false);
		list.add(field);
		
		field = new Field("path", SimpleTypeEnum.STRING_64, "存储路径（相对）");
		list.add(field);
		
		field = new Field("MD5", SimpleTypeEnum.STRING_32, "MD5码");
		list.add(field);
		
		field = new Field("expiration", SimpleTypeEnum.DATETIME, "(缓存文件)过期时间");
		list.add(field);
		
		field = new Field("cacheKey", SimpleTypeEnum.STRING_64, "(缓存文件)key值");
		list.add(field);
		
		
				

		return list;
	}
	


}
