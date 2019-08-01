package idv.const_x.tools.coder.generator.view.field;

import java.util.HashMap;
import java.util.Map;

import idv.const_x.file.PlaceHodler;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class RichTextFieldGenerator extends AbsFieldGenerator{
	
	@Override
	public void init(ViewContext context, Field field) {
	
		StringBuilder create = new StringBuilder();
		create.append("	   <div style='padding:0 10px;'>\n	   		<p style='-webkit-margin-before: 2px;'>\n	   		<label style='width:90px;text-align:right;vertical-align:top;display:inline-block;'>").append(field.getAlias()).append(":</label>\n");
		create.append("	   		<textarea style='margin:0px;height:290px;width:630px;border-radius:4px 4px 4px 4px;border:1px solid #ddd;'  name=\"").append(field.getName()).append("\"  id=\"").append(field.getName()).append("\" ></textarea>\n"); 
		if(!field.isNullable()){
			create.append("   			<span class='starL'></span>\n");
		}
		create.append("	   		</p>\n	   </div>\n");
		this.createFieldElements = create.toString();
		
		create = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		create.append("	   <div style='padding:0 10px;'>\n	   		<p style='-webkit-margin-before: 2px;'>\n	   		<label style='width:90px;text-align:right;vertical-align:top;display:inline-block;'>").append(field.getAlias()).append(":</label>\n");
		create.append("	   		<textarea style='margin:0px;height:290px;width:630px;border-radius:4px 4px 4px 4px;border:1px solid #ddd;'  name=\"").append(field.getName()).append("\"  id=\"").append(field.getName()).append("\" \n"); 
		create.append("	   		>${").append(entry).append(".").append(field.getName()).append("}</textarea>\n");
		if(!field.isNullable()){
			create.append("   			<span class='starL'></span>\n");
		}
		create.append("	   		</p>\n	   </div>\n");
		
		this.updateFieldElements = create.toString();
		
		
		StringBuilder scripts = new StringBuilder();
		String temp = "xheditor.tmp";
		PlaceHodler hodler = new PlaceHodler();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("module", context.getBaseContext().getModule().toLowerCase());
		properties.put("field",field.getName());
		scripts.append(hodler.hodle(context.getTempBase() + temp,properties)).append("\n");
		
		
		
		super.createExpandScripts = scripts.toString();
		super.updateExpandScripts = createExpandScripts;
		
		if(!field.isNullable()){
			StringBuilder validate = new StringBuilder();
			validate.append("		if(!($(\"#").append(field.getName()).append("\").val())){\n");
			validate.append("			$.messager.alert('错误', '").append(field.getAlias()).append("不能为空', 'error');\n");
			validate.append("			return false;\n");
			validate.append("		}\n");
			super.createValdiatesBeforeSubmit = validate.toString();
			super.updateValdiatesBeforeSubmit = validate.toString();
		}
	}

}
