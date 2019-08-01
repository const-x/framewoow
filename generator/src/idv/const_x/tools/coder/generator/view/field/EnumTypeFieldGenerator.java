package idv.const_x.tools.coder.generator.view.field;

import org.apache.commons.lang3.StringUtils;

import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class EnumTypeFieldGenerator extends AbsFieldGenerator{
	
	@Override
	public void init(ViewContext context, Field field) {
		EnumType enums = (EnumType)field.getType();
		String style = "";
		if(!field.isNullable()){
			style = "validType:['required']";
		}
		String profix = super.getProfix(context, field);
		
		StringBuilder create = new StringBuilder();
		create.append("   		<tr>\n   			<td>").append(field.getAlias()).append(":</td>\n");
		create.append("   			<td><select class= 'easyui-combobox'  editable='false' name='").append(field.getName()).append("' ");
		create.append("  data-options=\"").append(field.isNullable() ? "":"required:true,").append(style).append("\" >\n");
		create.append("            	  <option value='' >-选择-</option>\n");
		for(int i = 0 ; i < enums.getEnumNames().length;i++){
			create.append("            	  <option value=\"").append(enums.getValues()[i]).append("\" ");
			if(enums.getValues()[i] == field.getDefaultValue()){
				create.append(" selected ");
			}
			create.append(" >").append(enums.getAlias()[i]).append("</option>\n");
		}
		create.append("   			</select></td>").append(profix).append("\n   		</tr>\n");
		this.createFieldElements = create.toString();
		
		StringBuilder update = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		update.append("   		<tr>\n   			<td>").append(field.getAlias()).append(":</td>\n");
		update.append("   			<td><select class= 'easyui-combobox'  editable='false'  name='").append(field.getName()).append("' ");
		update.append("  data-options=\"").append(field.isNullable() ? "":"required:true,").append(style).append("\" >\n");
		update.append("            	  <option value='' ").append("${ empty ").append(entry).append(".").append(field.getName());
		update.append(" ? 'selected' : ''}>").append("-选择-</option>\n");
		for(int i = 0 ; i < enums.getEnumNames().length;i++){
			update.append("            	  <option value='").append(enums.getValues()[i]).append("' ")
			.append("${").append(entry).append(".").append(field.getName()).append(" eq ").append(enums.getValues()[i]).append(" ? 'selected' : ''}>")
			.append(enums.getAlias()[i]).append("</option>\n");
		}
		update.append("   			</select></td>").append(profix).append("\n   		</tr>\n");
		this.updateFieldElements = update.toString();
		
	}
}
