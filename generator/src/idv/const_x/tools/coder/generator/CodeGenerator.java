package idv.const_x.tools.coder.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import idv.const_x.utils.FileUtils;
import idv.const_x.tools.coder.generator.java.JavaGenerator;
import idv.const_x.tools.coder.generator.mapper.MapperGenerator;
import idv.const_x.tools.coder.generator.others.Log4jGenerator;
import idv.const_x.tools.coder.generator.sql.SqlGenerator;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.StaticValidateFactory;
import idv.const_x.tools.coder.generator.view.ViewGenerator;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.jdbc.table.ValidateUtil;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;


public class CodeGenerator {

	public static void generator(Context context) {
		List<File> files = new ArrayList<>();
		try {
			Set<String> fields = new HashSet<String>();
			Field primary = null;
			List<Field> associations = new ArrayList<Field>();
			List<Field> simples = new ArrayList<Field>();

			for (Field data : context.fields) {
				//添加默认的格式校验器
				AbsValidateStyles validate = StaticValidateFactory.getValidate(data.getType());
				if(validate != null){
					data.addValidate(validate);
				}
				
				
				data.setName(data.getName().substring(0, 1).toLowerCase() + data.getName().substring(1));
				if (data.getType().equals(context.getEntity())) {
					data.setName("parent");
					context.setTree(true);
					associations.add(data);
				}
				if (!data.getType().isSimpleType()) {
					associations.add(data);
				} else {
					if (data.getType().isPremiryKey()) {
						primary = data;
					}else{
						simples.add(data);
					}
				}

				if (!data.isNullable() && !data.isEditable() && data.getDefaultValue() == null) {
					System.err.println("非空字段，必须为可编辑或提供默认值" + data.getName());
					return;
				}
				String column = data.getColumn();
				if(column.length() > 30){
					System.err.println("表字段长度不能超过30:" + column);
					return;
				}
				if(!ValidateUtil.validateColumn(column)){
					System.err.println("非法的表字段名：" + column);
					return;
				}

				if (fields.contains(data.getName().toLowerCase())) {
					System.err.println("存在重复的字段：" + data.getName());
					return;
				} 
				
				
				fields.add(data.getName().toLowerCase());
			}

			context.removeAllFields();
			if(context.getTable().length() > 30){
				System.err.println("表名长度不能超过30:" + context.getTable());
				return;
			}

			if (primary == null) {
				primary = new Field("id", SimpleTypeEnum.ID, "主键", false, false);
				primary.setNullable(false);
			}
			context.addField(primary);
			context.addFields(simples);

			
			if(context.isTree){
				ComplexType complex = new ComplexType(context.getBasepackage()+"."+ context.getModule().toLowerCase() + ".entity." + context.getEntity());
				complex.setRelationship(ComplexType.ONE_TO_ONE);
				complex.setRefIDField(primary.getName());
				complex.setRefIDColumn(primary.getColumn());
				complex.setRefTable(context.getTable());
				Field field = new Field("parent", complex, "上级" + context.getEntityAlias());
				context.addField(field);
			}
			
			
			if (context.isLevel() && !fields.contains("level")) {
				Field field = new Field("level", SimpleTypeEnum.INTEGER, "数据等级", false, false);
				field.setEditable(false);
				context.addField(field);
			}

			
			if (context.logicDel) {
				Field field = new Field("dr", SimpleTypeEnum.BOOLEAN, "逻辑删除标记（0 未删除 1 已删除）", false, false);
				field.setDefaultValue(0);
				context.addField(field);
			}
			
			
//			ComplexType complex = new ComplexType(context.getBasepackage() + ".security.entity.User");
//			complex.setRelationship(ComplexType.ONE_TO_ONE);
//			complex.setRefNameField("realname");
//			complex.setRefNameColumn("realname");
//			complex.setRefTable("SECURITY_USER");
//			Field field = new Field("creater", complex, "创建人", false, false);
//			field.setColumn("CREATER");
//			context.addField(field);
//			 field = new Field("modifyer", complex, "最后修改人", false, false);
//			 field.setColumn("MODIFYER");
			
			if(context.needAudit){
				Field field = new Field("auditer", SimpleTypeEnum.LONG, "最后审批人", false, false);
				field.setColumn("AUDITER");
				field.setEditable(false);
				field.setDisplay(false);
				context.addField(field);
				
				field = new Field("auditStatus", SimpleTypeEnum.INTEGER, "审批状态", false, false);
				field.setColumn("AUDIT_STATUS");
				field.setDefaultValue(0);
				context.addField(field);
				
				field = new Field("audittime", SimpleTypeEnum.DATETIME, "最后审批时间 ", false, false);
				context.addField(field);
			}
			
		    Field field = new Field("creater", SimpleTypeEnum.LONG, "创建人", false, false);
			field.setColumn("CREATER");
			field.setEditable(false);
			field.setDisplay(false);
			context.addField(field);
			
			field = new Field("modifyer", SimpleTypeEnum.LONG, "最后修改人", false, false);
			field.setColumn("MODIFYER");
			field.setEditable(false);
			field.setDisplay(false);
			context.addField(field);
			
			 field = new Field("createtime", SimpleTypeEnum.DATETIME, "创建时间", false, false);
			context.addField(field);
			 field = new Field("modifytime", SimpleTypeEnum.DATETIME, "最后修改时间", false, false);
			context.addField(field);
			
			

			context.addFields(associations);

			files.addAll(new MapperGenerator().generator(context));
			files.addAll(new JavaGenerator().generator(context));
			files.addAll(new SqlGenerator().generator(context));
			files.addAll(new ViewGenerator().generator(context));
			
			files.addAll(new Log4jGenerator().generator(context));

			System.out.println("已生成以下文件：");
			for (File file : files) {
				System.out.println(file.getPath());
			}

		} catch (Exception e) {
			for (File file : files) {
				file.delete();
			}
			e.printStackTrace();
		}
	}
	
	
	
	public void clear(Context context){
		try {
		String basePath = "";
		String path = context.getJavaBasepath() + basePath + "dao" + File.separator + context.getModule().toLowerCase();
		FileUtils.delete(path);
		path = context.getJavaBasepath() + basePath + "service" + File.separator  + context.getModule().toLowerCase();
		FileUtils.delete(path);
		path = context.getJavaBasepath() + basePath + "controller" + File.separator + context.getModule().toLowerCase();
		FileUtils.delete(path);
		path = context.getJavaBasepath() + basePath + "entity" + File.separator + context.getModule().toLowerCase();
		FileUtils.delete(path);
		path = context.getJavaBasepath() + basePath + "vo" + File.separator +  context.getModule().toLowerCase();
		FileUtils.delete(path);
		
		path = context.getMapperBasepath()  + context.getModule().toLowerCase();
		FileUtils.delete(path);
		
		path = context.getViewBasepath()  + context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase();
		FileUtils.delete(path);
		
		path = context.getSqlBasepath()  + context.getModule().toLowerCase() ;
		FileUtils.delete(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
