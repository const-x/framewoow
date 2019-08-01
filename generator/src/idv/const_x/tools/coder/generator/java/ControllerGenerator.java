package idv.const_x.tools.coder.generator.java;

import idv.const_x.file.PlaceHodler;
import idv.const_x.tools.coder.CamelCaseUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.utils.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ControllerGenerator {
   
	public File generator(JavaContext context){
		PlaceHodler hodler = new PlaceHodler();
		Context ctx = context.getBaseContext();
		Map<String, String> repalce = context.getRepalce();
		String templateFile = null;
		Set<String> imports = new HashSet<String>();
		Set<String> autowireds = new HashSet<String>();
		
		if(ctx.isSystemData()){
			StringBuilder sysdata = new StringBuilder(); 
			sysdata.append("		    User user = super.getCurrentUser();\n");
			sysdata.append("		    if(user.getUserType() != UserTypeEnum.SYSTEM.getValue()){\n");
			sysdata.append("		  	  return super.alert(\"只有系统级用户才能维护此项数据\", null, map);\n");
			sysdata.append("		    }");
			imports.add("import " + ctx.getBasepackage() + ".security.entity.UserTypeEnum;\n");
			context.setRepalce("beforecreate",sysdata.toString());
			context.setRepalce("beforeupdate",sysdata.toString());
		}else{
			context.setRepalce("beforecreate", "");
			context.setRepalce("beforeupdate", "");
		}
		
		StringBuilder refs = new StringBuilder();
		
		for(Field field : ctx.getFields() ){
			if(!field.getType().isSimpleType() && field.isEditable()){
				ComplexType type = (ComplexType)field.getType();
				String refTemp = ctx.getTemplateBasepath() +File.separator + "controller" + File.separator+"RefController.tmp";
				if(type.getRelationship() != ComplexType.ONE_TO_ONE){
					refTemp = ctx.getTemplateBasepath() +File.separator + "controller" + File.separator+"BatchRefController.tmp";
				}
				imports.add("import "+type.getRefClass()+";\n");
				imports.add("import "+ctx.getBasepackage()+"."+type.getRefModule()+".view."+type.getJavaType()+"View;\n");
				imports.add("import "+ctx.getBasepackage()+"."+type.getRefModule()+".service."+type.getJavaType()+"Service;\n");
				autowireds.add("	@Autowired\n	private "+type.getJavaType()+"Service " + CamelCaseUtils.toFristLower(type.getJavaType()) + "Service;\n\n");
				context.setRepalce("fieldUpper", field.getName().toUpperCase());
				context.setRepalce("field", field.getName());
				context.setRepalce("alias", field.getAlias());
				context.setRepalce("refClass", type.getJavaType());
				context.setRepalce("refClassFristLower", CamelCaseUtils.toFristLower(type.getJavaType()));
				context.setRepalce("refNameField", type.getRefNameField());
				refs.append(hodler.hodle(refTemp, context.getRepalce())).append("\n");
				
				System.out.println("需在controller中完成 对" + field.getAlias() + "参照内容的查询部分");
			}
		}
		context.setRepalce("refs", refs.toString());
		
		if(context.isTree()){
			templateFile = ctx.getTemplateBasepath() +File.separator + "controller"+File.separator + File.separator+"TreeController.tmp";
		}else{
			templateFile = ctx.getTemplateBasepath() +File.separator + "controller"+File.separator + File.separator+"Controller.tmp";
		}
		String imageTemp =  ctx.getTemplateBasepath() +File.separator + "controller"+File.separator + File.separator+"ImageViews.tmp";
		String richTextTemp =  ctx.getTemplateBasepath() +File.separator + "controller"+File.separator + File.separator+"RichTextViews.tmp";
		String saveToFile = ctx.getJavaBasepath() +File.separator +  ctx.getModule().toLowerCase() +File.separator+"controller"+File.separator+ File.separator+ ctx.getComponent() +"Controller.java";
		File file = FileUtils.createFile(saveToFile);
		
		StringBuilder image2url = new StringBuilder();
		StringBuilder url2image = new StringBuilder();
		StringBuilder delrel = new StringBuilder();
		
		StringBuilder viewpic = new StringBuilder();
		StringBuilder viewText = new StringBuilder();
		for(Field field : ctx.getFields()){
			if(field instanceof ImageField){
				ImageField img = (ImageField)field;
				if(img.isMultiple()){
					String name = img.getName();
					String upper = CamelCaseUtils.toFristUpper(name);
					
					image2url.append("     String ").append(name).append(" = (String)view.get(\"").append(name).append("\");\n");
					image2url.append("     if(StringUtils.isNotBlank(").append(name).append(")){\n");
					image2url.append("        String[] keys = ").append(name).append(".split(\",\");\n");
					image2url.append("        String[] urls = super.convertToUrls(keys);\n");
					image2url.append("        view.set").append(upper).append("Urls(urls);\n");
					image2url.append("     };\n");
					
					url2image.append("     String ").append(name).append(" = entry.get").append(upper).append("();\n");
					url2image.append("     if(StringUtils.isNotBlank(").append(name).append(")){\n");
					url2image.append("        String[] urls = ").append(name).append(".split(\",\");\n");
					url2image.append("        StringBuilder keys = new StringBuilder();\n");
					url2image.append("        for(int i = 0 ; i < urls.length; i++){ \n");
					url2image.append("            keys.append(super.addRelkey(urls[i])).append(\",\");\n");
					url2image.append("        }\n");
					url2image.append("        keys = keys.deleteCharAt(keys.length() - 1);\n");
					url2image.append("        entry.set").append(upper).append("(keys.toString());\n");
					url2image.append("     };\n");
					
					delrel.append("     	String ").append(name).append(" = entry.get").append(upper).append("();\n");
					delrel.append("     	if(StringUtils.isNotBlank(").append(name).append(")){\n");
					delrel.append("     		String[] keys = ").append(name).append(".split(\",\");\n");
					delrel.append("     		super.relaseRelKeys(keys);\n");
					delrel.append("     	};\n");
					
					repalce.put("field", img.getName());
					repalce.put("fieldFirstUpper", CamelCaseUtils.toFristUpper(field.getName()));
					viewpic.append(hodler.hodle(imageTemp, repalce)).append("\n");
					
				}else{
					String name = img.getName();
					String upper = CamelCaseUtils.toFristUpper(name);
					
					image2url.append("     String ").append(name).append(" = (String)view.get(\"").append(name).append("\");\n");
					image2url.append("     if(StringUtils.isNotBlank(").append(name).append(")){\n");
					image2url.append("       view.set").append(upper).append("Url(super.convertToUrl(").append(name).append("));\n");
					image2url.append("     };\n");
					
					url2image.append("     String ").append(name).append(" = entry.get").append(upper).append("();\n");
					url2image.append("     if(StringUtils.isNotBlank(").append(name).append(")){\n");
					url2image.append("       entry.set").append(upper).append("(super.addRelkey(").append(name).append("));\n");
					url2image.append("     };\n");
					
					delrel.append("     	String ").append(name).append(" = entry.get").append(upper).append("();\n");
					delrel.append("    		if(StringUtils.isNotBlank(").append(name).append(")){\n");
					delrel.append("     	   super.relaseRelKeys(").append(name).append(");\n");
					delrel.append("     	};\n");
				}
			}else if(field instanceof RichTextField){
				repalce.put("field", field.getName());
				repalce.put("fieldFirstUpper", CamelCaseUtils.toFristUpper(field.getName()));
				viewText.append(hodler.hodle(richTextTemp, repalce)).append("\n");
				
				String name = field.getName();
				String upper = CamelCaseUtils.toFristUpper(name);
				image2url.append("     String ").append(name).append(" = (String)view.get(\"").append(name).append("\");\n");
				image2url.append("     if(StringUtils.isNotBlank(").append(name).append(")){\n");
				image2url.append("       view.put(\"").append(name).append("\",super.convertToUrlInContent(").append(name).append("));\n");
				image2url.append("     };\n");
				
				url2image.append("     String ").append(name).append(" = entry.get").append(upper).append("();\n");
				url2image.append("     if(StringUtils.isNotBlank(").append(name).append(")){\n");
				url2image.append("       entry.set").append(upper).append("(super.addRelkeyInContent(").append(name).append("));\n");
				url2image.append("     };\n");
				
				delrel.append("     	String ").append(name).append(" = entry.get").append(upper).append("();\n");
				delrel.append("    		if(StringUtils.isNotBlank(").append(name).append(")){\n");
				delrel.append("     	   super.relaseRelKeysInContent(").append(name).append(");\n");
				delrel.append("     	};\n");
			}
		}
	
		
		context.setRepalce("convert2url", image2url.toString());
		context.setRepalce("saverel", url2image.toString());
		context.setRepalce("delrel", delrel.toString());
		context.setRepalce("viewpic", viewpic.toString());
		context.setRepalce("viewText", viewText.toString());
		
		StringBuilder expands = new StringBuilder();
		if(ctx.isNeedAudit()){
			String expandsTemp = ctx.getTemplateBasepath() +File.separator + "controller" + File.separator+"AuditController.tmp";
			expands.append(hodler.hodle(expandsTemp, context.getRepalce())).append("\n");
			imports.add("import "+ctx.getBasepackage()+".base.entity.OperationHistoryEntity;\n");
			imports.add("import "+ctx.getBasepackage()+".base.view.OperationHistoryView;\n");
			
			imports.add("import "+ctx.getBasepackage()+".base.entity.AuditStatusEnum;\n");
		}
		if(ctx.hasExport()){
			String expandsTemp = ctx.getTemplateBasepath() +File.separator + "controller" + File.separator+"ExportController.tmp";
			StringBuilder exportText = new StringBuilder();
			exportText.append(" List<SelectField> selectFields = new ArrayList<SelectField>();\n");
			List<Field> fields = context.getBaseContext().getFields();
			exportText.append("			 SelectField  field;\n");
				for (Field field : fields) {
					if(field.isExport()){
						exportText.append("			 field = new SelectField(\""+field.getAlias()+"\",\""+field.getName()+"\",true);\n");
						exportText.append("			 selectFields.add(field);\n");
					}
				}
				context.setRepalce("exportText", exportText.toString());
				expands.append(hodler.hodle(expandsTemp, context.getRepalce())).append("\n"); 
				imports.add("import java.util.Date;\n");
				imports.add("import java.text.SimpleDateFormat;\n");
				imports.add("import "+ctx.getBasepackage()+".util.dwz.SelectField;\n");
			}

		
		context.setRepalce("expands", expands.toString());
		
		StringBuilder importStr = new StringBuilder();
		for (String string : imports) {
			importStr.append(string);
		}
		context.setRepalce("imports", importStr.toString());
		
		StringBuilder autowiredStr = new StringBuilder();
		for (String string : autowireds) {
			autowiredStr.append(string);
		}
		context.setRepalce("autowireds", autowiredStr.toString());
		hodler.hodle(templateFile, file, repalce);
		return file;
	}

	public void clear(Context context) {
		String saveToFile = context.getJavaBasepath() +File.separator+ context.getModule().toLowerCase() +File.separator + "controller"+ File.separator+ context.getComponent() +"Controller.java";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
	
}
