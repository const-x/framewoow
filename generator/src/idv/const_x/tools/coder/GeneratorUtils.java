package idv.const_x.tools.coder;


import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.Range;
import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class GeneratorUtils {

	public static void main(String args[]) throws Exception {
		//创建模块
		create();
		//创建对VO的调用
		//setterInvoke();
	}

	
	private static void create() throws Exception {
		Context context = new Context();

		// 指定 所属模块、组件，以及对应实体
		context.setEntity("viper");
		context.setEntityAlias("VIP会员");
		context.setComponent("member");
		context.setComponentAlias("会员");
		context.setModule("memberMng");
		context.setModuleAlias("会员管理");
		//自定义表名 如果不设置的话 默认按  模块_实体 生成表名
//		context.setTableName("member_viper");
		//表名前缀
		context.setTableProfix("oms");

		context.addFields(getFields());
		
		// 是否支持数据等级
		context.setLevel(false);
		// 是否支持审批
		context.setNeedAudit(true);
		// 是否支持逻辑删
		context.setLogicDel(true);
		// 是否系统级数据（只能由系统级用户维护的数据）
		context.setSystemData(false);
		// 是否树结构类型
		context.setTree(false);

		// 需创建的文件
		context.genSqls(true);// 建库脚本，模块注册脚本
		context.genEntityCodes(true);// 实体文件和view文件
		context.genMapperAndDao(true);// mapper映射文件和dao
		context.genOtherCodes(true);// controller和service
		context.genViews(true);// jsp页面

		context.setBasepackage("idv.constx.demo");
		context.setAuther("const.x");
		context.setDbType(DBType.ORACLE);
		context.setBasepath("E:/workspace/demo/src/main");
		context.setSqlBasepath("E:/workspace/demo/doc/sql");
		context.setLogBasepath("E:/workspace/demo/log");
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
		// 字段示例
				// * 一般字段
				Field field = new Field("card", SimpleTypeEnum.STRING, "会员卡号");
				field.setDefaultValue("12312312"); // 默认值
				field.setDisplay(true); // 是否在界面展示 默认为true
				field.setEditable(true); // 是否界面可编辑 默认为true
				field.setNullable(false); // 是否可为空 默认为 true
				field.setUnique(true); // 是否需校验唯一性 默认为false
				field.setIsCondition(true); // 是否作为界面查询条件 默认为 false
				field.setSortable(true);// 是否支持界面点击排序 默认为 false
				field.setExport(true);     // 是否可导出, 默认为 false
				field.setTransient(false); // 是否不可序列化,用于支持序列化缓存, 默认为 false
				field.setColumn("cardNo");// 自定义表字段名  默认表字段名与类属性名一致
				list.add(field);
//
				field = new Field("isvip", SimpleTypeEnum.BOOLEAN, "是否会员");
				field.setIsCondition(true);
				field.setExport(true);    
				field.setNullable(false);
				list.add(field);

				field = new Field("age", SimpleTypeEnum.INTEGER, "年龄");
				//添加自定义的校验规则
				AbsValidateStyles validate = new Range(0,120);
//				validate.setMessage("年龄必需在{0}到{1}之间");
				field.addValidate(validate);
				field.setExport(true); 
				list.add(field);

				field = new Field("brith", SimpleTypeEnum.DATE, "生日");
				field.setExport(true); 
				field.setNullable(false);
				list.add(field);

				field = new Field("joinTime", SimpleTypeEnum.DATETIME, "加入时间");
				field.setIsCondition(true);
				field.setExport(true); 
				field.setNullable(false);
				list.add(field);

				field = new Field("integral", SimpleTypeEnum.FLOAT, "积分");
				field.setExport(true); 
				list.add(field);

				// * 自定义类型的字段（方式1）
				SimpleType type = new SimpleType();
				type.setJavaType("String");
				type.setJdbcType("VARCHAR");
				type.setLength(64);
				field = new Field("name", type, "姓名");
				field.setIsCondition(true);
				field.setUnique(true);
				field.setNullable(false);
				field.setSortable(true);
				field.setExport(true); 
				list.add(field);

				// * 自定义类型的字段（方式2）
				SimpleType type2 = SimpleType.clone(SimpleTypeEnum.STRING_256);
				type2.setLength(300);
				field = new Field("description", type2, "描述");
				field.setExport(true); 
				field.setNullable(false);
				list.add(field);

				// * 枚举字段
				String[] enumNames = { "BLACK", "WRITE", "RED" };
				String[] alias = { "黑卡", "白金卡", "东方红卡" };
				String[] values = { "0", "1", "2" };
				field = new Field("lvl", new EnumType(enumNames, alias, values), "等级");
				field.setSortable(true);
				field.setIsCondition(true);
				field.setExport(true); 
				field.setNullable(false);
				list.add(field);
				
				
				// * 所属组织
				field = new Field("organization", new ComplexType(
						"idv.constx.demo.security.entity.Organization"), "所属组织");
				field.setIsCondition(true);
				field.setSortable(true);
				field.setExport(true); 
				list.add(field);


				ImageField image ;
				// * 图片上传
				image = new ImageField("photo", "照片");
				//指定长宽及大小（KB）
				image.setMaxSize(500);
				image.setHeight(200);
				image.setWidth(300);
				list.add(image);

				image = new ImageField("pictures", "相册");
				//指定最大长宽
				image.setMaxSize(500);
				image.setMaxWidth(140);
				image.setMaxHeight(140);
				image.setMultiple(true); // 是否支持多张图片上传 默认为false
				list.add(image);

				
				
				// * 业务组织范围
				ComplexType complex = new ComplexType("idv.constx.demo.security.entity.Organization");
				complex.setRelationship(ComplexType.MANY_TO_MANY);
				//可指定參照內容的ID 名称 的属性名及相应数据库列名，指定对应表名，用于兼容非标准数据
				complex.setRefIDField("id");
				complex.setRefNameField("name");
				complex.setRefIDColumn("ID");
				complex.setRefNameColumn("name");
				complex.setRefTable("security_Organization");
				field = new Field("operation", complex, "业务组织范围");
				list.add(field);
				
				// * 图文详情
				RichTextField richText = new RichTextField("detials", "图文详情", 140, 140,20);
				richText.setTools("Fontface,FontSize,|,Bold,Italic,Underline,Strikethrough,|,FontColor,BackColor,|,Img");// 可选的界面控件
				richText.setTransient(true); //不可序列化
				richText.setNullable(false);
				list.add(richText);
				

		return list;
	}
	


}
