package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class TextFieldGenerator extends SimpleTypeFieldGenerator{
	
	@Override
	public void init(ViewContext context, Field field) {
		String style = super.getStyle(field);
		String profix = super.getProfix(context, field);
		Type type = field.getType();
		
		StringBuilder create = new StringBuilder();
		if(type.getLength() > 64){
			create.append("")
			.append("	<div style='padding:0 10px;'>\n   		<p style='-webkit-margin-before: 2px;'>\n")
			.append("   			<label style='width:90px;text-align:right;vertical-align:top;display:inline-block;'>").append(field.getAlias()).append(":</label>\n")
			.append("   			<input class='easyui-textbox' type='text' name='").append(field.getName())
			.append("' data-options=\"multiline:true,").append(field.isNullable() ? "":"required:true,").append("height:100,width:300,").append(style).append("\" ");
			create.append(" />\n");
			if(!field.isNullable()){
				create.append("   			<span class='starL'></span>\n");
			}
			create.append("   		</p>\n   	</div>\n");
		}else{
			create.append("   		<tr>\n");
			create.append("   			").append("<td>").append(field.getAlias()).append(":</td>\n");
			create.append("   			<td><input class='easyui-textbox' type='text' name='")
			.append(field.getName()).append("' data-options=\"").append(field.isNullable() ? "":"required:true,").append(style).append("\" ");
			if(field.getDefaultValue() != null){
				create.append(" value=\"").append(field.getDefaultValue()).append("\"");
			}
			create.append(" /></td>").append(profix).append("\n   		</tr>\n");
		}
		
		
		this.createFieldElements = create.toString();
		
	
		StringBuilder update = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		
        if(type.getLength() > 64){
        	update.append("")
			.append("	<div style='padding:0 10px;'>\n   		<p style='-webkit-margin-before: 2px;'>\n")
			.append("   			<label style='width:90px;text-align:right;vertical-align:top;display:inline-block;'>").append(field.getAlias()).append(":</label>\n")
			.append("   			<input class='easyui-textbox' type='text' name='").append(field.getName())
			.append("' data-options=\"multiline:true,height:100,width:300,").append(style).append("\" ");
			if(!field.isNullable()){
				update.append(" required='required' ");
			}
			update.append(" value=\"${").append(entry).append(".").append(field.getName()).append("}\"");
			update.append(" />\n");
			if(!field.isNullable()){
				update.append("   			<span class='starL'></span>\n");
			}
			update.append("   		</p>\n   	</div>\n");
		}else{
			update.append("   		<tr>\n");
			update.append("   			").append("<td>").append(field.getAlias()).append(":</td>\n");
			update.append("   			<td><input class='easyui-textbox' type='text' name='")
			.append(field.getName()).append("' data-options=\"");
			update.append(style).append("\" ");
			update.append(" value=\"${").append(entry).append(".").append(field.getName()).append("}\"");
			if(!field.isNullable()){
				update.append(" required='required' ");
			}
			update.append(" /></td>").append(profix).append("\n   		</tr>\n");
		}
		
		
		this.updateFieldElements = update.toString();
		
	}

}
