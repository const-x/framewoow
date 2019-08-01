package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class BooleanFieldGenerator extends AbsFieldGenerator{
	
	@Override
	public void init(ViewContext context, Field field) {
		String profix = super.getProfix(context, field);
		StringBuilder create = new StringBuilder();
//		create.append("   		<tr>\n   			<td>").append(field.getAlias()).append("：</td>\n");
//		
//		create.append("   			<td><input type='radio' class='easyui-validatebox' name='").append(field.getName()).append("' ");
//		if(field.getDefaultValue() != null && field.getDefaultValue().equals("1")){
//			create.append(" checked='checked' ");
//		}
//		create.append("value='1'>是</input>\n");
//		create.append("   			    <input type='radio' class='easyui-validatebox' name='").append(field.getName()).append("' ");
//		if(field.getDefaultValue() != null && field.getDefaultValue().equals("0")){
//			create.append(" checked='checked' ");
//		}
//		create.append("value='0'>否</input>\n");
//		create.append(profix).append("   		</tr>\n");
		
		create.append("   		<tr>\n   			<td>").append(field.getAlias()).append(":</td>\n");
		create.append("   			<td><select class= 'easyui-combobox' editable='false'  name='").append(field.getName()).append("' ");
		create.append("  data-options=\"\" >\n");
		create.append("             <option value=\"1\" ");
		if(field.getDefaultValue() != null && field.getDefaultValue().equals("1")){
			create.append(" selected ");
		}
		create.append(" >是</option>\n")
		.append("             <option value=\"0\" ");
		if(field.getDefaultValue() != null && field.getDefaultValue().equals("0")){
			create.append(" selected ");
		}
		create.append(" >否</option>\n")
        .append("   			</select></td>")
        .append(profix)
		.append("   		</tr>\n");
		this.createFieldElements = create.toString();
		
		StringBuilder update = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		update.append("   		<tr>\n   			<td>").append(field.getAlias()).append(":</td>\n");
		update.append("   			<td><select class= 'easyui-combobox' editable='false'  name='").append(field.getName()).append("' ");
		update.append("  data-options=\"").append("\" >\n");
		update.append("		      <option value='1' ").append("${").append(entry).append(".").append(field.getName()).append(" eq 1 ? 'selected' : ''}>是</option>\n");
		update.append("		      <option value='0' ").append("${").append(entry).append(".").append(field.getName()).append(" eq 0 ? 'selected' : ''}>否</option>\n");
       
		update.append("   			</select></td>").append(profix).append("   		</tr>\n");
		this.updateFieldElements = create.toString();
	}

}
