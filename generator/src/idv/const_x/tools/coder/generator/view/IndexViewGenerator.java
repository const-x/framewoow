package idv.const_x.tools.coder.generator.view;

import java.io.File;
import java.util.List;

import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.ImageField;
import idv.const_x.tools.coder.generator.RichTextField;
import idv.const_x.utils.FileUtils;
import idv.const_x.file.PlaceHodler;
import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class IndexViewGenerator {
   
	public File generator(ViewContext context) {
		List<Field> fields = context.getBaseContext().getFields();
		StringBuilder heads = new StringBuilder();
		StringBuilder conditioninputs = new StringBuilder();
		StringBuilder scripts = new StringBuilder();
		
		PlaceHodler hodler = new PlaceHodler();
		int height = 32;
		
		String componentlower = context.getBaseContext().getComponent().toLowerCase();
		String idfield = "id";
		for(Field field : fields){
			if(field.getType() == SimpleTypeEnum.ID){
				heads.append("				    <th data-options=\"field:'").append(field.getName()).append("',checkbox:true\"").append(">ID</th>\n");	
				heads.append("				    <th data-options=\"field:'_operate',width:160,align:'center',formatter:").append(componentlower).append("RowOperatFomatter\">操作</th>\n");
				idfield = field.getName();
			}
		}
		
		for(Field field : fields){
			if(field.isCondition()){
				this.addCondtion(field, conditioninputs,context);
			}
			if(field.isDisplay()){
				this.addHeader(field, heads, context);
			}
			this.addOtherScripts(field, idfield, scripts, context);
		}
		if(context.getBaseContext().isNeedAudit()){
			heads.append("				    <th data-options=\"field:'auditStatus',width:80,sortable:'true',formatter:data2StringFomatter\">审批状态</th>\n");
		}
		
		StringBuilder actions = new StringBuilder();
		if(context.getBaseContext().hasExport()){
			actions.append(hodler.hodle(context.getTempBase() + "exportButton.tmp",context.getRepalce()));
		}
		
		StringBuilder rowactions = new StringBuilder();
		this.addRowActions(idfield,rowactions, context);
		
		StringBuilder sortcolumns = new StringBuilder();
		for(Field field : fields){
			if(field.isSortable() && !field.getColumn().equalsIgnoreCase(field.getName())){
				sortcolumns.append("'").append(field.getName()).append("':'").append(field.getColumn()).append("',");
			}
		}
		
		context.setRepalce("actions", actions.toString());
		context.setRepalce("rowactions", rowactions.toString());
		context.setRepalce("conditions", conditioninputs.toString());
		context.setRepalce("heads", heads.toString());
		context.setRepalce("height", String.valueOf(height));
		context.setRepalce("scripts", scripts.toString());
		context.setRepalce("idfield", String.valueOf(idfield));
		context.setRepalce("sortcolumns", sortcolumns.toString());
		
		File file = FileUtils.createFile(context.getSaveBase() + "index.jsp");
		hodler.hodle(context.getTempBase() + "index.tmp",file , context.getRepalce());
		return  file;
	}
	
	private void addCondtion(Field field,StringBuilder conditions, ViewContext context){
		if(field.getType().isSimpleType()){
			if(field.getType().equals(SimpleTypeEnum.BOOLEAN)){
				conditions.append("		        	    <li>\n		        	      <select class= 'easyui-combobox'  style='width:100%;'  name='").append(field.getName()).append("'  data-options=\"label:'").append(field.getAlias()).append(":'\" >\n")
				.append("		        	        <option value=\"\">所有</option>\n")
				.append("		        	        <option value=\"0\" ${condition.").append(field.getName()).append(" eq 0 ? 'selected' : ''} >否</option>\n")
				.append("					        <option value=\"1\" ${condition.").append(field.getName()).append(" eq 1 ? 'selected' : ''} >是</option>\n")
				.append("		        	      </select>\n		        	    </li>\n");
			}else if(field.getType() instanceof EnumType){
				EnumType type = (EnumType)field.getType();
				conditions.append("		        	    <li>\n		        	      <select class= 'easyui-combobox' style='width:100%;' name='").append(field.getName()).append("'  data-options=\"label:'").append(field.getAlias()).append(":'\" >\n")
				.append("		        	        <option value=\"\">所有</option>\n");
                 for(int i = 0 ; i < type.getValues().length; i ++){
                	 String value = type.getValues()[i];
                	 String alias = type.getAlias()[i];
                	 conditions.append("		        	        <option value=\"").append(value).append("\" ${condition.").append(field.getName()).append(" eq '").append(value).append("' ? 'selected' : ''} >").append(alias).append("</option>\n");
                 }
                 conditions.append("		        	      </select>\n		        	    </li>\n");
			}else if(field.getType() == SimpleTypeEnum.DATETIME || field.getType() == SimpleTypeEnum.DATE){
				conditions.append("		        	    <li><input class='easyui-datebox' style='width:100%;' name='").append(field.getName()).append("Begin' ")
				.append("' value=\'${condition.").append(field.getName()).append("Begin}'  data-options='label:\"").append(field.getAlias()).append("从:\"'/></li> \n");
				conditions.append("		        	    <li><input class='easyui-datebox' style='width:100%;' name='").append(field.getName()).append("End' ")
				.append("' value=\'${condition.").append(field.getName()).append("End}' data-options='label:\"").append(field.getAlias()).append("到:\"'/></li> \n");
			}else{
				conditions.append("		        	    <li><input class='easyui-textbox' style='width:100%;' name='").append(field.getName())
				.append("' value=\'${condition.").append(field.getName()).append("}' data-options='label:\"").append(field.getAlias()).append(":\"'/></li> \n");
			}
		}else{
			ComplexType ctype = (ComplexType)field.getType();
			conditions.append("		        	    <li>\n")
			.append("		        	      <input class='easyui-combobox'  style='width:100%;' name='").append(field.getName()).append(".").append(ctype.getRefNameField()).append("' ")
			.append(" data-options=\"label:'").append(field.getAlias()).append(":',hasDownArrow:false,mode:'remote',textField:'").append(ctype.getRefNameField()).append("',valueField:'").append(ctype.getRefNameField()).append("'\"\n ")
			.append("		        	        url='<%=basePath %>/").append(context.getBaseContext().getComponent().toLowerCase()).append("/lookup2").append(field.getName()).append("' ")
			.append(" lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefNameField()).append("' />\n ")
			.append("		        	      <input type='hidden' name='").append(field.getName()).append(".").append(ctype.getRefIDField()).append("' ")
			.append(" lookupGroup='").append(field.getName()).append("' lookupfield='").append(ctype.getRefIDField()).append("' />\n ")
			.append("		        	    </li>\n");
		}
	}
	
	private void addHeader(Field field,StringBuilder heads, ViewContext context){
		if(field instanceof ImageField && !((ImageField)field).isMultiple()){
			heads.append("				    <th data-options=\"field:'").append(field.getName()).append("Url'");
		}else{
			heads.append("				    <th data-options=\"field:'").append(field.getName()).append("'");
		}	
		
		String javaType = field.getType().getJavaType();
		
		if(field.isSortable()){
			heads.append(",sortable:'true'");
		}
		
		if(field.getType() == SimpleTypeEnum.BOOLEAN){
			heads.append(",width:80,formatter:data2StringFomatter");
		}else if(field.getType()  instanceof EnumType){
			heads.append(",width:80,formatter:data2StringFomatter");
		}else if(field.getType()  instanceof ComplexType){
			heads.append(",width:150,formatter:data2StringFomatter");
		}else if(field instanceof ImageField){
			if(((ImageField)field).isMultiple()){
				heads.append(",width:80,align:'center'").append(",formatter:").append(field.getName()).append("Fomatter");
			}else{
				heads.append(",width:150,align:'center'").append(",formatter:imageFomatter");
			}
		}else if(field  instanceof RichTextField){
			heads.append(",width:150,align:'center'").append(",formatter:").append(field.getName()).append("Fomatter");
		}else if(javaType.equalsIgnoreCase("Integer") || javaType.equalsIgnoreCase("int")|| javaType.equalsIgnoreCase("Long")
				|| javaType.equalsIgnoreCase("Float")|| javaType.equalsIgnoreCase("Double")){
			heads.append(",width:80,align:'right'");
		}else if(field.getType() == SimpleTypeEnum.DATE || field.getType() == SimpleTypeEnum.TIME){
			heads.append(",width:80");
		}else{
			heads.append(",width:150");
		}
		heads.append("\">").append(field.getAlias()).append("</th>\n");
	}
	
	private void addRowActions(String idField,StringBuilder rowActions, ViewContext context){
		if(context.getBaseContext().isNeedAudit()){
			rowActions.append("		var actions = '';\n")
			.append("		var editPermission = '${editPermission}';\n")
			.append("		var submitPermission = '${submitPermission}';\n")
			.append("		var auditPermission = '${auditPermission}';\n")
			.append("		actions += operationsWithAudit('").append(context.getBaseContext().getComponent().toLowerCase()).append("',row['").append(idField).append("'],row['auditStatus'],editPermission,submitPermission,auditPermission);\n")
			.append("		return actions;\n");
		}else{
			rowActions.append("		var actions = '';\n")
			.append("		var permission = '${editPermission}';\n")
			.append("		actions += operations('").append(context.getBaseContext().getComponent().toLowerCase()).append("',row['").append(idField).append("'],permission);\n")
			.append("		return actions;\n");
		}
	}
	
	
	private void addOtherScripts(Field field,String idField,StringBuilder scripts, ViewContext context){
		if(field.isDisplay() && ((field instanceof ImageField && ((ImageField)field).isMultiple()) || field instanceof RichTextField )){
			scripts.append("	function ").append(field.getName()).append("Fomatter(value,row,index){\n")
			.append("		if(value){\n")
			.append("			var id = row.").append(idField).append(";\n")
			.append("			var res =  \"<a class='easyui-linkbutton' href='javascript:void(0)' method='get' onclick='toolbarButtonClick(this)' url='<%=basePath %>/")
			.append(context.getBaseContext().getComponent().toLowerCase()).append("/").append(field.getName()).append("/\"+id+\"' type='dialog' model='none' plain='true'>查看").append(field.getAlias()).append("</a>\";\n")
			.append("           return res;\n")
			.append("	 	}\n")
			.append("		return '';\n")
			.append("	}\n\n");
		}
	}	


	
	public void clear(Context context) {
		String saveToFile = context.getViewBasepath() +File.separator+ context.getModule().toLowerCase() + File.separator + context.getComponent().toLowerCase() + File.separator;
		saveToFile = saveToFile + "index.jsp";
		File file = new File(saveToFile);
		if (file.exists()) {
			file.delete();
		}
	}
}
