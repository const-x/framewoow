package idv.const_x.tools.coder.generator.view.field;

import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.view.ViewContext;

public class HiddenFieldGenerator extends AbsFieldGenerator{
	
	@Override
	public void init(ViewContext context, Field field) {
		StringBuilder create = new StringBuilder();
		create.append("		<input type=\"hidden\" name=\"").append(field.getName()).append("\" value=\"").append(field.getDefaultValue()).append("\" /> \n");
		this.createFieldElements = create.toString();
		create = new StringBuilder();
		String entry = context.getBaseContext().getEntity().toLowerCase();
		create.append("		<input type=\"hidden\" name=\"").append(field.getName()).append("\" value=\"${").append(entry).append(".").append(field.getName()).append("}\" />\n");
		this.updateFieldElements = create.toString();
	}

}
