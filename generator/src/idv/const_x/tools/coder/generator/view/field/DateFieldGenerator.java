package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class DateFieldGenerator extends SimpleTypeFieldGenerator{
	
	@Override
	public void init(ViewContext context, Field field) {
		String style = super.getStyle(field);
		String profix = super.getProfix(context, field);
		
		StringBuilder create = new StringBuilder();
		create.append("   		<tr>\n");
		create.append("   			").append("<td>").append(field.getAlias()).append(":</td>\n");
		create.append("   			<td><input class='easyui-datebox' type='text' name='")
		.append(field.getName()).append("' data-options=\"").append(field.isNullable() ? "":"required:true,")
		.append(style).append("\" ");
		if(field.getDefaultValue() != null){
			create.append(" value=\"").append(field.getDefaultValue()).append("\"");
		}
		create.append(" />").append(profix).append("</td>\n   		</tr>\n");
		this.createFieldElements = create.toString();
		
	
		StringBuilder update = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		update.append("   		<tr>\n");
		update.append("   			<td>").append(field.getAlias()).append(":</td>\n");
		update.append("   			<td><input class='easyui-datebox' type='text' name='")
		.append(field.getName()).append("' data-options=\"").append(field.isNullable() ? "":"required:true,")
		.append(style).append("\" ").append(" value=\"${").append(entry).append(".").append(field.getName()).append("}\"");
		update.append(" />").append(profix).append("</td>\n   		</tr>\n");
		this.updateFieldElements = update.toString();
	}

}
