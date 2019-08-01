package idv.const_x.tools.coder.init;


import java.util.ArrayList;
import java.util.List;


import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class MetaColumnGeneratorUtils {

	public static void main(String args[]) throws Exception {
		//创建模块
		create();
		//创建对VO的调用
		//setterInvoke();
	}


	
	

	private static void create() throws Exception {
		Context context = new Context();

		// 指定 所属模块、组件，以及对应实体
		context.setEntity("MetaField");
		context.setEntityAlias("元字段");
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
		context.setDbType(DBType.MYSQL);
		context.setBasepath("E:/workspace/demo/src/main");
		context.setSqlBasepath("E:/workspace/demo/doc/sql");
		context.setLogBasepath("F:/demo/log");
		CodeGenerator.generator(context);
	}
	
	
	
	private static List<Field> getFields() throws Exception {
		List<Field> list = new ArrayList<Field>();

		
		Field field = new Field("name", SimpleTypeEnum.STRING, "字段名称");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setSortable(true);
		list.add(field);
		
		field = new Field("description", SimpleTypeEnum.STRING_32, "字段说明");
		list.add(field);
		
		field = new Field("columnname", SimpleTypeEnum.STRING, "表列名");
		field.setIsCondition(true);
		field.setNullable(false);
		list.add(field);
		
		field = new Field("javatype", SimpleTypeEnum.STRING, "JAVA类型");
		list.add(field);
		
		
		field = new Field("maxlength", SimpleTypeEnum.INTEGER, "最大长度");
		list.add(field);
		
		field = new Field("scale", SimpleTypeEnum.INTEGER, "小数点位数");
		field.setDefaultValue(0);
		list.add(field);
		
		field = new Field("nullable", SimpleTypeEnum.BOOLEAN, "是否可为空");
		list.add(field);
		
		field = new Field("uniqueness", SimpleTypeEnum.BOOLEAN, "是否唯一");
		list.add(field);
		
		field = new Field("editable", SimpleTypeEnum.BOOLEAN, "是否可编辑");
		list.add(field);
		
		field = new Field("defvalue", SimpleTypeEnum.STRING_32, "默认值");
		list.add(field);
		
		field = new Field("refclass", SimpleTypeEnum.STRING_64, "参照类名");
		list.add(field);
		
		field = new Field("refEnum", SimpleTypeEnum.STRING_64, "参照枚举类名");
		list.add(field);
		
		field = new Field("relationship", SimpleTypeEnum.STRING, "关联关系");
		list.add(field);
	
		
		
		ComplexType complex = new ComplexType("idv.constx.demo.security.entity.MetaEntity");
		complex.setRelationship(ComplexType.ONE_TO_ONE);
		
		field = new Field("entity", complex, "所属实体");
		field.setIsCondition(true);
		field.setNullable(false);
		field.setSortable(true);
		list.add(field);
		
		return list;
	}
	


}
