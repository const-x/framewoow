package idv.const_x.tools.coder.generator.java;

import idv.const_x.utils.FileUtils;
import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ServiceGenerator {
   
	public List<File> generator(JavaContext context){
		List<File> files = this.generatorService(context);
		files.addAll(this.generatorServiceImpl(context));
		return files;
	}
	
	private List<File> generatorService(JavaContext context){
		List<File> files = new ArrayList<File>();
		PlaceHodler hodler = new PlaceHodler();
		Context ctx = context.getBaseContext();
		String templateFile;
	    if(context.isTree()){
			templateFile =   ctx.getTemplateBasepath() +File.separator + "service" + File.separator+"TreeService.tmp";
		}else{
			templateFile =   ctx.getTemplateBasepath() +File.separator + "service" + File.separator+"Service.tmp";
		}
	    StringBuilder expands = new StringBuilder();
	    Set<String> imports = new HashSet<String>();
	    
		if(ctx.isNeedAudit()){
			String expandsTemp = ctx.getTemplateBasepath() +File.separator + "service" + File.separator+"AuditService.tmp";
			expands.append(hodler.hodle(expandsTemp, context.getRepalce())).append("\n");
			imports.add("import "+ctx.getBasepackage()+".base.entity.OperationHistoryEntity;\n");
		}
		if(ctx.hasExport()){
			expands.append("	void exportExcel(").append(ctx.getEntity()).append("Condition condition, Page page,String selectedFields, OutputStream out);\n");
			imports.add("import java.io.OutputStream;\n");
		}
		
		context.setRepalce("expands", expands.toString());
		StringBuilder importStr = new StringBuilder();
		for (String string : imports) {
			importStr.append(string);
		}
		context.setRepalce("imports", importStr.toString());  
		String saveToFile = ctx.getJavaBasepath() +File.separator + ctx.getModule().toLowerCase()+File.separator+ "service" + File.separator + ctx.getComponent() +"Service.java";
		File file = FileUtils.createFile(saveToFile);
	    hodler.hodle(templateFile, file, context.getRepalce());
	    files.add(file);
		return files;
	}
	
    private List<File> generatorServiceImpl(JavaContext context){
    	List<File> files = new ArrayList<File>();
		PlaceHodler hodler = new PlaceHodler();
		Context ctx = context.getBaseContext();
		String templateFile;
		if(ctx.isLogicDel()){
			context.setRepalce("delMethod", "deleteInLogic(super.getCurrentUser().getId(),new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(new Date()),ids)");
		}else{
			context.setRepalce("delMethod", "delete(ids)");
		}
		StringBuilder validate = new StringBuilder();
		StringBuilder afterupdate = new StringBuilder();
		StringBuilder afterdel = new StringBuilder();
		StringBuilder aftersave = new StringBuilder();

		StringBuilder expands = new StringBuilder();
		Set<String> imports = new HashSet<String>();
		
		
		String uniqueTemp = ctx.getTemplateBasepath() +File.separator + "service" + File.separator+ "impl" + File.separator+"UniqueServiceImpl.tmp";
		String requiredTemp = ctx.getTemplateBasepath() +File.separator + "service" + File.separator+ "impl" + File.separator+"RequiredServiceImpl.tmp";
		for(Field field : ctx.getFields()){
		  if(!field.isNullable() && !field.isPrimary()){
			   context.setRepalce("field", field.getName());
			   context.setRepalce("column", field.getColumn());
	    	   context.setRepalce("fieldFirstUpper",CamelCaseUtils.toFristUpper( field.getName()));
	           context.setRepalce("fieldAlias", field.getAlias());
	           validate.append(hodler.hodle(requiredTemp, context.getRepalce())).append("\n");
		   }
		   if(field.isUnique()&& !field.isPrimary()){
			   context.setRepalce("field", field.getName());
			   context.setRepalce("column", field.getColumn());
	    	   context.setRepalce("fieldFirstUpper",CamelCaseUtils.toFristUpper( field.getName()));
	           context.setRepalce("fieldAlias", field.getAlias());
	           validate.append(hodler.hodle(uniqueTemp, context.getRepalce())).append("\n");
		   }
		   if(!field.getType().isSimpleType()){
			   ComplexType type = (ComplexType)field.getType();
			   if(type.getRelationship() != ComplexType.ONE_TO_ONE){
				   String entryFristlower = context.getRepalce().get("entryFristlower");
				   String refClass = type.getRefClass();
				   imports.add("import "+refClass+";\n");
				   String refname = CamelCaseUtils.toFristUpper(field.getName());
				   refClass = type.getRefClass().substring(refClass.lastIndexOf(".") + 1);
				   
				   aftersave.append("			List<").append(refClass).append(">  ").append(field.getName()).append(" = ").append(entryFristlower).append(".get").append(refname).append("();\n")
				   .append("			if(").append(field.getName()).append(" != null && ").append(field.getName()).append(".size() > 0){\n")
				   .append("				").append(entryFristlower).append("Dao.save").append(refname).append("(").append(entryFristlower).append(".getId(), ").append(field.getName()).append(".toArray(new ").append(refClass).append("[0]));\n")
				   .append("			}");
				   
				   afterdel.append("			").append(entryFristlower).append("Dao.delete").append(refname).append("ById(ids);");
				   
				   afterupdate.append("			").append(entryFristlower).append("Dao.delete").append(refname).append("ById(").append(entryFristlower).append(".getId());\n")
				   .append(aftersave.toString());
			   }
			   
	       }
		}   
		context.setRepalce("validate", validate.toString());
		context.setRepalce("afterupdate", afterupdate.toString());
		context.setRepalce("afterdel", afterdel.toString());
		context.setRepalce("aftersave", aftersave.toString());
		
		
		
		if(context.getBaseContext().isNeedAudit()){
			context.setRepalce("delAudit", "\n			" + CamelCaseUtils.toFristLower(context.getBaseContext().getEntity())+"Dao.deleteAuditById(ids);");
			String expandsTemp = ctx.getTemplateBasepath() +File.separator + "service"+ File.separator+"impl"+ File.separator+"AuditServiceImpl.tmp";
			expands.append(hodler.hodle(expandsTemp, context.getRepalce())).append("\n");
			imports.add("import "+ctx.getBasepackage()+".base.entity.OperationHistoryEntity;\n");
		}else{
			context.setRepalce("delAudit", "");
		}
		
		
		if(ctx.hasExport()){
			imports.add("import java.io.OutputStream;\n");
			imports.add("import "+ctx.getBasepackage()+".common.excel.ExcelColumnMeta;\n");
			imports.add("import "+ctx.getBasepackage()+".common.excel.ExcelCreater;\n");
			imports.add("import org.apache.poi.hssf.usermodel.HSSFWorkbook;\n");
			imports.add("import java.util.ArrayList;\n");
			
			StringBuilder exportculmns = new StringBuilder();
			for(Field field : ctx.getFields()){
				if(field.isExport()){
					exportculmns.append("				    case \"").append(field.getName()).append("\":\n");
					exportculmns.append("				        column = new ExcelColumnMeta(\"").append(field.getName()).append("\", \"").append(field.getAlias()).append("\");\n");
					if(field.getType().isSimpleType()){
						if(field.getType() instanceof EnumType){
							String enumName = CamelCaseUtils.toFristUpper(field.getName()) + "Enum";
							imports.add("import "+ ctx.getBasepackage() + "."+ ctx.getModule().toLowerCase()+ ".entity." +enumName+";\n");
							imports.add("import "+ctx.getBasepackage()+".common.excel.renderer.EnumValueRenderer;\n");
							exportculmns.append("				        column.setRenderer(new EnumValueRenderer<").append(context.getBaseContext().getEntity()).append(">(")
							.append(enumName).append(".values()));\n");
						}else if(field.getType().equals(SimpleTypeEnum.BOOLEAN)){
							imports.add("import "+ctx.getBasepackage()+".common.excel.renderer.BooleanValueRenderer;\n");
							exportculmns.append("				        column.setRenderer(new BooleanValueRenderer<").append(context.getBaseContext().getEntity()).append(">());\n");
						}
					}else{
						ComplexType ctype = (ComplexType)field.getType();
						imports.add("import "+ctx.getBasepackage()+".common.excel.renderer.RefValueRenderer;\n");
						exportculmns.append("				        column.setRenderer(new RefValueRenderer<").append(context.getBaseContext().getEntity())
						.append(">(\"").append(ctype.getRefNameField()).append("\"));\n");
					}
					exportculmns.append("				        columns.add(column);\n				        break;\n");
				}
			}
			context.setRepalce("exportculmns", exportculmns.toString());
			String expandsTemp = ctx.getTemplateBasepath() +File.separator + "service"+ File.separator+"impl"+ File.separator+"ExportServiceImpl.tmp";
			expands.append(hodler.hodle(expandsTemp, context.getRepalce())).append("\n");
		}
		
		context.setRepalce("expands", expands.toString());
		StringBuilder importStr = new StringBuilder();
		for (String string : imports) {
			importStr.append(string);
		}
		context.setRepalce("imports", importStr.toString());  
		
		if(context.isTree()){
		    templateFile = ctx.getTemplateBasepath() +File.separator + "service"+ File.separator+"impl" + File.separator+"TreeServiceImpl.tmp";
		}else{
			templateFile = ctx.getTemplateBasepath() +File.separator + "service"+ File.separator+"impl"+ File.separator+"ServiceImpl.tmp";
		}
		String saveToFile = ctx.getJavaBasepath()  +File.separator + ctx.getModule().toLowerCase()+File.separator+ "service"+File.separator+ File.separator+"impl"+ File.separator+ ctx.getComponent() +"ServiceImpl.java";
		File file = FileUtils.createFile(saveToFile);
		hodler.hodle(templateFile, file, context.getRepalce());
		files.add(file);
		return files;
	}

	public void clear(Context context) {
		String saveToFile = context.getJavaBasepath() + File.separator + context.getModule().toLowerCase() + File.separator + "service" + File.separator + context.getComponent() + "Service.java";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}

		saveToFile = context.getJavaBasepath() + File.separator + context.getModule().toLowerCase() + File.separator + "service" + File.separator + "impl" + File.separator + context.getComponent() + "ServiceImpl.java";
		file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
	
}
